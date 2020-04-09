package com.application.moneyapi.api.repository.lancamento;

import com.application.moneyapi.api.model.Lancamento;
import com.application.moneyapi.api.repository.filter.LancamentoFilter;
import com.application.moneyapi.api.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
