package com.application.moneyapi.api.repository.lancamento;

import com.application.moneyapi.api.model.Lancamento;
import com.application.moneyapi.api.repository.filter.LancamentoFilter;

import java.util.List;

public interface LancamentoRepositoryQuery {

    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
