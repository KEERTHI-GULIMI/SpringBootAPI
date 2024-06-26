package com.siemens.SpringBootAPI.AOP;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Validation {

    private static final Logger logger = LoggerFactory.getLogger(Validation.class);

    @Around("execution(* com.siemens.SpringBootAPI.service.ProductService.getProductById(..)) && args(pId) || execution(* com.siemens.SpringBootAPI.service.ProductService.deleteProduct(..)) && args(pId)")
    public Object ValidateAndUpdate(ProceedingJoinPoint jp, Integer pId) throws Throwable {

        if (pId < 0) {
            logger.info("product Id is negative,updating it");
            pId = -pId;
            logger.info("new value is " + pId);

        }
        Object obj = jp.proceed(new Object[]{pId});

        return obj;
    }
}
