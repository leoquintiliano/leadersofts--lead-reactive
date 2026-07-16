package br.com.apirest.leadersofts.leadcapture.infrastructure.config;

//import com.grupopan.lib.cartoes.aspects.Layer;
import org.springframework.cache.Cache;

//import static com.grupopan.lib.cartoes.utils.LogUtils.log;

public class CacheErrorHandlerConfig implements org.springframework.cache.interceptor.CacheErrorHandler {

    private static final String CACHE_NAME = "cacheName";
    private static final String KEY = "key";

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
//        log("handleCacheGetError", Layer.INTERACTORS,
//            "CACHE - FALHA AO OBTER DO CACHE",
//            this.getClass())
//            .withError(exception)
//            .withField(CACHE_NAME, cache.getName())
//            .withField(KEY, key)
//            .log();
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key,
        Object value) {
//        log("handleCachePutError", Layer.INTERACTORS,
//            "CACHE - FALHA AO INSERIR DO CACHE",
//            this.getClass())
//            .withError(exception)
//            .withField(CACHE_NAME, cache.getName())
//            .withField(KEY, key)
//            .log();
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
//        log("handleCacheEvictError", Layer.INTERACTORS,
//            "CACHE - FALHA NO DESPEJO DO CACHE",
//            this.getClass())
//            .withError(exception)
//            .withField(CACHE_NAME, cache.getName())
//            .withField(KEY, key)
//            .log();
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
//        log("handleCacheClearError", Layer.INTERACTORS,
//            "CACHE - FALHA NA LIMPEZA DO CACHE",
//            this.getClass())
//            .withError(exception)
//            .withField(CACHE_NAME, cache.getName())
//            .log();
    }
}
