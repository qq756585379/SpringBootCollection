package com.springboot.dao;

import com.springboot.domain.dao.RoleUserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户服务数据接口类
 */

@Repository
public interface RoleUserDao extends JpaRepository<RoleUserDO, Long> {

    void deleteByRoleId(Long roleId);

    void deleteByUserId(Long userId);

}
