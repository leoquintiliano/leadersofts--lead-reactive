package br.com.apirest.leadersofts.leadcapture.application;

import br.com.apirest.leadersofts.leadcapture.adapter.DeleteLeadAdapter;
import br.com.apirest.leadersofts.leadcapture.core.DeleteLeadUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeleteLeadUseCaseImpl implements DeleteLeadUseCase {

    private final DeleteLeadAdapter deleteLeadAdapter;

    public DeleteLeadUseCaseImpl(DeleteLeadAdapter deleteLeadAdapter) {
        this.deleteLeadAdapter = deleteLeadAdapter;
    }

    @Override
    public void delete(Long id) {
        deleteLeadAdapter.delete(id);
    }
}
