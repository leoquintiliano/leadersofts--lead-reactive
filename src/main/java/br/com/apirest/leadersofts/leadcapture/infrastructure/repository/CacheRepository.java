package br.com.apirest.leadersofts.leadcapture.infrastructure.repository;

import br.com.apirest.leadersofts.leadcapture.infrastructure.entities.LeadCache;

import java.util.List;

public interface CacheRepository {
    void revogarCacheLeadPostado(List<LeadCache> leads);
}
