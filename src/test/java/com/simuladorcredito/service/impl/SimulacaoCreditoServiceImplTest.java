package com.simuladorcredito.service.impl;

import com.simuladorcredito.model.SimulacaoRequest;
import com.simuladorcredito.service.CalculoEmprestimoService;
import com.simuladorcredito.service.SimulacaoClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openapitools.model.SimulacaoEmprestimoResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class SimulacaoCreditoServiceImplTest {


    @InjectMocks
    private SimulacaoCreditoServiceImpl simulacaoCreditoService;

    @Mock
    private CalculoEmprestimoService calculoEmprestimoService;

    @Mock
    private SimulacaoClienteService simulacaoClienteService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void gerarSimulacao() {

        SimulacaoRequest request = SimulacaoRequest.builder()
                .cpf("12345678901")
                .email("teste@teste.com")
                .valorEmprestimo(BigDecimal.valueOf(10000))
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .qtdParcelas(12)
                .build();

        when(calculoEmprestimoService.getIdade(request.getDataNascimento())).thenReturn(33);
        when(calculoEmprestimoService.obterTaxaDeJuros(33)).thenReturn(BigDecimal.valueOf(0.03));
        when(calculoEmprestimoService.calculaPagamentoMensal(
                request.getValorEmprestimo(),
                BigDecimal.valueOf(0.03),
                request.getQtdParcelas()
        )).thenReturn(BigDecimal.valueOf(858.33));

        SimulacaoEmprestimoResponse response = simulacaoCreditoService.gerarSimulacao(request);

        assertAll(
                () -> assertEquals(BigDecimal.valueOf(10299.96).setScale(2), response.getValorTotal()),
                () -> assertEquals(BigDecimal.valueOf(299.96).setScale(2), response.getTotalJuros()),
                () -> assertEquals(BigDecimal.valueOf(858.33).setScale(2), response.getValorParcelas())
        );

    }
}