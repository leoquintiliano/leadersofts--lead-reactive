package br.com.apirest.leadersofts.leadcapture.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadDTO implements Serializable {

    private static Long serialVersionUID = 1L;

    @JsonProperty
    private Long id;

    @JsonProperty
    private String nome;

    @JsonProperty
    private String primeiroContato;

    @JsonProperty
    private String ultimoContato;

    @JsonProperty
    private String dataNascimento;

    @JsonProperty
    private String celular;

    @JsonProperty
    private String celular2;

    @JsonProperty
    private String telefone;

    @JsonProperty
    private String endereco;

    @JsonProperty
    private String email;

    @JsonProperty
    private String uf;

    @JsonProperty
    private String cidade;

    @JsonProperty
    private String carroInteresse1;

    @JsonProperty
    private String carroInteresse2;

    @JsonProperty
    private String carroInteresse3;

    @JsonProperty
    private String carroAtual1;

    @JsonProperty
    private String carroAtual2;

    @JsonProperty
    private String carroAtual3;

    @JsonProperty
    private String vendedor;

    @JsonProperty
    private String status;

    @JsonProperty
    private String opcaoVeiculo; // TODOS, 0KM, SEMI-NOVO

    @JsonProperty
    private String observacoes;

    @JsonProperty
    private String dataCadastro;

    @JsonProperty
    private Long diasCadastro;

    @JsonProperty
    private Long diasUltimoContato;

    @JsonProperty
    private String dataVenda;

    @JsonProperty
    private Long diasVenda;

}
