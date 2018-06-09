package com.zengqiang.future.controller;

import com.alibaba.fastjson.JSON;
import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.dao.AccountMapper;
import com.zengqiang.future.dao.RoleMapper;
import com.zengqiang.future.dao.UserMapper;
import com.zengqiang.future.dao.UserRoleMapper;
import com.zengqiang.future.form.UserForm;
import com.zengqiang.future.pojo.Post;
import com.zengqiang.future.pojo.Role;
import com.zengqiang.future.pojo.User;
import com.zengqiang.future.pojo.UserRole;
import com.zengqiang.future.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TextControoler {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    TestService testService;

    @Autowired
    AccountMapper accountMapper;

    @RequestMapping(value = "test2",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse test(@RequestBody List<Post> post,@RequestBody User userForm) {

        if(post!=null){
            String s=post.get(0).getContent();
            System.out.println(s);
        }
        if (userForm!=null){
            System.out.println(userForm.getPhone());
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "test",method = RequestMethod.POST)
    @ResponseBody
    public void test(){
        testService.test();


        /*Integer a=accountMapper.selectIdByAccount("1234");
        int b=accountMapper.selectIdByAccount("123456789");
        System.out.println(a+"  "+b);*/
       /* System.out.println("test");
        for(int i=0;i<150;i++){
        try{
            User user=new User();
            user.setAccount("123456");
            user.setEmail("155960");
            user.setPassword("123");
            userMapper.insert(user);
            int userId=user.getId();
            Role role=new Role();
            role.setRoleName("123");
            roleMapper.insert(role);
            int roleId=role.getId();
            UserRole userRole=new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }catch (Exception e){

        }}*/

    }
}
