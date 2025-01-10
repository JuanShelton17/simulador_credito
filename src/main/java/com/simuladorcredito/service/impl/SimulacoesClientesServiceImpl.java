package com.simuladorcredito.service.impl;

import com.simuladorcredito.model.SimulacaoRequest;
import com.simuladorcredito.service.SimulacaoCreditoService;
import com.simuladorcredito.service.SimulacoesClientesService;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.SimulacaoEmprestimoRequest;
import org.openapitools.model.SimulacaoEmprestimoResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimulacoesClientesServiceImpl implements SimulacoesClientesService {

    private final SimulacaoCreditoService simulacaoCreditoService;

    @Override
    public List<SimulacaoEmprestimoResponse> gerarSimulacoes(List<SimulacaoEmprestimoRequest> simulacoes) {
        var simulacoesRequest = simulacoes.stream().map(this::toSimulacaoRequest).toList();

        List<CompletableFuture<SimulacaoEmprestimoResponse>> futures = simulacoesRequest.stream()
                .map(request -> CompletableFuture.supplyAsync(() ->
                        simulacaoCreditoService.gerarSimulacao(request)))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    private SimulacaoRequest toSimulacaoRequest(SimulacaoEmprestimoRequest simulacaoEmprestimoRequest) {
        return SimulacaoRequest.builder()
                .cpf(simulacaoEmprestimoRequest.getCpf())
                .dataNascimento(simulacaoEmprestimoRequest.getDataNascimento())
                .email(simulacaoEmprestimoRequest.getEmail())
                .qtdParcelas(simulacaoEmprestimoRequest.getQtdParcelas())
                .valorEmprestimo(simulacaoEmprestimoRequest.getValorEmprestimo())
                .build();
    }
}
