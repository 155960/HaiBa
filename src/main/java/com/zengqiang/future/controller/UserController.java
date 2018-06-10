package com.zengqiang.future.controller;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.UserForm;
import com.zengqiang.future.pojo.Account;
import com.zengqiang.future.pojo.Post;
import com.zengqiang.future.pojo.User;
import com.zengqiang.future.service.IFileService;
import com.zengqiang.future.service.IUserService;
import com.zengqiang.future.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/register" ,method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse register(UserForm userForm){
        return userService.register(userForm);
    }

    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(UserForm userForm, HttpServletResponse response){
        ServerResponse serverResponse=
                userService.login(userForm);
        if(serverResponse.isSuccess()){
            //这里存入什么待定
            Map<String,Object> claims=new HashMap<>();
            claims.put("account",userForm.getAccount());
            claims.put("type",userForm.getType());
            String token= TokenUtil.createToken(userForm.getAccount(),claims,360000000);
            response.addHeader("Authorization",token);
            //更新登录状态
            boolean isSuccess=userService.updateLoginStatus(userForm.getAccount(),true);
            if(isSuccess){
                logger.info("用户"+userForm.getAccount()+"登录成功");
                return serverResponse;
            }else{
                logger.info("用户"+userForm.getAccount()+"登录成功");
                return ServerResponse.createByErrorMessage("未知错误");
            }
        }
        return serverResponse;
    }

    @RequestMapping(value = "/logout" ,method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse logout(String account,HttpServletRequest request){
        String token=request.getHeader("Authorization");
        return userService.logout(account,token);
    }

    @RequestMapping("/my_post")
    @ResponseBody
    public ServerResponse findMyPost(HttpServletRequest request){
        String token=request.getHeader("Authorization");
        Integer userId=TokenUtil.getUserId(token);
        return userService.findMyPost(userId);
    }

    @RequestMapping("/upload_head_picture")
    public ServerResponse uploadHeadPicture(MultipartFile file,HttpServletRequest request){
        String token=request.getHeader("Authorization");
        Integer userId=TokenUtil.getUserId(token);
        return userService.uploadHeadImg(file,userId);
    }
}
