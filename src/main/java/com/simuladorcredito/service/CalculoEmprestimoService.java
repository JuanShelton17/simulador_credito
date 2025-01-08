package com.simuladorcredito.service;

import java.math.BigDecimal;

public interface CalculoEmprestimoService {

    BigDecimal calculaPagamentoMensal(BigDecimal valorEmprestimo, BigDecimal taxaAnual, int parcelas);

    BigDecimal obterTaxaDeJuros(int idade);
}