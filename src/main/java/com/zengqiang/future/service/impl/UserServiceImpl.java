package com.zengqiang.future.service.impl;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.dao.AccountMapper;
import com.zengqiang.future.dao.UserMapper;
import com.zengqiang.future.form.UserForm;
import com.zengqiang.future.pojo.Account;
import com.zengqiang.future.pojo.User;
import com.zengqiang.future.service.IUserService;
import com.zengqiang.future.util.EncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements IUserService {

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountMapper accountMapper;

    public ServerResponse login(UserForm userForm){
        if(StringUtils.isEmpty(userForm.getAccount())||
                StringUtils.isEmpty(userForm.getPassword())||
                userForm.getType()==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        String password=accountMapper.selectPasswordByAccount(userForm.getAccount());
        if(password==null){
            return ServerResponse.createByErrorMessage("该用户不存在");
        }
        String password2=EncryptUtil.encryptByMD5(userForm.getPassword());
        if(password.equals(password2)){
            int id=accountMapper.selectIdByAccount(userForm.getAccount());
            User user=userMapper.selectByAccountId(id);
            return ServerResponse.createBySuccess(user);
        }
        return null;
    }

    //涉及多个表的插入，开启事务
    @Transactional
    public ServerResponse register( UserForm userForm){
        Account account=formToAccount(userForm);
        User user=formToUser(userForm);
        if(account==null){
            return ServerResponse.createByErrorMessage("完善注册信息");
        }
        try{
            //插入时将id设置如account对象
            int rowCount=accountMapper.insert(account);
            if(rowCount>0 && user!=null){
                user.setAccountId(account.getId());
                rowCount=userMapper.insert(user);
                if(rowCount>0){

                    return ServerResponse.createBySuccess();
                }
            }
        }catch (DuplicateKeyException e){
            logger.error("注册时用户名冲突");
            return ServerResponse.createByErrorMessage("该用户名已存在");
        }
        return ServerResponse.createByErrorMessage("未知错误");
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
}
