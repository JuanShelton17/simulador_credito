package com.simuladorcredito.controller;

import com.simuladorcredito.model.SimulacaoRequest;
import com.simuladorcredito.service.SimulacaoClienteService;
import com.simuladorcredito.service.SimulacaoCreditoService;
import com.simuladorcredito.service.SimulacoesClientesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openapitools.model.SimulacaoEmprestimoRequest;
import org.openapitools.model.SimulacaoEmprestimoResponse;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class SimulacaoCreditoControllerImplTest {

    @InjectMocks
    private SimulacaoCreditoControllerImpl simulacaoCreditoController;

    @Mock
    private SimulacaoCreditoService simulacaoCreditoService;

    @Mock
    private SimulacaoClienteService simulacaoClienteService;

    @Mock
    private SimulacoesClientesService simulacoesClientesService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getSimulacaoEmprestimo() {

        var cpf = "123456789";
        var email = "email@email.com";
        var valorEmprestimo = new BigDecimal("100.00");
        var dataNascimento = LocalDate.now();
        var qtdParcelas = 1;

        var dadosSimulacao = SimulacaoRequest.builder()
                .cpf(cpf)
                .email(email)
                .valorEmprestimo(valorEmprestimo)
                .dataNascimento(dataNascimento)
                .qtdParcelas(qtdParcelas)
                .build();

        var simulacaoEmprestimoResponse = SimulacaoEmprestimoResponse.builder().dataSimulacao(LocalDate.now().toString()).valorEmprestimo(BigDecimal.TEN).valorParcelas(BigDecimal.TEN).valorTotal(BigDecimal.TEN).build();

        when(simulacaoCreditoService.gerarSimulacao(dadosSimulacao)).thenReturn(simulacaoEmprestimoResponse);

        var response = simulacaoCreditoController.getSimulacaoEmprestimo(cpf, email, valorEmprestimo, dataNascimento, qtdParcelas);


        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(simulacaoEmprestimoResponse, response.getBody()),
                () -> verify(simulacaoCreditoService, times(1)).gerarSimulacao(dadosSimulacao)

        );

    }

    @Test
    void getSimulacoesClientes() {

        var cpf = "123456789";

        var simulacaoEmprestimoResponse = SimulacaoEmprestimoResponse.builder().dataSimulacao(LocalDate.now().toString()).valorEmprestimo(BigDecimal.TEN).valorParcelas(BigDecimal.TEN).valorTotal(BigDecimal.TEN).build();


        when(simulacaoClienteService.listarSimulacoes(cpf)).thenReturn(List.of(simulacaoEmprestimoResponse));

        var response = simulacaoCreditoController.getSimulacoesClientes(cpf);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(List.of(simulacaoEmprestimoResponse), response.getBody()),
                () -> verify(simulacaoClienteService, times(1)).listarSimulacoes(cpf)

        );
    }

    @Test
    void gerarSimulacoes(){

        var simulacaoEmprestimoResponse = SimulacaoEmprestimoResponse.builder().dataSimulacao(LocalDate.now().toString()).valorEmprestimo(BigDecimal.TEN).valorParcelas(BigDecimal.TEN).valorTotal(BigDecimal.TEN).build();

        when(simulacoesClientesService.gerarSimulacoes(List.of(request1()))).thenReturn((List.of(simulacaoEmprestimoResponse)));

        var response = simulacaoCreditoController.gerarSimulacoes(List.of(request1()));

        verify(simulacoesClientesService, times(1)).gerarSimulacoes(List.of(request1()));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(simulacaoEmprestimoResponse), response.getBody());
    }

    private SimulacaoEmprestimoRequest request1(){
        return SimulacaoEmprestimoRequest.builder()
                .cpf("12345678901")
                .email("teste1@teste.com")
                .valorEmprestimo(BigDecimal.valueOf(100))
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .qtdParcelas(12)
                .build();
    }
}