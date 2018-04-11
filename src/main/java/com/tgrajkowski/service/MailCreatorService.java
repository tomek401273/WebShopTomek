package com.tgrajkowski.service;

import com.tgrajkowski.app.AdminConfig;
import com.tgrajkowski.app.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;


@Service
public class MailCreatorService {
    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    private String welcome = "Welcome welcome";
    private String godbye = "Have a nice day";

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        boolean isSchedulingEmail = false;

        if (message.contains("Currently")) {
            functionality.add("Today will be great day");
            functionality.add("you will accomplish all your scheduld task ");
            functionality.add("Yes you can !!!");
            isSchedulingEmail= true;
        } else {
            functionality.add("You can menanage your tasks");
            functionality.add("Previous connection with Trello Account");
            functionality.add("Application allow sending tasks to trello ");
        }

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://tomek401273.github.io/index.html");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        context.setVariable("companyConfig", companyConfig);
        context.setVariable("welcomeMessage", welcome);
        context.setVariable("godbyeMessage", godbye);
        context.setVariable("isScheduling", isSchedulingEmail);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildScheduleEmail(String message) {
        List<String> posibility = new ArrayList<>();
        posibility.add("Today will be great day");
        posibility.add("you will accomplish all your scheduld task ");
        posibility.add("Yes you can !!!");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://tomek401273.github.io/index.html");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", posibility);
        context.setVariable("companyConfig", companyConfig);
        context.setVariable("welcomeMessage", welcome);
        context.setVariable("godbyeMessage", godbye);


        return templateEngine.process("mail/scheduled-mail", context);
    }
}
