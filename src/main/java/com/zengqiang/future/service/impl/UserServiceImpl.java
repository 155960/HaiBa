package com.zengqiang.future.service.impl;

import com.zengqiang.future.common.Const;
import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.dao.*;
import com.zengqiang.future.form.UserForm;
import com.zengqiang.future.info.PostInfo;
import com.zengqiang.future.info.UserInfo;
import com.zengqiang.future.pojo.*;
import com.zengqiang.future.service.IFileService;
import com.zengqiang.future.service.IUserService;
import com.zengqiang.future.util.EncryptUtil;
import com.zengqiang.future.util.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import net.sf.jsqlparser.schema.Server;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePerMapper rolePerMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private IFileService fileService;

    @Autowired
    private AddressMapper addressMapper;

    @Transactional
    public ServerResponse uploadHeadImg(MultipartFile file,Integer userId){
        String url=fileService.upload(file, Const.Good.IMG);
        if(StringUtils.isEmpty(url)){
            return ServerResponse.createByErrorMessage("上传失败");
        }else{
            Good good=new Good();
            good.setUrl(url);
            good.setType(Const.Good.IMG);
            goodMapper.insert(good);
            User user=new User();
            user.setImageId(good.getId());
            int rowCount=userMapper.updateByPrimaryKeySelective(user);
            if(rowCount>0){
                return ServerResponse.createBySuccess();
            }else{
                return ServerResponse.createByErrorMessage("更新失败");
            }
        }
    }

    /**
     *
     * @param
     * @return
     */
    public ServerResponse findMyPost(Integer userId){
        try{
            List<Post> posts=postMapper.selectPostsByUserId(userId);
            if(posts!=null&&posts.size()>0){
                List<PostInfo> infos=postToPostInfo(posts);
                return ServerResponse.createBySuccess(infos);
            }else{
                return ServerResponse.createBySuccessMessage("不存在帖子信息");
            }

        }catch (Exception e){
            return ServerResponse.createByErrorMessage("查询失败");
        }
    }

    private List<PostInfo> postToPostInfo(List<Post> posts){
        List<PostInfo> infos=new ArrayList<>();
        for(int i=0;i<posts.size();i++){
            PostInfo info=postToPostInfo(posts.get(i));
            infos.add(info);
        }
        return infos;
    }

    private PostInfo postToPostInfo(Post post){
        PostInfo info=new PostInfo();
        info.setContent(post.getContent());
        info.setId(post.getId());
        info.setType(post.getType());
        info.setIsEnabledComment(post.getIsEnabledComment());
        info.setUpdateTime(post.getUpdateTime());
        info.setNumComment(post.getNumComment());
        info.setNumPraise(post.getNumPraise());
        try{
            Address addr=addressMapper.selectByPrimaryKey(post.getAddrId());
            if(addr!=null){
                info.setAddr(addr.getAddrDetail());
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询地址失败");
        }

        return info;
    }

    //可省略
    public String checkToken(String token)throws Exception{
        if(token==null){
            return null;
        }
        Claims claims=TokenUtil.checkToken(token);
        if(claims!=null){
            return (String)claims.get("account");
        }
        return null;
    }

    public ServerResponse logout(String account,String token){
        if(account==null||token==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        String account1= null;
        try {
            account1 = checkToken(token);
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("token已过期，重新登录");
        }
        catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("token验证出错或者其他错误,请检查token");
        }
        if(account1==null||!account1.equals(account)){
            return ServerResponse.createByErrorMessage("token与用户不匹配");
        }
        boolean result=updateLoginStatus(account,false);
        if(result){
            return ServerResponse.createBySuccess();
        }else{
            return ServerResponse.createByErrorMessage("该用户不存在");
        }
    }

    /**
     *
     * @param isLogin  true 未登录，设置登录状态  false 已登录，设置登出状态
     */
    public boolean updateLoginStatus(String account ,boolean isLogin){
        Integer accountId=accountMapper.selectIdByAccount(account);
        if(accountId==null){
            return false;
        }
        User user=new User();
        user.setAccountId(accountId);
        if(isLogin){
            user.setStatus(1);
        }else{
            user.setStatus(0);
        }
        int rowCount=userMapper.updateByAccountId(user);
        if(rowCount>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 流程
     * 1根据登录信息判断用户是否存在
     * 2.密码校对
     * 3.用户信息查询，权限信息查询，返回给客户端
     *
     * @param userForm
     * @return
     */
    public ServerResponse login(UserForm userForm){
        if(StringUtils.isEmpty(userForm.getAccount())||
                StringUtils.isEmpty(userForm.getPassword())||
                userForm.getType()==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        try{
            String password=accountMapper.selectPasswordByAccount(userForm.getAccount());
            if(password==null){
                return ServerResponse.createByErrorMessage("该用户不存在");
            }
            String password2=EncryptUtil.encryptByMD5(userForm.getPassword());
            if(password.equals(password2)){
                int id=accountMapper.selectIdByAccount(userForm.getAccount());
                User user=userMapper.selectByAccountId(id);
                int roleId=userRoleMapper.selectRoleIdByAccountId(id);
                //权限代码
                int perCode=rolePerMapper.selectCodeByRoleId(roleId);
                UserInfo info=userToUserInfo(user);
                info.setAccount(userForm.getAccount());
                info.setCode(perCode);
                String imgUrl=goodMapper.selectByPrimaryKey(user.getId()).getUrl();
                info.setImg(imgUrl);
                return ServerResponse.createBySuccess(info);
            }else{
                return ServerResponse.createByErrorMessage("密码与用户名不匹配");
            }
        }catch (Exception e){
            logger.error("未知错误，可能是数据库查询出错");
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("未知错误，可能是数据库查询出错");
        }

    }

    //涉及多个表的插入，开启事务
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public ServerResponse register( UserForm userForm){
        Account account=formToAccount(userForm);
        User user=formToUser(userForm);
        if(account==null){
            return ServerResponse.createByErrorMessage("完善注册信息");
        }
        try{
            //插入时将id设置如account对象
            int rowCount=accountMapper.insert(account);
            if(user==null){
                user=new User();
            }

            user.setAccountId(account.getId());
            user.setEmail(account.getAccount());
            userMapper.insert(user);

            //数据中普通用户id=2
            UserRole userRole=createUserRole(account.getId(),2);
            userRoleMapper.insert(userRole);
            return ServerResponse.createBySuccess();
        }catch (DuplicateKeyException e){
            logger.error("注册时用户名冲突");
            return ServerResponse.createByErrorMessage("该用户名已存在");
        }catch (Exception e){
            logger.error("未知错误，可能是插入数据库时出错");
            return ServerResponse.createByErrorMessage("未知错误，可能是插入数据库时出错");
        }

    }


    private UserRole createUserRole(int userId,int roleId){
        UserRole userRole=new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRole;
    }
    //
    //前端设定账户、密码不能为null
    private Account formToAccount(UserForm form){
        if(form==null){
            return null;
        }
        Account account=new Account();
        account.setAccount(form.getAccount());
        account.setPassword(EncryptUtil.encryptByMD5(form.getPassword()));
        account.setType(form.getType());
        return account;
    }

    private User formToUser(UserForm form){
        if(form==null){
            return null;
        }
        User user=new User();
        user.setEmail(form.getEmail());
        user.setImageId(form.getImageId());
        user.setNickName(form.getNickName());
        user.setQqNumber(form.getQqNumber());
        user.setPersonalIndroduce(form.getPersonalIndroduce());
        user.setPhone(form.getPhone());
        return user;
    }

    private UserForm userToUserForm(User user){
        UserForm userForm=new UserForm();
        userForm.setEmail(user.getEmail());
        userForm.setImageId(user.getImageId());
        userForm.setNickName(user.getNickName());
        userForm.setPersonalIndroduce(user.getPersonalIndroduce());
        userForm.setPhone(user.getPhone());
        userForm.setQqNumber(user.getQqNumber());
        return userForm;
    }

    private UserInfo userToUserInfo(User user){
        UserInfo info=new UserInfo();
        info.setMessages(user.getMessages());
        info.setEmail(user.getEmail());
        info.setNickName(user.getNickName());
        info.setPersonalIndroduce(user.getPersonalIndroduce());
        info.setPhone(user.getPhone());
        info.setQqNumber(user.getQqNumber());
        info.setCreateTime(user.getCreateTime());
        return info;
    }
}
