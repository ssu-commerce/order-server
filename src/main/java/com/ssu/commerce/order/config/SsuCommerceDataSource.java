package com.ssu.commerce.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Profile("!test")
@Configuration
public class SsuCommerceDataSource {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Primary
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(dataSourceProperties.driverClassName)
                .url(dataSourceProperties.dataSource)
                .username(dataSourceProperties.userId)
                .password(dataSourceProperties.password)
                .build();
//                .also { LoggerFactory.getLogger(SsuCommerceDataSource::class.java).info("\uD83D\uDCC2 SSUCommerce Auth DataSource 접속이 되었습니다.") }
    }

}
