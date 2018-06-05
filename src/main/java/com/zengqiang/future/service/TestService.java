package com.zengqiang.future.service;

import com.zengqiang.future.dao.AccountMapper;
import com.zengqiang.future.dao.UserMapper;
import com.zengqiang.future.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

    @Autowired
    AccountMapper accountMapper;

    public void test(){
        Account account1=new Account();
        account1.setPassword("123");
        account1.setAccount("123456789");
        account1.setType(1);

        accountMapper.insert(account1);

        Account account2=new Account();
        account2.setPassword("123##");
        account2.setAccount("123456789");
        account2.setType(1);
        accountMapper.insert(account2);
    }
}
