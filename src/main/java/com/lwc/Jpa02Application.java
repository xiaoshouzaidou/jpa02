package com.lwc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Jpa02Application {

	public static void main(String[] args) {
		SpringApplication.run(Jpa02Application.class, args);
	}

}
