package com.springboot.dao;

import com.springboot.domain.dao.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户服务数据接口类
 * 我们要实现一个增加、删除、修改、查询功能的持久层服务，那么我只需要声明一个接口，
 * 这个接口继承org.springframework.data.repository.Repository<T, ID> 接口或者他的子接口就行。
 * 这里为了功能的完备，我们继承了 org.springframework.data.jpa.repository.JpaRepository<T, ID> 接口。
 * 其中 T 是数据库实体类，ID 是数据库实体类的主键。然后再简单的在这个接口上增加一个 @Repository 注解就结束了。
 */
@Repository
public interface UserDao extends JpaRepository<UserDO, Long> {

    UserDO findByAccount(String account);

    List<UserDO> findByIdGreaterThan(Long id);

    //这里是用 PQL 的语法来定义一个查询。其中两个参数名字有语句中的 : 后面的支付来决定
    @Query("SELECT U FROM UserDO U ,RoleUserDO RU WHERE U.id = RU.userId AND RU.roleId = :roleId")
    List<UserDO> findUsersByRole(@Param("roleId") Long roleId);

    //@Query 注解中增加一个 nativeQuery = true 的属性，就可以采用原生 SQL 语句的方式来编写查询。
    @Query(nativeQuery = true, value = "SELECT * FROM AUTH_USER WHERE name = :name1  OR name = :name2 ")
    List<UserDO> findSQL(@Param("name1") String name1, @Param("name2") String name2);

}
