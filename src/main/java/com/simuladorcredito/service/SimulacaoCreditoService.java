package com.simuladorcredito.service;

import org.openapitools.model.SimulacaoEmprestimoResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SimulacaoCreditoService {
    SimulacaoEmprestimoResponse gerarSimulacao(BigDecimal valorEmprestimo, LocalDate dataNascimento, Integer qtdParcelas);
}
