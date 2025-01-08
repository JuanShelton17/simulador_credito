package com.simuladorcredito.service.impl;

import com.simuladorcredito.service.CalculoEmprestimoService;

import java.math.BigDecimal;

public class CalculoEmprestimoServiceImpl implements CalculoEmprestimoService {

    @Override
    public BigDecimal calculaPagamentoMensal(BigDecimal valorEmprestimo, BigDecimal taxaAnual, int parcelas) {
        return null;
    }

    @Override
    public BigDecimal obterTaxaDeJuros(int idade) {
        return null;
    }
}
