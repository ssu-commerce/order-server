package com.ssu.commerce.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class DataSourceProperties {
}
