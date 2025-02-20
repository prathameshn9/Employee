package com.Employee.Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.Employee.Management", "com.Employee.Management.util",
		"com.Employee.Management.controller", "com.Employee.Management.service" })
@EnableJpaRepositories(basePackages = "com.Employee.Management.repo")
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

}
