package com.scf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author Naseem
 *
 */
@SpringBootApplication
@EnableSwagger2
public class SCFIMOnboarding 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(SCFIMOnboarding.class, args);
	}
}
