package com.dpermi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.*;

/**
 * Created by val620@126.com on 2017/11/3.
 */
@Component
@ConfigurationProperties(prefix = "spring.redis.cache")
@Data
public class RedisConfig {
    private String nodes;
    private int timeout;
}
