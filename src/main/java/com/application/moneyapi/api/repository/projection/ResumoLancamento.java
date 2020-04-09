package com.application.moneyapi.api.repository.projection;

import com.application.moneyapi.api.model.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ResumoLancamento {

    private Long codigo;
    private String descricao;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private TipoLancamento tipo;
    private String categoria;
    private String pessoa;

}
