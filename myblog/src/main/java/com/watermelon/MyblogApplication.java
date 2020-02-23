package com.watermelon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.watermelon.entity")
@EnableJpaRepositories(basePackages="com.watermelon.DAO")
@ComponentScan({"com.watermelon.myblog","com.watermelon.service","com.watermelon.aspect"})
@SpringBootApplication(scanBasePackages = "com.watermelon")
public class MyblogApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return super.configure(builder);
	}
}

