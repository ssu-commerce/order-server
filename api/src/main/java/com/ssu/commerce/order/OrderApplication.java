package com.ssu.commerce.order;

import com.ssu.commerce.core.configs.EnableSsuCommerceCore;
import com.ssu.commerce.vault.config.EnableSsuCommerceVault;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSsuCommerceVault
@EnableSsuCommerceCore
@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
