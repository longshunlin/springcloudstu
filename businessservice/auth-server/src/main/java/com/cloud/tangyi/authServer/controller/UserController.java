package com.cloud.tangyi.authServer.controller;


import com.cloud.tangyi.authServer.dao.AccountRepository;
import com.cloud.tangyi.authServer.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * 初始化用户数据
     */
    @Autowired
    public void init(){

        // 为了方便测试,这里添加了两个不同角色的账户
        accountRepository.deleteAll();

        Account accountA = new Account();
        accountA.setUserName("admin");
        accountA.setPassWord("admin");
        accountA.setRoles("ROLE_ADMIN,ROLE_USER");
        accountRepository.save(accountA);

        Account accountB = new Account();
        accountB.setUserName("guest");
        accountB.setPassWord("pass123");
        accountB.setRoles("ROLE_GUEST");
        accountRepository.save(accountB);
    }

    /**
     * 获取授权用户的信息
     * @param user 当前用户
     * @return 授权信息
     */
    @RequestMapping(method = RequestMethod.GET,value = "/user")
    public Principal user(Principal user){
        return user;
    }
}
