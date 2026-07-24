package br.com.apirest.leadersofts.leadcapture.infrastructure.config.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties(prefix = "cache")
@Data
public class CacheProperties {

    @NotNull(message = "cache.primaryHostName nao pode ser nulo")
    private String primaryHostName;

    @NotNull
    private int port;

    @NotNull(message = "cache.ttlDefault nao pode ser nulo")
    private Long ttlDefault;

    @NotNull(message = "cache.commandTimeout nao pode ser nulo")
    private Long commandTimeout;

    @NotNull(message = "cache.readHostName nao pode ser nulo")
    private String readHostName;

}
