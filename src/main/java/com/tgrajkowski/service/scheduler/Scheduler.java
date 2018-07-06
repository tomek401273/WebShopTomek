package com.tgrajkowski.service.scheduler;

import com.tgrajkowski.service.NewsletterService;
import com.tgrajkowski.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    private NewsletterService newsletterService;

    @Autowired
    private ProductService productService;

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        newsletterService.sendNewsLetter();
    }

    @Scheduled(cron = "0 0 00 * * *")
    public void deleteProduct() {
        productService.deleteProductFromSale();
    }

}
