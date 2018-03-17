package com.springboot.service;

import com.springboot.domain.dao.UserDO;

import java.util.List;

/**
 * 用户服务层接口
 */
public interface UserService {

    UserDO add(UserDO user);

    UserDO update(UserDO user);

    boolean delete(Long id);

    UserDO locate(Long id);

    List<UserDO> matchName(String name);
}
