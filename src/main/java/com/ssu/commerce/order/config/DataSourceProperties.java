package com.ssu.commerce.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.vault.annotation.VaultPropertySource;
import org.springframework.vault.annotation.VaultPropertySources;

@Profile("!test")
@Configuration
@VaultPropertySources({
        @VaultPropertySource(value = "ssu-commerce-order/${spring.profiles.active:dev}", propertyNamePrefix = "ssu-commerce-order."),
        @VaultPropertySource(value = "ssu-commerce-order/dev", propertyNamePrefix = "ssu-commerce-order.")
})
public class DataSourceProperties {
    @Value("${ssu-commerce-order.dataSource}")
    public String dataSource;

    @Value("${ssu-commerce-order.userId}")
    public String userId;

    @Value("${ssu-commerce-order.password}")
    public String password;

    @Value("${ssu-commerce-order.driverClassName}")
    public String  driverClassName;
}
