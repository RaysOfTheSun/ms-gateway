package com.learning.msgateway.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "integration")
public class IntegrationConfiguration {
    Map<String, String> serviceNames;
}
