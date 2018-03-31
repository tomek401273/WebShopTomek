package com.tgrajkowski.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.tgrajkowski.controller", "com.tgrajkowski.model.model.dao","com.tgrajkowski.service","com.tgrajkowski.model","com.tgrajkowski.app"})
@EntityScan(basePackages = "com.tgrajkowski.model")
@EnableJpaRepositories("com.tgrajkowski.model.model.dao")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
