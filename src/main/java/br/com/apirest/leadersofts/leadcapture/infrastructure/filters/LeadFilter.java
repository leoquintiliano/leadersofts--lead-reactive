package br.com.apirest.leadersofts.leadcapture.infrastructure.filters;

import java.time.LocalDate;

public record LeadFilter(String term,
                         String nome,
                         String primeiroContato,
                         String ultimoContato,
                         String dataNascimento,
                         String celular,
                         String celular2,
                         String telefone,
                         String email,
                         String endereco,
                         String uf,
                         String cidade,
                         String carroInteresse1,
                         String carroInteresse2,
                         String carroInteresse3,
                         String carroAtual1,
                         String carroAtual2,
                         String carroAtual3,
                         String vendedor,
                         String status,
                         String opcaoVeiculo,
                         String observacoes,
                         String dataVenda) {
}
