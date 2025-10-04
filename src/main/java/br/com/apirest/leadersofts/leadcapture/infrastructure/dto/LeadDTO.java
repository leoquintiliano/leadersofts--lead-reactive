package br.com.apirest.leadersofts.leadcapture.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadDTO {

    private Long id;

    private String nome;

    private String primeiroContato;

    private String ultimoContato;

    private String dataNascimento;

    private String celular;

    private String celular2;

    private String telefone;

    private String endereco;

    private String email;

    private String uf;

    private String cidade;

    private String carroInteresse1;

    private String carroInteresse2;

    private String carroInteresse3;

    private String carroAtual1;

    private String carroAtual2;

    private String carroAtual3;

    private String vendedor;

    private String status;

    private String opcaoVeiculo; // TODOS, 0KM, SEMI-NOVO

    private String observacoes;

    private String dataCadastro;

    private Long diasCadastro;

    private Long diasUltimoContato;

    private String dataVenda;

    private Long diasVenda;

}
