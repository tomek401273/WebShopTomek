package com.tgrajkowski.service.scheduler;

import com.tgrajkowski.service.NewsletterService;
import com.tgrajkowski.service.ProductService;
import com.tgrajkowski.service.SimpleEmailService;
import com.tgrajkowski.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired
    private NewsletterService newsletterService;

    @Autowired
    private ProductService productService;



    @Scheduled(cron = "0 0 00 * * *")
    public void sendInformationEmail() {
        newsletterService.sendNewsLetter();
    }


    //    @Scheduled(fixedDelay = 60 * 1000)
    @Scheduled(cron = "0 0 00 * * *")
    public void deleteProduct() {
        productService.deleteProductFromSale();
    }

}
