package com.cloud.oauth2.service;

import com.cloud.oauth2.dao.BaseUserRepository;
import com.cloud.oauth2.entity.BaseUser;
import com.cloud.oauth2.entity.BaseUserDetail;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author longshunlin
 * @Date 2019/1/29
 */
@Service
public class BaseUserDetailService implements UserDetailsService {

//    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private BaseUserService baseUserService;
//    @Autowired
//    private BaseRoleService baseRoleService;
    @Autowired
    private BaseUserRepository baseUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        // 调用FeignClient查询用户
//        ResponseData<BaseUser> baseUserResponseData = baseUserService.getUserByUserName(username);
//        if(baseUserResponseData.getData() == null || !ResponseCode.SUCCESS.getCode().equals(baseUserResponseData.getCode())){
////            logger.error("找不到该用户，用户名：" + username);
//            System.out.println("找不到该用户，用户名：" + username);
//            throw new UsernameNotFoundException("找不到该用户，用户名：" + username);
//        }
//        BaseUser baseUser = baseUserResponseData.getData();
//
//        // 调用FeignClient查询角色
//        ResponseData<List<BaseRole>> baseRoleListResponseData = baseRoleService.getRoleByUserId(baseUser.getId());
//        List<BaseRole> roles;
//        if(baseRoleListResponseData.getData() == null ||  !ResponseCode.SUCCESS.getCode().equals(baseRoleListResponseData.getCode())){
//            logger.error("查询角色失败！");
//            roles = new ArrayList<>();
//        }else {
//            roles = baseRoleListResponseData.getData();
//        }
        BaseUser baseUser = null;
        try {
            baseUser = baseUserRepository.findById(username).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取用户权限列表
        List<GrantedAuthority> authorities = new ArrayList();
//        roles.forEach(e -> {
//            // 存储用户、角色信息到GrantedAuthority，并放到GrantedAuthority列表
//            GrantedAuthority authority = new SimpleGrantedAuthority(e.getRoleCode());
//            authorities.add(authority);
//
//        });

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        authorities.add(authority);

        // 返回带有用户权限信息的User
        org.springframework.security.core.userdetails.User user =  new org.springframework.security.core.userdetails.User(baseUser.getUsername(),
                baseUser.getPassword(), baseUser.getEnabled(), true, true, true, authorities);
        return new BaseUserDetail(baseUser, user);
    }
}
