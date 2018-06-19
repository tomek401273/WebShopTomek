package com.tgrajkowski.service.scheduler;

import com.tgrajkowski.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderDeliveryScheduler {

    @Autowired
    private BuyService buyService;

    @Scheduled(fixedDelay = 60 * 1000)
    public void orderDeliver() {
        buyService.orderDeliver();
    }
}
