package br.com.apirest.leadersofts.leadcapture.infrastructure.datasource;

import br.com.apirest.leadersofts.leadcapture.infrastructure.converter.LeadConverter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadDTO;
import br.com.apirest.leadersofts.leadcapture.infrastructure.repository.RedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RedisDataSource implements RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDataSource.class);

    public RedisDataSource(@Qualifier("redisTemplateLead") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void adicionarCache(String prefixo, String chave, Object valor, Long ttl) {
        try {
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            if(valor instanceof LeadDTO && LeadDTO.class.isInstance(valor)) {
                valueOperations.set(prefixo.concat(chave),LeadDTO.class.cast(valor));
                valueOperations.set("lead",(LeadDTO) valor);
            } else {
                valueOperations.set(prefixo.concat(chave), objectMapper.writeValueAsString(valor));
            }
            LOGGER.info("Caching leads :: {}");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            LOGGER.info("finalizando!");
        }

    }

    @Override
    public <T> Object obterCache(String prefixo, String chave) {
        var caching = redisTemplate.opsForValue().get(chave);
//        return Optional.ofNullable( objectMapper.readValue(caching) );
        if(Objects.nonNull(caching)) {
            LinkedHashMap cache = new LinkedHashMap();
            cache.put("key",caching);
            return LeadConverter.getLeadRecordFromCache(cache);
        }
        return null;
    }

    @Override
    public <T> Optional<List<T>> obterCacheList(String prefixo, String chave, Class<T> classe) {
        return Optional.empty();
    }

    @Override
    public void removerCache(String key) {

    }

    @Override
    public void removerCacheRegex(String key) {

    }

    @Override
    public Long getExpireTtl(String prefixo, String chave) {
        return 0L;
    }
}
