package com.simuladorcredito.service.impl;

import com.simuladorcredito.exception.SimulacaoClienteException;
import com.simuladorcredito.service.CalculoEmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

import static com.simuladorcredito.constants.MessageConstants.ERROR_CLIENTE_MENOR;

@Service
@RequiredArgsConstructor
public class CalculoEmprestimoServiceImpl implements CalculoEmprestimoService {

    private final MessageSource messageSource;

    @Override
    public BigDecimal calculaPagamentoMensal(BigDecimal valorEmprestimo, BigDecimal taxaAnual, int parcelas) {

        BigDecimal taxaDeJurosMensal = taxaAnual.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        MathContext mc = new MathContext(10, RoundingMode.HALF_UP);

        BigDecimal calculoTaxa = taxaDeJurosMensal.add(BigDecimal.ONE, mc);

        BigDecimal fatorPotencia = calculoTaxa.pow(-parcelas, mc);

        BigDecimal denominador = BigDecimal.ONE.subtract(fatorPotencia, mc);

        BigDecimal numerador = valorEmprestimo.multiply(taxaDeJurosMensal, mc);

        BigDecimal pmt = numerador.divide(denominador, mc);

        return pmt.setScale(2, RoundingMode.DOWN);
    }

    @Override
    public BigDecimal obterTaxaDeJuros(int idade) {
        if (idade < 18) {
            throw new SimulacaoClienteException(HttpStatus.NOT_FOUND, messageSource.getMessage(ERROR_CLIENTE_MENOR, null, Locale.getDefault()));
        }
        if (idade <= 25) return BigDecimal.valueOf(0.05);
        if (idade <= 40) return BigDecimal.valueOf(0.03);
        if (idade <= 60) return BigDecimal.valueOf(0.02);
        return BigDecimal.valueOf(0.04);
    }

    @Override
    public Integer getIdade(LocalDate date) {
        return Period.between(date, LocalDate.now()).getYears();
    }
}
