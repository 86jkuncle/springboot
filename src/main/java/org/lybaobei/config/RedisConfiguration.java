package org.lybaobei.config;

import jodd.util.StringUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.lybaobei.config.ApplicationProperties.RedisConfig;

@Configuration
public class RedisConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RedisConfiguration.class);

    private RedisConfig redisConfig;

    @Autowired
    public RedisConfiguration(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }


    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonSingle() {

        RedissonClient redissonClient=null;


        try {
            Config config = new Config();

            config.setCodec(new JsonJacksonCodec());

            if(redisConfig.getIsCluster()==1) {
                log.info("redisson Cluster start ");
                String[] nodes =redisConfig.getAddress().split(",");
                ClusterServersConfig serverConfig = config.useClusterServers();
                serverConfig.addNodeAddress(nodes);
                serverConfig.setKeepAlive(true);
                serverConfig.setPingConnectionInterval(redisConfig.getPingConnectionInterval());
                serverConfig.setTimeout(redisConfig.getTimeout());
                serverConfig.setConnectTimeout(redisConfig.getConnectTimeout());
                if(!StringUtil.isEmpty(redisConfig.getPassword())) {
                    serverConfig.setPassword(redisConfig.getPassword());
                }
            }else {
                log.info("redisson Single start ");
                SingleServerConfig serverConfig = config.useSingleServer()
                    .setAddress(redisConfig.getAddress())
                    .setDatabase(redisConfig.getDatabase());
                serverConfig.setKeepAlive(true);
                serverConfig.setPingConnectionInterval(redisConfig.getPingConnectionInterval());
                serverConfig.setTimeout(redisConfig.getTimeout());
                serverConfig.setConnectTimeout(redisConfig.getConnectTimeout());
                serverConfig.setConnectionMinimumIdleSize(redisConfig.getConnectionMinimumIdleSize());

                serverConfig.setConnectionPoolSize(redisConfig.getConnectionPoolSize());

                if(!StringUtil.isEmpty(redisConfig.getPassword())) {
                    serverConfig.setPassword(redisConfig.getPassword());
                }
            }
            redissonClient= Redisson.create(config);

            log.info("redisson create end ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return redissonClient;

    }

}
