package com.tgrajkowski.app;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {
    @Value("${admin.mail}")
    private String adminmail;
    @Value("${admin.name}")
    private String adminName;
}
