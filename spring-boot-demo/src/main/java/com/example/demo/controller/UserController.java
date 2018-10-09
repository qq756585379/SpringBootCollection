package com.example.demo.controller;

import com.example.demo.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 有时候属性太多了，一个个绑定到属性字段上太累,官方提倡绑定一个对象的bean，这里我们建一个ConfigBean.java类，
 * 顶部需要使用注解@ConfigurationProperties(prefix = “com.dudu”)来指明使用哪个
 */
@RestController
public class UserController {

    @Autowired
    ConfigBean configBean;

    @RequestMapping("/")
    public String hexo() {
        return configBean.getName() + configBean.getWant();
    }

}
