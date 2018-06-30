package com.tgrajkowski.service.mapper;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class WebShopConfig {
    @Value("${info.app.name}")
    private String companyName;

    @Value("${info.company.goal}")
    private String companyGoal;

    @Value("${info.company.email}")
    private String companyEmail;

    @Value("${info.company.phone}")
    private String companyPhone;

    @Value("${frontend.link}")
    private String frontedLink;

}
