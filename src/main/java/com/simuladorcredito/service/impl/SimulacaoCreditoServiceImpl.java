package com.simuladorcredito.service.impl;

import com.simuladorcredito.model.SimulacaoRequest;
import com.simuladorcredito.service.CalculoEmprestimoService;
import com.simuladorcredito.service.SimulacaoClienteService;
import com.simuladorcredito.service.SimulacaoCreditoService;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.SimulacaoEmprestimoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SimulacaoCreditoServiceImpl implements SimulacaoCreditoService {

    private final CalculoEmprestimoService calculoEmprestimoService;
    private final SimulacaoClienteService simulacaoClienteService;

    @Override
    public SimulacaoEmprestimoResponse gerarSimulacao(SimulacaoRequest simulacaoRequest) {

        int idade = calculoEmprestimoService.getIdade(simulacaoRequest.getDataNascimento());

        var taxaDeJuros = calculoEmprestimoService.obterTaxaDeJuros(idade);

        var parcelaMensal = calculoEmprestimoService.calculaPagamentoMensal(
                simulacaoRequest.getValorEmprestimo(),
                taxaDeJuros,
                simulacaoRequest.getQtdParcelas()
        );

        var totalPagamento = parcelaMensal.multiply(BigDecimal.valueOf(simulacaoRequest.getQtdParcelas())).setScale(2, RoundingMode.DOWN);

        var jurosTotal = totalPagamento.subtract(simulacaoRequest.getValorEmprestimo()).setScale(2, RoundingMode.DOWN);

        var response = SimulacaoEmprestimoResponse.builder()
                .dataSimulacao(LocalDate.now().toString())
                .valorTotal(totalPagamento)
                .valorParcelas(parcelaMensal)
                .totalJuros(jurosTotal)
                .build();

        simulacaoRequest.setValorParcelas(parcelaMensal);
        simulacaoRequest.setValorTotal(totalPagamento);
        simulacaoRequest.setTotalJuros(jurosTotal);
        simulacaoRequest.setDataSimulacao(LocalDate.now());

        simulacaoClienteService.salvarSimulacao(simulacaoRequest);

        return response;
    }
}
