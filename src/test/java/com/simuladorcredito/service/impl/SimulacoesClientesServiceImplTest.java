package com.simuladorcredito.service.impl;

import com.simuladorcredito.model.SimulacaoRequest;
import com.simuladorcredito.service.SimulacaoCreditoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openapitools.model.SimulacaoEmprestimoRequest;
import org.openapitools.model.SimulacaoEmprestimoResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class SimulacoesClientesServiceImplTest {

    @InjectMocks
    private SimulacoesClientesServiceImpl simulacoesClientesService;

    @Mock
    private SimulacaoCreditoService simulacaoCreditoService;


    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void gerarSimulacoes() {

        when(simulacaoCreditoService.gerarSimulacao(any(SimulacaoRequest.class))).thenReturn(response1()).thenReturn(response2());

        List<SimulacaoEmprestimoRequest> requests = List.of(request1(), request2());

        List<SimulacaoEmprestimoResponse> responses = simulacoesClientesService.gerarSimulacoes(requests);

        assertEquals(BigDecimal.valueOf(100), responses.get(0).getValorEmprestimo());
        assertEquals(BigDecimal.valueOf(10000), responses.get(1).getValorEmprestimo());

    }

    private SimulacaoEmprestimoRequest request1 (){
        return SimulacaoEmprestimoRequest.builder()
                .cpf("12345678901")
                .email("teste1@teste.com")
                .valorEmprestimo(BigDecimal.valueOf(100))
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .qtdParcelas(12)
                .build();
    }

    private SimulacaoEmprestimoRequest request2() {
        return SimulacaoEmprestimoRequest.builder()
                .cpf("98765432100")
                .email("teste2@teste.com")
                .valorEmprestimo(BigDecimal.valueOf(10000))
                .dataNascimento(LocalDate.of(2000, 5, 15))
                .qtdParcelas(24)
                .build();
    }

    SimulacaoEmprestimoResponse response1(){
        return SimulacaoEmprestimoResponse.builder()
                .valorEmprestimo(BigDecimal.valueOf(100))
                .totalJuros(BigDecimal.valueOf(500))
                .valorTotal(BigDecimal.valueOf(10500))
                .valorParcelas(BigDecimal.valueOf(875))
                .dataSimulacao(LocalDate.now().toString())
                .build();
    }

    SimulacaoEmprestimoResponse response2(){
        return SimulacaoEmprestimoResponse.builder()
                .valorEmprestimo(BigDecimal.valueOf(10000))
                .totalJuros(BigDecimal.valueOf(1000))
                .valorTotal(BigDecimal.valueOf(21000))
                .valorParcelas(BigDecimal.valueOf(875))
                .dataSimulacao(LocalDate.now().toString())
                .build();
    }
}