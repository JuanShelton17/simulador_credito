package com.simuladorcredito.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class SimulacaoRequest {

    private String cpf;
    private BigDecimal valorEmprestimo;
    private BigDecimal valorParcelas;
    private BigDecimal valorTotal;
    private BigDecimal totalJuros;
    private LocalDate dataSimulacao;
    private Integer qtdParcelas;
    private LocalDate dataNascimento;
}
