package com.auto_ds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Logger logger(InjectionPoint injectionPoint) {
        Class<?> cl = injectionPoint.getMember().getDeclaringClass();
        return LoggerFactory.getLogger(cl);
    }

}
