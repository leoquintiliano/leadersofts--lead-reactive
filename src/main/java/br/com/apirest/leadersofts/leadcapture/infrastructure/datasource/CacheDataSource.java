package br.com.apirest.leadersofts.leadcapture.infrastructure.datasource;

import br.com.apirest.leadersofts.leadcapture.infrastructure.entities.LeadCache;
import br.com.apirest.leadersofts.leadcapture.infrastructure.repository.CacheRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CacheDataSource implements CacheRepository {

    private static final String CHAVE_DATASOURCE_HISTORICO = "DSDHIS";

    private static final String CHAVE_DATASOURCE_DETALHE = "DSDET";

    private final RedisTemplate<String,Object> redisTemplate;

    public CacheDataSource(@Qualifier("redisTemplateLead") RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void revogarCacheLeadPostado(List<LeadCache> leads) {
        var chavesDetalhe = leads.stream().map(
                lead -> CHAVE_DATASOURCE_DETALHE.concat(lead.getNome())).collect( Collectors.toList());

        var chavesHistorico = leads.stream().map(
                lead -> CHAVE_DATASOURCE_HISTORICO.concat(lead.getNome())).collect( Collectors.toList());


        this.redisTemplate.delete(chavesDetalhe);
        redisTemplate.delete(chavesHistorico);
    }
}
