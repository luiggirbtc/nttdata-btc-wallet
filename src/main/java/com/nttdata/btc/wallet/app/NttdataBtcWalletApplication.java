package com.nttdata.btc.wallet.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class NttdataBtcWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(NttdataBtcWalletApplication.class, args);
	}

}