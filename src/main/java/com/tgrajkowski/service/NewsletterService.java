package com.tgrajkowski.service;

import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.model.dao.SubscriberDao;
import com.tgrajkowski.model.newsletter.RandomString;
import com.tgrajkowski.model.newsletter.Subscriber;
import com.tgrajkowski.model.newsletter.SubscriberDto;
import com.tgrajkowski.model.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

@Service
public class NewsletterService {

    @Autowired
    private SubscriberDao subscriberDao;

    @Autowired
    private ProductDao productDao;

    public boolean subscribe(SubscriberDto subscriberDto) {
        System.out.println(subscriberDao.findByEmail(subscriberDto.getEmail()));
        if (subscriberDao.findByEmail(subscriberDto.getEmail()) == null) {


            String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
            RandomString tickets = new RandomString(23, new SecureRandom(), easy);
            String generatedTicket = tickets.nextString();
            System.out.println("generatedTicket: " + generatedTicket);
            Subscriber subscriber = new Subscriber(subscriberDto.getName(), subscriberDto.getEmail(), generatedTicket);
            subscriber.setLastNewsletterSend(new Date());
            subscriberDao.save(subscriber);

            return true;
        }
        return false;
    }

    public boolean sendNewsLetter() {
        List<Subscriber> subscribers = subscriberDao.findAll();
        System.out.println("Peparing to send email");
        for (Subscriber subscriber : subscribers) {
            Date dateSubscription = subscriber.getLastNewsletterSend();
            List<Product> productList = productDao.findByLastModificationAfter(dateSubscription);
            System.out.println("Dowloaded data from database");
            if(productList.size()>0) {
                System.out.println("Start newsletter user: " + subscriber.getName() + " email: " + subscriber.getEmail() + " start date subscription: " + subscriber.getLastNewsletterSend());
                for (Product product : productList) {
                    if(product.getStatus().getCode().equals("sale")){
                        System.out.println("Product: " + product.getId() + " " + product.getTitle() + " " + product.getLastModification());
                    }
                }
                subscriber.setLastNewsletterSend(new Date());
                subscriberDao.save(subscriber);
                System.out.println("end newsLetter this user");
            } else {
                System.out.println("Abort send email nothing found new in database");
            }
        }

        return true;
    }
}
