package com.cloud.oauth2.dao;

import com.cloud.oauth2.entity.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author longshunlin
 * @Date 2019/1/29
 */
@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, String> {
}
