package br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.implementation;

import br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.IDeleter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.repository.jpa.LeadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleterImpl implements IDeleter {

    private final LeadRepository leadRepository;

    @Override
    public void delete(Long id) {
        this.leadRepository.deleteById(id).subscribe();
    }
}
