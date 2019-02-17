package com.cloud.oauth.dao;

import com.cloud.oauth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author longshunlin
 * @Date 2019/1/29
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
}
