package com.simuladorcredito.service;

import org.openapitools.model.SimulacaoEmprestimoRequest;
import org.openapitools.model.SimulacaoEmprestimoResponse;

import java.util.List;

public interface SimulacoesClientesService {
    List<SimulacaoEmprestimoResponse> gerarSimulacoes(List<SimulacaoEmprestimoRequest> simulacoes);
}
