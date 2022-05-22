package com.ssu.commerce.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class SsuCommerceDataSource {

    @Autowired
    private DataSourceProperties dataSourceProperties;

}
