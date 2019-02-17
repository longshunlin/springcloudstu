package com.cloud.tangyi.authServer.server;

import com.cloud.tangyi.authServer.dao.AccountRepository;
import com.cloud.tangyi.authServer.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServer implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;    // 账户数据操作接口

    /**
     * 根据用户名查找账户信息并返回用户信息实体
     * @param username 用户名
     * @return 用于身份认证的 UserDetails 用户信息实体
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserName(username);
        if (account!=null){
            return new User(account.getUserName(),account.getPassWord(), AuthorityUtils.createAuthorityList(account.getRoles()));
        }else {
            throw  new UsernameNotFoundException("用户["+username+"]不存在");
        }
    }
}
