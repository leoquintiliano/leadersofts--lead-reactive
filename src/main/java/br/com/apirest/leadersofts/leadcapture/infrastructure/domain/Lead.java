package br.com.apirest.leadersofts.leadcapture.infrastructure.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lead")
@Entity(name = "lead")
@Builder
public class Lead {

    @Id
    @SequenceGenerator(name = "seq_lead", sequenceName = "seq_lead", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lead")
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


