package com.boomshair.springpicture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringPictureApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPictureApplication.class, args);
    }

}

