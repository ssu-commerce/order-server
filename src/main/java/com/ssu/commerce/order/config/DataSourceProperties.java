package com.ssu.commerce.order.config;

import com.ssu.commerce.core.configs.AbstractDataSourceProperties;
import org.jetbrains.annotations.NotNull;
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
public class DataSourceProperties implements AbstractDataSourceProperties {
    @Value("${ssu-commerce-order.dataSource}")
    public String dataSource;

    @Value("${ssu-commerce-order.userId}")
    public String userId;

    @Value("${ssu-commerce-order.password}")
    public String password;

    @Value("${ssu-commerce-order.driverClassName}")
    public String  driverClassName;


    @NotNull
    @Override
    public String getDataSource() {
        return this.dataSource;
    }

    @Override
    public void setDataSource(@NotNull String s) {

    }

    @NotNull
    @Override
    public String getDriverClassName() {
        return this.driverClassName;
    }

    @Override
    public void setDriverClassName(@NotNull String s) {

    }

    @NotNull
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(@NotNull String s) {

    }

    @NotNull
    @Override
    public String getProjectName() {
        return "order";
    }

    @Override
    public void setProjectName(@NotNull String s) {

    }

    @NotNull
    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(@NotNull String s) {

    }
}
