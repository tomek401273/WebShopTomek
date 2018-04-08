package com.tgrajkowski.app;

import com.tgrajkowski.model.newsletter.RandomString;
import org.junit.Test;

import java.security.SecureRandom;


public class RandomGeneratorSuite {

    @Test
    public void justTest() {
        String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
        RandomString tickets = new RandomString(23, new SecureRandom(), easy);
        System.out.println(tickets.nextString());
    }
}
