package org.lybaobei.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ms")
public class ApplicationProperties {

    private RedisConfig redisConfig;

    public RedisConfig getRedisConfig() {
        return redisConfig;
    }

    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    public static class RedisConfig {
        private String address;
        private int database = 0;

        private String password;
        private int connectionMinimumIdleSize=32;
        private int connectionPoolSize=64;
        private int connectTimeout=10000;
        private int pingConnectionInterval=500;
        private int pingTimeout=10000;
        private int timeout=10000;

        private String host;
        private int port;
        private short isCluster=0; //是否开启集群 0 关闭 1开启

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getConnectionMinimumIdleSize() {
            return connectionMinimumIdleSize;
        }

        public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
            this.connectionMinimumIdleSize = connectionMinimumIdleSize;
        }

        public int getConnectionPoolSize() {
            return connectionPoolSize;
        }

        public void setConnectionPoolSize(int connectionPoolSize) {
            this.connectionPoolSize = connectionPoolSize;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public int getPingConnectionInterval() {
            return pingConnectionInterval;
        }

        public void setPingConnectionInterval(int pingConnectionInterval) {
            this.pingConnectionInterval = pingConnectionInterval;
        }

        public int getPingTimeout() {
            return pingTimeout;
        }

        public void setPingTimeout(int pingTimeout) {
            this.pingTimeout = pingTimeout;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public short getIsCluster() {
            return isCluster;
        }

        public void setIsCluster(short isCluster) {
            this.isCluster = isCluster;
        }
    }
}
