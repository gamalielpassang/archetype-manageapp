package com.sprintpay.kyc.conformity.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PcsInitializerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcsInitializerApplication.class, args);
    }

}
