package com.dss.gateway.config;

import com.dss.gateway.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("admin-service", r -> r.path("/dss/api/admin/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://admin-service"))
                .route("auth-service", r -> r.path("/dss/api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .route("movie-service", r -> r.path("/dss/api/movie/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://movie-service"))
                .route("actor-service", r -> r.path("/dss/api/actor/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://actor-service"))
                .route("review-service", r -> r.path("/dss/api/review/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://review-service"))
                .build();
    }

}
