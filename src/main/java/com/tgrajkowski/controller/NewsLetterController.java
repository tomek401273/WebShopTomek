package com.tgrajkowski.controller;

import com.tgrajkowski.model.File;
import com.tgrajkowski.model.newsletter.SubscriberDto;
import com.tgrajkowski.service.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(method = RequestMethod.GET, value = "/task/{name}")
    public void getFile(@PathVariable(value = "name", required = true) String name) {
        System.out.println("Name name: "+name);
    }
}
