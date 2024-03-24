package com.ssu.commerce.order.config;

import com.ssu.commerce.core.jpa.config.AbstractDataSourceProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
@ConfigurationProperties("ssu-commerce-order")
public class DataSourceProperties implements AbstractDataSourceProperties {
    public String dataSource;
    public String userId;
    public String password;
    public String driverClassName;


    @NotNull
    @Override
    public String getDataSource() {
        return dataSource;
    }

    @Override
    public void setDataSource(@NotNull String s) {

    }

    @NotNull
    @Override
    public String getDriverClassName() {
        return driverClassName;
    }

    @Override
    public void setDriverClassName(@NotNull String s) {

    }

    @NotNull
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(@NotNull String s) {

    }

    @NotNull
    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(@NotNull String s) {

    }
}
