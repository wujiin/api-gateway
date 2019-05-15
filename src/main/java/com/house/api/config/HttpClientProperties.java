package com.house.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.httpclient")
@Data
public class HttpClientProperties {

    private Integer connectTimeOut = 10000;
    private Integer socketTimeOut = 1000000;
    private String agent = "agent";
    private Integer maxConnPerRoute = 10;
    private Integer maxConnTotaol = 50;

}
