package com.tgrajkowski.service;

import com.tgrajkowski.service.mapper.WebShopConfig;
import com.tgrajkowski.model.mail.Mail;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.model.dao.ShortDescriptionDao;
import com.tgrajkowski.model.model.dao.SubscriberDao;
import com.tgrajkowski.model.newsletter.ConfirmDto;
import com.tgrajkowski.model.newsletter.RandomString;
import com.tgrajkowski.model.newsletter.Subscriber;
import com.tgrajkowski.model.newsletter.SubscriberDto;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.ProductDto;
import com.tgrajkowski.service.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

@Service
public class NewsletterService {

    @Autowired
    private SubscriberDao subscriberDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    private ShortDescriptionDao shortDescriptionDao;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WebShopConfig webShopConfig;

    public boolean subscribe(SubscriberDto subscriberDto) {
        if (subscriberDao.findByEmail(subscriberDto.getEmail()) == null) {
            Date dateSubscription = new Date();
            String generatedTicket = generateCode();
            Subscriber subscriber = new Subscriber(subscriberDto.getName(), subscriberDto.getEmail());
            subscriber.setLastNewsletterSend(dateSubscription);
            subscriber.setConfirm(false);
            subscriber.setConfirmCode(generatedTicket);
            subscriberDao.save(subscriber);
            sendEmailConfirmSubscription(subscriberDto.getEmail(), subscriberDto.getName(), generatedTicket, dateSubscription);
            return true;
        }
        return false;
    }

    private String generateCode() {
        String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
        RandomString tickets = new RandomString(23, new SecureRandom(), easy);
        return tickets.nextString();
    }

    public void sendEmailConfirmSubscription(String email, String username, String confirmCode, Date dateSubscription) {
        String subject = "Computer WebShop Newsletter";
        Mail mail = new Mail(email, subject);
        mail.setWelcome("Welcome in Computer WebShop newsletter");
        mail.setUserName(username);
        mail.setConfirmCode(confirmCode);
        mail.setCreateDate(dateSubscription);

        mail.setLinkConfirm("newsletter/confirm?email=" + email + "&code-confirm=" + confirmCode);
        mail.setExplain("You or someone has subscribed to this list on " + mail.getCreateDate() + " using the address " + mail.getMailTo());
        mail.setMessage("If you want to receive 10% discount in Computer WebShop please confirm this email");
        mail.setTemplate("newsletter");
        mail.setFragment("confirm");
        simpleEmailService.sendMail(mail);
    }

    public void sendNewsletterToSubscriber(String email, String name, List<ProductDto> newProductOffer) {
        LocalDate localDate = LocalDate.now();
        String subject = messageSource.getMessage("messager-subject-newsletter-mail", new Object[]{}, Locale.US) + " " + localDate;
        String message = "Dear " + name + " please browse our new product offer";
        Mail mail = new Mail(email, subject);
        mail.setWelcome("Welcome subscriber");
        mail.setMessage(message);
        mail.setNewProductOffer(newProductOffer);
        mail.setTemplate("newsletter");
        mail.setFragment("scheduled");
        simpleEmailService.sendMail(mail);
    }


    public ConfirmDto confirmEmail(ConfirmDto confirmDto) {
        Optional<Subscriber> subscriber =
                Optional.ofNullable(subscriberDao.findByEmail(confirmDto.getEmail()));
        if (subscriber.isPresent()) {
            if (subscriber.get().getConfirmCode().equals(confirmDto.getConfirmCode())
                    && subscriber.get().isConfirm() == false) {

                subscriber.get().setConfirm(true);
                String ticket = generateCode();
                subscriber.get().setCode(ticket);
                confirmDto.setDiscount(true);
                confirmDto.setDiscountCode(ticket);
                subscriberDao.save(subscriber.get());
                return confirmDto;
            }
        }
        confirmDto.setDiscount(false);
        return confirmDto;
    }

    public boolean sendNewsLetter() {
        List<Subscriber> subscribers = subscriberDao.findAll();
        for (Subscriber subscriber : subscribers) {
            Date dateSubscription = subscriber.getLastNewsletterSend();
            List<Product> productList = productDao.findByLastModificationAfter(dateSubscription);
            List<ProductDto> newProductOffer = new ArrayList<>();
            if (productList.size() > 0) {
                for (Product product : productList) {
                    if (product.getStatus().getCode().equals("sale")) {
                        ProductDto productDto = productMapper.mapToProductDtoForMail(product);


//                        List<ShortDescription> descriptions = shortDescriptionDao.findByProductId(product.getId());
//                        productDto.setShortDescription(descriptions.stream()
//                                .map(x -> x.getAttribute())
//                                .collect(Collectors.toList()));
                        newProductOffer.add(productDto);
                    }
                }
                if (newProductOffer.size() > 0) {
                    subscriber.setLastNewsletterSend(new Date());
                    subscriberDao.save(subscriber);
                    sendNewsletterToSubscriber(subscriber.getEmail(), subscriber.getName(), newProductOffer);
                }
            }
        }
        return true;
    }
}
