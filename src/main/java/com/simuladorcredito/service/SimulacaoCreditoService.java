package com.simuladorcredito.service;

import com.simuladorcredito.model.SimulacaoRequest;
import org.openapitools.model.SimulacaoEmprestimoResponse;


public interface SimulacaoCreditoService {
    SimulacaoEmprestimoResponse gerarSimulacao(SimulacaoRequest simulacaoRequest);
}
