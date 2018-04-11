package com.tgrajkowski.service;

import com.tgrajkowski.model.mail.Mail;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.model.dao.SubscriberDao;
import com.tgrajkowski.model.newsletter.RandomString;
import com.tgrajkowski.model.newsletter.Subscriber;
import com.tgrajkowski.model.newsletter.SubscriberDto;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.ProductDto;
import com.tgrajkowski.model.product.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NewsletterService {

    @Autowired
    private SubscriberDao subscriberDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private ProductMapper productMapper;

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
        for (Subscriber subscriber : subscribers) {
            Date dateSubscription = subscriber.getLastNewsletterSend();
            List<Product> productList = productDao.findByLastModificationAfter(dateSubscription);
            List<String> productTitle = new ArrayList<>();
            if(productList.size()>0) {
                System.out.println("Start newsletter user: " + subscriber.getName() + " email: " + subscriber.getEmail() + " start date subscription: " + subscriber.getLastNewsletterSend());

                for (Product product : productList) {
                    if(product.getStatus().getCode().equals("sale")){
                        productTitle.add(product.getTitle());
                        System.out.println("Product: " + product.getId() + " " + product.getTitle() + " " + product.getLastModification());
                    }
                }
                subscriber.setLastNewsletterSend(new Date());
                subscriberDao.save(subscriber);

                sendNewsletterToSubscriber(subscriber.getEmail(), subscriber.getName(), productTitle);
            }
        }

        return true;
    }

    public void sendNewsletterToSubscriber(String email, String name, List<String> productList) {
        LocalDate localDate = LocalDate.now();
        String subject = "Dear user: ";
        String message = "Please browse our new product offer";
        send(email, subject, message);
    }

    public void send(String email, String subject, String message) {
        simpleEmailService.send(new Mail(
                email,
                subject,
                message
        ));
    }
}
