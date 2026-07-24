package br.com.apirest.leadersofts.leadcapture.infrastructure.dto.carga.okta_storage;

public record LeadPayloadRecord(String nome, String dataNascimento, String celular, String email,
                                String endereco, String carroAtual, String carroInteresse, String observacoes) {
}
