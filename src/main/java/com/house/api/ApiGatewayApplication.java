package com.house.api;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
@EnableApolloConfig
public class ApiGatewayApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}

	
