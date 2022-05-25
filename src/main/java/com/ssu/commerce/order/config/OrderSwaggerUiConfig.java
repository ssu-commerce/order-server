package com.ssu.commerce.order.config;

import com.ssu.commerce.core.configs.SwaggerDocsConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class OrderSwaggerUiConfig implements SwaggerDocsConfig {

    @NotNull
    @Override
    public String getBasePackage() {
        return "com.ssu.commerce.order";
    }

    @NotNull
    @Override
    public String getTitle() {
        return "order api";
    }

    @NotNull
    @Override
    public String getVersion() {
        return "1.0";
    }
}
