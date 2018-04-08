package com.tgrajkowski.controller;

import com.tgrajkowski.model.newsletter.SubscriberDto;
import com.tgrajkowski.service.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsletter")
@CrossOrigin("*")
public class NewsLetterController {
    @Autowired
    private NewsletterService newsletterService;

    @RequestMapping(method = RequestMethod.POST, value = "/subscribe")
    public @ResponseBody boolean subscrive(@RequestBody SubscriberDto subscriberDto) {
      return newsletterService.subscribe(subscriberDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/send")
    public @ResponseBody boolean sendNewsletter() {
       return newsletterService.sendNewsLetter();
    }
}
