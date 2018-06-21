package com.tgrajkowski.service;

import com.tgrajkowski.app.WebShopConfig;
import com.tgrajkowski.model.mail.Mail;
import com.tgrajkowski.model.mail.MailType;
import com.tgrajkowski.model.product.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;

import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private WebShopConfig webShopConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    private String welcome;
    private String godbye = "Have a nice day";

    public String create(Mail mail) {
        Context context = new Context();
        context.setVariable("companyConfig", webShopConfig);
        context.setVariable("godbyeMessage", godbye);
        if (mail.getMailType().equals(MailType.SCHEDULED_NEWSLETTER)) {
            return scheduledNewsletterEmail(mail, context);
        }
        if(mail.getMailType().equals(MailType.CONFIRM_NEWSLETTER)) {
            return confirmNewsletter(mail, context);
        }
        return templateEngine.process("mail/newsletter-mail", context);
    }

    private String scheduledNewsletterEmail(Mail mail, Context context) {
        List<ProductDto> newOffer = mail.getNewProductOffer();
        welcome = "Welcome subscriber";
        context.setVariable("message", mail.getMessage());
        context.setVariable("welcomeMessage", welcome);
        context.setVariable("newOffer", mail.getNewProductOffer());
        return templateEngine.process("mail/newsletter-mail", context);
    }

    private String confirmNewsletter(Mail mail, Context context) {
        welcome ="Welcome in Computer WebShop newsletter";
        String confirmationLink = "http://localhost:4200/newsletter/confirm?email="+mail.getMailTo()+"&code-confirm="+mail.getConfirmCode();
        String explain = "You or someone has subscribed to this list on "+mail.getCreateDate()+" using the address "+mail.getMailTo();
        String message = "If you want to receive 10% discount in Computer WebShop please confirm this email";
        context.setVariable("welcomeMessage", welcome);
        context.setVariable("message", message);
        context.setVariable("confirmationLink", confirmationLink);
        context.setVariable("explain", explain);
        return templateEngine.process("/mail/confim-newsletter", context);
    }

    public String createMailMessage(Mail mail) {
        Context context = new Context();
        context.setVariable("welcomeMessage", mail.getWelcome());
        context.setVariable("message", mail.getMessage());
        context.setVariable("confirmationLink", mail.getLinkConfirm());
        context.setVariable("explain", mail.getExplain());
        context.setVariable("godbyeMessage", mail.getGoodbye());
        context.setVariable("companyConfig", webShopConfig);
        context.setVariable("newOffer", mail.getNewProductOffer());
        context.setVariable("template", mail.getTemplate());
        context.setVariable("fragment", mail.getFragment());
        return templateEngine.process("/web-shop-mail", context);
    }
}
