package com.tgrajkowski.service;

import com.tgrajkowski.service.mapper.WebShopConfig;
import com.tgrajkowski.model.mail.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    private WebShopConfig webShopConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String createMailMessage(Mail mail) {
        Context context = new Context();
        context.setVariable("welcomeMessage", mail.getWelcome());
        context.setVariable("message", mail.getMessage());
        context.setVariable("confirmationLink", webShopConfig.getFrontedLink() + mail.getLinkConfirm());
        context.setVariable("explain", mail.getExplain());
        context.setVariable("companyConfig", webShopConfig);
        context.setVariable("newOffer", mail.getNewProductOffer());
        context.setVariable("productUpdated", mail.getProductDto());
        context.setVariable("template", mail.getTemplate());
        context.setVariable("fragment", mail.getFragment());
        return templateEngine.process("web-shop-mail", context);
    }
}
