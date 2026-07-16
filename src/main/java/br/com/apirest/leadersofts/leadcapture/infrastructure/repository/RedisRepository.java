package br.com.apirest.leadersofts.leadcapture.infrastructure.repository;

import java.util.List;
import java.util.Optional;

public interface RedisRepository {

    void adicionarCache(String prefixo, String chave, Object valor, Long ttl);

    <T> Object obterCache(String prefixo, String chave);

    <T> Optional<List<T>> obterCacheList(String prefixo, String chave, Class<T> classe);

    void removerCache(String key);

    void removerCacheRegex(String key);

    Long getExpireTtl(String prefixo, String chave);

}
