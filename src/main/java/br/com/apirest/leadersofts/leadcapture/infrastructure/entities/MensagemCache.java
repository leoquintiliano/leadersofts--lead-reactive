package br.com.apirest.leadersofts.leadcapture.infrastructure.entities;

import java.util.Arrays;
import java.util.List;

public class MensagemCache {

    private List<LeadCache> leads = Arrays.asList();

    public List<LeadCache> getLeads() {
        return leads;
    }

    public void setLeads(List<LeadCache> leads) {
        this.leads = leads;
    }
}
