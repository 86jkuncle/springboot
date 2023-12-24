package org.lybaobei.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.lybaobei.config.ApplicationProperties.RedisConfig;

@Configuration
public class CommonConfigruation {

    private static final Logger log = LoggerFactory.getLogger(CommonConfigruation.class);

    private ApplicationProperties applicationProperties;

    @Autowired
    public CommonConfigruation(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean(name = "redisConfig")
    public RedisConfig redisConfig(){
        RedisConfig redisConfig = applicationProperties.getRedisConfig();
        log.info("redisConfig  ----");
        return redisConfig;
    }
}
