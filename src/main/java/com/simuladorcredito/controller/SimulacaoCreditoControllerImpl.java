package com.simuladorcredito.controller;

import com.simuladorcredito.model.SimulacaoRequest;
import com.simuladorcredito.service.SimulacaoClienteService;
import com.simuladorcredito.service.SimulacaoCreditoService;
import com.simuladorcredito.service.SimulacoesClientesService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.SimulacaoApi;

import org.openapitools.model.SimulacaoEmprestimoRequest;
import org.openapitools.model.SimulacaoEmprestimoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class SimulacaoCreditoControllerImpl implements SimulacaoApi {

    private final SimulacaoCreditoService simulacaoCreditoService;

    private final SimulacaoClienteService simulacaoClienteService;

    private final SimulacoesClientesService simulacoesClientesService;

    @Override
    public ResponseEntity<List<SimulacaoEmprestimoResponse>> gerarSimulacoes(List<SimulacaoEmprestimoRequest> simulacaoEmprestimoRequest) {
        return ResponseEntity.ok(simulacoesClientesService.gerarSimulacoes(simulacaoEmprestimoRequest));
    }

    @Override
    public ResponseEntity<SimulacaoEmprestimoResponse>  getSimulacaoEmprestimo(String cpf, String email, BigDecimal valorEmprestimo, LocalDate dataNascimento, Integer qtdParcelas) {
        var dadosSimulacao = SimulacaoRequest.builder()
                .cpf(cpf)
                .email(email)
                .valorEmprestimo(valorEmprestimo)
                .dataNascimento(dataNascimento)
                .qtdParcelas(qtdParcelas)
                .build();

        var simulacao = simulacaoCreditoService.gerarSimulacao(dadosSimulacao);
        return ResponseEntity.ok(simulacao);    }

    @Override
    public ResponseEntity<List<SimulacaoEmprestimoResponse>> getSimulacoesClientes(String cpf) {
        var simulacoesClientes = simulacaoClienteService.listarSimulacoes(cpf);

        return ResponseEntity.ok(simulacoesClientes);
    }

}
