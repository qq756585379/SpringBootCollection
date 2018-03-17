package com.springboot.domain.dto;

import com.springboot.domain.dao.UserDO;

/**
 * 用户登录业务对象类
 */
public class SecurityEntity {
    private String token;
    private Long exipre;
    private UserDO user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExipre() {
        return exipre;
    }

    public void setExipre(Long exipre) {
        this.exipre = exipre;
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }
}
