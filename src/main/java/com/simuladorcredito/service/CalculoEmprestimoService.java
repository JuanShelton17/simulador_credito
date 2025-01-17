package com.simuladorcredito.service;


import java.math.BigDecimal;
import java.time.LocalDate;


public interface CalculoEmprestimoService {

    BigDecimal calculaPagamentoMensal(BigDecimal valorEmprestimo, BigDecimal taxaDeJuros, int parcelas);

    BigDecimal obterTaxaDeJuros(int idade);

    Integer getIdade(LocalDate date);
}
