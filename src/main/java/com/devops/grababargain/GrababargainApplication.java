package com.devops.grababargain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EntityScan("com.devops.grababargain.model")
public class GrababargainApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrababargainApplication.class, args);
	}

}
