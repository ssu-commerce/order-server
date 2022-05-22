package com.ssu.commerce.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.vault.annotation.VaultPropertySource;
import org.springframework.vault.annotation.VaultPropertySources;

@Profile("!test")
@Configuration
@VaultPropertySources({
        @VaultPropertySource(value = "ssu-commerce-auth/${spring.profiles.active:local}", propertyNamePrefix = "ssu-commerce-auth."),
        @VaultPropertySource(value = "ssu-commerce-auth/dev", propertyNamePrefix = "ssu-commerce-auth.")
})
public class DataSourceProperties {
    @Value("${ssu-commerce-auth.dataSource}")
    public String dataSource;

    @Value("${ssu-commerce-auth.userId}")
    public String userId;

    @Value("${ssu-commerce-auth.password}")
    public String password;

    @Value("${ssu-commerce-auth.driverClassName}")
    public String  driverClassName;
}
