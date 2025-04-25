package com.shoppi.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.shoppi.common.entity","com.shoppi.admin.user"})
public class ShoppiBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppiBackEndApplication.class, args);
	}

}
