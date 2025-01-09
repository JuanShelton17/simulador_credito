package com.simuladorcredito.service.impl;

import com.simuladorcredito.model.SimulacaoCliente;
import com.simuladorcredito.model.SimulacaoRequest;
import com.simuladorcredito.repository.SimulacaoClienteRepository;
import com.simuladorcredito.service.SimulacaoClienteService;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.SimulacaoEmprestimoResponse;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimulacaoClienteServiceImpl implements SimulacaoClienteService {

    private final SimulacaoClienteRepository repository;

    private final MongoTemplate mongoTemplate;

    @Override
    public SimulacaoCliente salvarSimulacao(SimulacaoRequest simulacaoRequest) {

        var simulacaoCliente = toSimulacaoCliente(simulacaoRequest);

        return repository.insert(simulacaoCliente);
    }

    @Override
    public List<SimulacaoEmprestimoResponse> listarSimulacoes(String cpf) {

        Query query = new Query();
        query.addCriteria(Criteria.where("cpf").in(cpf));
        var list = mongoTemplate.find(query, SimulacaoCliente.class);


        return list.stream().map(this::toSimulacaoRequest).collect(Collectors.toList());
    }

    private SimulacaoCliente toSimulacaoCliente(SimulacaoRequest simulacaoRequest) {
        return SimulacaoCliente.builder()
                .cpf(simulacaoRequest.getCpf())
                .email(simulacaoRequest.getEmail())
                .valorEmprestimo(simulacaoRequest.getValorEmprestimo())
                .valorParcelas(simulacaoRequest.getValorParcelas())
                .valorTotal(simulacaoRequest.getValorTotal())
                .totalJuros(simulacaoRequest.getTotalJuros())
                .dataSimulacao(simulacaoRequest.getDataSimulacao())
                .qtdParcelas(simulacaoRequest.getQtdParcelas())
                .dataNascimento(simulacaoRequest.getDataNascimento())
                .build();
    }

    private SimulacaoEmprestimoResponse toSimulacaoRequest(SimulacaoCliente simulacaoCliente) {
        return SimulacaoEmprestimoResponse.builder()
                .valorEmprestimo(simulacaoCliente.getValorEmprestimo())
                .dataSimulacao(simulacaoCliente.getDataSimulacao().toString())
                .totalJuros(simulacaoCliente.getTotalJuros())
                .valorParcelas(simulacaoCliente.getValorParcelas())
                .valorTotal(simulacaoCliente.getValorTotal())
                .build();
    }
}
