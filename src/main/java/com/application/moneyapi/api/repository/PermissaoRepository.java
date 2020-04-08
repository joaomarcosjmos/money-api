package com.application.moneyapi.api.repository;

import com.application.moneyapi.api.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
}
