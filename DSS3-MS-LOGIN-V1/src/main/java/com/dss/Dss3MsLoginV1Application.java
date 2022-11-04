package com.dss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Dss3MsLoginV1Application {
    public static void main(String[] args) {
        SpringApplication.run(Dss3MsLoginV1Application.class, args);
    }

}
