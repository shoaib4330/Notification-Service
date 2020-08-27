package com.vend.gateway.notification.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@ConditionalOnProperty(prefix = "vend.notification", name = "max-retries")
@EnableRetry
public class RetryConfig {
}
