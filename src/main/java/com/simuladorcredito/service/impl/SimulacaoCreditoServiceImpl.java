package com.simuladorcredito.service.impl;

import com.simuladorcredito.service.SimulacaoCreditoService;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.SimulacaoEmprestimoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SimulacaoCreditoServiceImpl implements SimulacaoCreditoService {


    @Override
    public SimulacaoEmprestimoResponse gerarSimulacao(BigDecimal valorEmprestimo, LocalDate dataNascimento, Integer qtdParcelas) {
        return null;
    }
}
