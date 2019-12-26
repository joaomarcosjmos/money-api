package com.application.moneyapi.api.repository;

import com.application.moneyapi.api.model.Lancamento;
import com.application.moneyapi.api.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {
}
