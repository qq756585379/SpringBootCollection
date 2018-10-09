package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "com.dudu")
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class ConfigBean {

    private String name;

    private String want;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWant() {
        return want;
    }

    public void setWant(String want) {
        this.want = want;
    }
}
