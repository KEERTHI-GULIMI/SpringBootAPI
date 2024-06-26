package com.siemens.SpringBootAPI.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect

public class LoggingAop {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAop.class);


    @Before("execution(* com.siemens.SpringBootAPI.service.ProductService.getAllProducts(..))")
    public void logMethodCall(JoinPoint jp) {
        logger.info("method called" + jp.getSignature().getName());
    }

    @AfterThrowing("execution(* com.siemens.SpringBootAPI.service.ProductService.getProductById(..))")
    public void logMethodAfter(JoinPoint jp) {
        logger.info("Given id is not present  " + jp.getSignature().getName());
    }

    @AfterReturning("execution(* com.siemens.SpringBootAPI.service.ProductService.getProductById(..))")
    public void logMethodCall1(JoinPoint jp) {
        logger.info("method executed " + jp.getSignature().getName());
    }


}
