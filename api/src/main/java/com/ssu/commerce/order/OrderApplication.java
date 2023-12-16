package com.ssu.commerce.order;

import com.ssu.commerce.core.web.configs.EnableSsuCommerceCore;
import com.ssu.commerce.vault.config.EnableSsuCommerceVault;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableSsuCommerceVault
@EnableSsuCommerceCore
@EnableFeignClients
@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
