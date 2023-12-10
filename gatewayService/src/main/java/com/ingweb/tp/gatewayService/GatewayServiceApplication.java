package com.ingweb.tp.gatewayService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}
	@Bean
	RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route( r -> r.path("/employes/**")
						.uri("lb://employeMicroService"))
				.route(r -> r.path("/projects/**")
						.uri("lb://projectService/"))
				.route(r -> r.path("/salaries/**")
						.uri("lb://salaryService/"))

				.build();
	}
}
