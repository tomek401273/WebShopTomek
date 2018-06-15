package com.tgrajkowski;

import com.tgrajkowski.service.StorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.Resource;
import java.util.Locale;

@SpringBootApplication
@ComponentScan({"com.tgrajkowski.controller", "com.tgrajkowski.model.model.dao","com.tgrajkowski.service","com.tgrajkowski.model","com.tgrajkowski.app"})
@EntityScan(basePackages = "com.tgrajkowski.model")
@EnableJpaRepositories("com.tgrajkowski.model.model.dao")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

//    @Resource
//    StorageService storageService;
//
//    @Bean
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
//        sessionLocaleResolver.setDefaultLocale(Locale.US);
//        System.out.println("Run run..");
//        storageService.deleteAll();
//        storageService.init();
//        return sessionLocaleResolver;
//    }
//
//    public void run() throws Exception {
//        System.out.println("Run run..");
//        storageService.deleteAll();
//        storageService.init();
//    }
}
