package org.lybaobei.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @Description java类的作用
* @Author mtxst
* @Date 2024/5/13 6:01 $
*/
@Configuration
public class JackJsonConfig {
    @Value("${spring.jackson.local-date-time-format}")
    String localDateTimeFormat;

    @Value("${spring.jackson.local-date-format}")
    String localDateFormat;

    @Value("${spring.jackson.local-time-format}")
    String localTimeFormat;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.serializationInclusion(JsonInclude.Include.NON_NULL)
            .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(localDateTimeFormat)))
            .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .deserializerByType(LocalDateTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(localDateTimeFormat)));
    }
}
