package com.dss;

import com.dss.service.AdminService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class Dss8ClientV1Application {

	public static void main(String[] args) {
		ApplicationContext applicationContext =  SpringApplication.run(Dss8ClientV1Application.class, args);
		AdminService adminService = applicationContext.getBean("adminService", AdminService.class);
		System.out.println(adminService.doLogin("test3@gmail.com","P@ssw0rd"));
		System.out.println(adminService.getServiceInstance());
		System.out.println(adminService.doLogin("test3@gmail.com","P@ssw0rd"));
		System.out.println(adminService.getServiceInstance());
	}

}
