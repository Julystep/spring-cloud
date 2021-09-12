package com.boomshair.mainentrance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MainEntranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainEntranceApplication.class, args);
    }

}
