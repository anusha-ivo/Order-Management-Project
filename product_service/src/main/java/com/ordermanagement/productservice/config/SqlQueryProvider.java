package com.ordermanagement.productservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SqlQueryProvider {

    @Autowired
    private Environment environment;

    public String getQuery(String key) {
        return environment.getProperty(key);
    }
}