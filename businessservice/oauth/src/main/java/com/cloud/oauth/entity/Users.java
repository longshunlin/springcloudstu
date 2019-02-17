package com.cloud.oauth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Description
 * @Author longshunlin
 * @Date 2019/1/29
 */
@Entity
public class Users {

    @Id
    @Column(length = 50)
    private String username;

    @Column(length = 500)
    private String password;

    @Column(columnDefinition = " default 0")
    private Boolean enabled;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
