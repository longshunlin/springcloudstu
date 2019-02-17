package com.cloud.tangyi.authServer.dao;

import com.cloud.tangyi.authServer.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {

   @Query("from Account where userName=?1")
   Account findByUserName(String userName);
}
