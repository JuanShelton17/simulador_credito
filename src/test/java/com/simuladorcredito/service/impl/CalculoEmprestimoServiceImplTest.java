package com.simuladorcredito.service.impl;

import com.simuladorcredito.exception.SimulacaoClienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CalculoEmprestimoServiceImplTest {

    @InjectMocks
    private CalculoEmprestimoServiceImpl calculoEmprestimoServiceImpl;

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void calculaPagamentoMensal() {
        var valorEmprestimo = BigDecimal.valueOf(10);
        var taxaAnual = BigDecimal.valueOf(0.2);
        var parcelas = 10;
        var expected = BigDecimal.valueOf(2.38);

        var response = calculoEmprestimoServiceImpl.calculaPagamentoMensal(valorEmprestimo, taxaAnual, parcelas);

        assertEquals(expected, response);

    }

    @Test
    void obterTaxaDeJuros() {
        assertAll(
                () -> assertEquals(BigDecimal.valueOf(0.05), calculoEmprestimoServiceImpl.obterTaxaDeJuros(20)),
                () -> assertEquals(BigDecimal.valueOf(0.03), calculoEmprestimoServiceImpl.obterTaxaDeJuros(30)),
                () -> assertEquals(BigDecimal.valueOf(0.02), calculoEmprestimoServiceImpl.obterTaxaDeJuros(50)),
                () -> assertEquals(BigDecimal.valueOf(0.04), calculoEmprestimoServiceImpl.obterTaxaDeJuros(65))
        );
    }

    @Test
    void obterTaxaDeJurosMenor() {
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Cliente menor de idade");

        var exception = assertThrows(SimulacaoClienteException.class, () -> calculoEmprestimoServiceImpl.obterTaxaDeJuros(10));

        assertEquals("Cliente menor de idade", exception.getMessage());

    }

    @Test
    void getIdade() {
        LocalDate dataNascimento = LocalDate.of(2000, 1, 1);

        Integer idade = calculoEmprestimoServiceImpl.getIdade(dataNascimento);

        assertEquals(25, idade);
    }
}