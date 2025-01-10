package com.simuladorcredito.service;

import com.simuladorcredito.model.SimulacaoCliente;
import com.simuladorcredito.model.SimulacaoRequest;
import org.openapitools.model.SimulacaoEmprestimoResponse;

import java.util.List;

public interface SimulacaoClienteService {

    SimulacaoCliente salvarSimulacao(SimulacaoRequest simulacaoRequest);

    List<SimulacaoEmprestimoResponse> listarSimulacoes(String cpf);
}
