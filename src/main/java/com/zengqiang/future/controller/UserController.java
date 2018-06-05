package com.zengqiang.future.controller;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.UserForm;
import com.zengqiang.future.pojo.Account;
import com.zengqiang.future.pojo.User;
import com.zengqiang.future.service.IUserService;
import com.zengqiang.future.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/register" ,method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse register(UserForm userForm){
        return userService.register(userForm);
    }

    @RequestMapping("/login")
    @ResponseBody
    public ServerResponse login(UserForm userForm, HttpServletResponse response){
        ServerResponse serverResponse=
                userService.login(userForm);
        if(serverResponse.isSuccess()){
            Map<String,Object> claims=new HashMap<>();
            claims.put("account",userForm.getAccount());
            claims.put("type",userForm.getType());
            String token= TokenUtil.createToken("account",claims,100000);
            response.addHeader("Authorization",token);

        }

        return serverResponse;
    }
}
