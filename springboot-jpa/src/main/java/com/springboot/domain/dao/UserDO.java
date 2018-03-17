package com.springboot.domain.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户实体类
 * 1.@Entity 是一个必选的注解，声明这个类对应了一个数据库表。
 * 2.@Table(name = "AUTH_USER") 是一个可选的注解。声明了数据库实体对应的表信息。包括表名称、索引信息等。
 * 这里声明这个实体类对应的表名是 AUTH_USER。如果没有指定，则表名和实体的名称保持一致。
 * 3.@Id 注解声明了实体唯一标识对应的属性。
 * 4.@Column(length = 32) 用来声明实体属性的表字段的定义。默认的实体每个属性都对应了表的一个字段。
 * 字段的名称默认和属性名称保持一致（并不一定相等）。字段的类型根据实体属性类型自动推断。这里主要是声明了字符字段的长度。
 * 如果不这么声明，则系统会采用 255 作为该字段的长度
 */
@Entity
@Table(name = "AUTH_USER")
public class UserDO {
    @Id
    private Long id;
    @Column(length = 32)
    private String name;
    @Column(length = 32)
    private String account;
    @Column(length = 64)
    private String pwd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
