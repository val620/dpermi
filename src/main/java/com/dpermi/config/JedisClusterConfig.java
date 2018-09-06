package com.dpermi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.*;

/**
 * Created by val620@126.com on 2017/11/3.
 */
@Configuration
@ConditionalOnClass({JedisCluster.class})
@EnableConfigurationProperties(RedisConfig.class)
public class JedisClusterConfig {
    @Autowired
    private RedisConfig redisConfig;
    private static JedisCluster jedis;

    @Bean
    public JedisCluster getJedisCluster() {
        String[] serverArray = redisConfig.getNodes().split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        for (String ipPort: serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(),Integer.valueOf(ipPortPair[1].trim())));
        }
        if(jedis==null){
            jedis=new JedisCluster(nodes, redisConfig.getTimeout());
        }
        return jedis;
    }
}
