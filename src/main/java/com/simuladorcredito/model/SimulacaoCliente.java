package com.simuladorcredito.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Document(collection = "simulacoes")
public class SimulacaoCliente {

    @Id
    private String cpf;
    private BigDecimal valorEmprestimo;
    private BigDecimal valorParcelas;
    private BigDecimal valorTotal;
    private BigDecimal totalJuros;
    private LocalDate dataSimulacao;
    private Integer qtdParcelas;
    private LocalDate dataNascimento;

}