package com.simuladorcredito.service.impl;

import com.simuladorcredito.model.SimulacaoCliente;
import com.simuladorcredito.model.SimulacaoRequest;
import com.simuladorcredito.repository.SimulacaoClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openapitools.model.SimulacaoEmprestimoResponse;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class SimulacaoClienteServiceImplTest {

    @InjectMocks
    private SimulacaoClienteServiceImpl simulacaoClienteService;

    @Mock
    private SimulacaoClienteRepository repository;

    @Mock
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        openMocks(this);

    }

    @Test
    void salvarSimulacao() {

        SimulacaoRequest simulacaoRequest = SimulacaoRequest.builder()
                .cpf("12345678901")
                .email("teste@teste.com")
                .valorEmprestimo(BigDecimal.valueOf(10000))
                .valorParcelas(BigDecimal.valueOf(833.33))
                .valorTotal(BigDecimal.valueOf(10000))
                .totalJuros(BigDecimal.ZERO)
                .dataSimulacao(LocalDate.now())
                .qtdParcelas(12)
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .build();

        SimulacaoCliente simulacaoCliente = SimulacaoCliente.builder()
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

        when(repository.insert(any(SimulacaoCliente.class))).thenReturn(simulacaoCliente);

        SimulacaoCliente response = simulacaoClienteService.salvarSimulacao(simulacaoRequest);

        assertAll(
                () -> assertEquals("12345678901", response.getCpf()),
                () -> assertEquals("teste@teste.com", response.getEmail()),
                () -> verify(repository, times(1)).insert(any(SimulacaoCliente.class))
        );
    }

    @Test
    void listarSimulacoes() {
        String cpf = "12345678901";

        SimulacaoCliente simulacaoCliente = SimulacaoCliente.builder()
                .cpf(cpf)
                .valorEmprestimo(BigDecimal.valueOf(10000))
                .valorParcelas(BigDecimal.valueOf(833.33))
                .valorTotal(BigDecimal.valueOf(10000))
                .totalJuros(BigDecimal.ZERO)
                .dataSimulacao(LocalDate.now())
                .qtdParcelas(12)
                .build();

        when(mongoTemplate.find(any(Query.class), eq(SimulacaoCliente.class)))
                .thenReturn(List.of(simulacaoCliente));

        List<SimulacaoEmprestimoResponse> response = simulacaoClienteService.listarSimulacoes(cpf);


        assertEquals(BigDecimal.valueOf(10000), response.get(0).getValorEmprestimo());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(SimulacaoCliente.class));
    }
}