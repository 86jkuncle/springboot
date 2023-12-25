package org.lybaobei.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.crazycake.shiro.exception.SerializationException;
import org.crazycake.shiro.serializer.MultiClassLoaderObjectInputStream;
import org.crazycake.shiro.serializer.RedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * https://alexxiyang.github.io/shiro-redis/#configurable-options
 * Serializer
 *
 * https://github.com/alexxiyang/shiro-redis/issues
 */
public class MyShiroRedisSerializer implements RedisSerializer<Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MyShiroRedisSerializer() {
    }

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        byte[] result = new byte[0];
        if (object == null) {
            return result;
        }

        try {
            result = objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        Object result = null;
        if (bytes != null && bytes.length != 0) {
            try {
                result = objectMapper.readValue(bytes, Object.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            return result;
        }

        return result;
    }
}
