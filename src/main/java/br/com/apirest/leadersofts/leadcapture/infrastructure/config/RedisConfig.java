package br.com.apirest.leadersofts.leadcapture.infrastructure.config;

import br.com.apirest.leadersofts.leadcapture.infrastructure.config.properties.CacheProperties;
import br.com.apirest.leadersofts.leadcapture.infrastructure.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;

import static io.lettuce.core.ReadFrom.REPLICA_PREFERRED;
import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;
import static java.time.Duration.*;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    public static final String MCC = "MCC";

    private final CacheProperties cacheProperties;

    @Value("{spring.redis.host}")
    private String hostName;


    public RedisConfig(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Override
    public CacheErrorHandlerConfig errorHandler() {
        return new CacheErrorHandlerConfig();
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(REPLICA_PREFERRED)
                .commandTimeout(Duration.ofSeconds(cacheProperties.getCommandTimeout()))
                .build();
        RedisStaticMasterReplicaConfiguration serverConfig =
                new RedisStaticMasterReplicaConfiguration(cacheProperties.getPrimaryHostName(),
                        cacheProperties.getPort());
        serverConfig.addNode(cacheProperties.getReadHostName(), cacheProperties.getPort());
        return new LettuceConnectionFactory(serverConfig, clientConfig);

    }

    @Bean
    public RedisTemplate<String, Object> redisTemplateLead() {
//        return RedisUtils.createRedisTemplate(redisConnectionFactory());
        return RedisUtils.getRedisTemplate(redisConnectionFactory());
    }

    @Bean
    RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return builder -> {
            var configurationMap = new HashMap<String, RedisCacheConfiguration>();
            configurationMap.putIfAbsent("",defaultCacheConfig().entryTtl(ofDays(10L)) );
            configurationMap.put(MCC, defaultCacheConfig());
            builder.withInitialCacheConfigurations(configurationMap);
        };
    }

}
