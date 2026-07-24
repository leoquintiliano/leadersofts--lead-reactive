package br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.usecases;

import br.com.apirest.leadersofts.leadcapture.adapter.DeleteLeadAdapter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.IDeleter;
import org.springframework.stereotype.Service;

@Service
public class DeleteLeadGatewayImpl implements DeleteLeadAdapter {

    private final IDeleter deleter;

    private DeleteLeadGatewayImpl(IDeleter deleter) {
        this.deleter = deleter;
    }

    @Override
    public void delete(Long id) {
        deleter.delete(id);
    }
}
