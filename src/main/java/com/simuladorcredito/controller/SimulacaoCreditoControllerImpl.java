package com.simuladorcredito.controller;

import com.simuladorcredito.service.SimulacaoCreditoService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.SimulacaoApi;

import org.openapitools.model.SimulacaoEmprestimoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
public class SimulacaoCreditoControllerImpl implements SimulacaoApi {

    private final SimulacaoCreditoService simulacaoCreditoService;

    @Override
    public ResponseEntity<SimulacaoEmprestimoResponse> getSimulacaoEmprestimo(BigDecimal valorEmprestimo, LocalDate dataNascimento, Integer qtdParcelas) {
        var simulacao = simulacaoCreditoService.gerarSimulacao(valorEmprestimo, dataNascimento, qtdParcelas);
        return ResponseEntity.ok(simulacao);
    }
}
