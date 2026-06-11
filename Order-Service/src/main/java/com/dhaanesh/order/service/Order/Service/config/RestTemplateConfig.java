package com.dhaanesh.order.service.Order.Service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfig {

    @Bean
    @LoadBalanced  // if we want to call the other service with the name registered in eureka server
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
