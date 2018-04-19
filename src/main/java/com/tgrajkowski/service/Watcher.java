package com.tgrajkowski.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Aspect
@Component
public class Watcher {
    private final Logger LOGGER = LoggerFactory.getLogger(Watcher.class);

    @Before("execution(* com.tgrajkowski.service.Calculator.factorial(..))&& args(theNumber) && target(object)")
    public void logEvent(BigDecimal theNumber, Object object) {
        LOGGER.info("Loggin the event");
        LOGGER.info("class: " + object.getClass().getName() + ", Args: " + theNumber);
    }

    @Around("execution(* com.tgrajkowski.service.*.*(..)) && target(object)")
    public Object measureTime(final ProceedingJoinPoint proceedingJoinPoint, Object object) throws Throwable {
        Object result;
        try {
            long begin = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();
            LOGGER.info("Time consumed: " + (end - begin) + "[ms], class: " + object.getClass().getName());
        } catch (Throwable throwable) {
            LOGGER.error(throwable.getMessage());
            throw throwable;
        }
        return result;
    }
}
