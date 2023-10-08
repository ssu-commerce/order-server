package com.ssu.commerce.order.filter;

import com.ssu.commerce.core.security.config.UrlPermissionFilter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

@Configuration
public class OrderUrlPermissionFilter implements UrlPermissionFilter {

    @NotNull
    @Override
    public ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlPermissions(
            @NotNull ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests
    ) {
        return authorizeRequests
                .anyRequest().authenticated();
    }
}
