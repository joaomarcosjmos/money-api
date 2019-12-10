package com.application.moneyapi.api.service;

import com.application.moneyapi.api.model.Pessoa;
import com.application.moneyapi.api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa atualizar(Long codigo, Pessoa pessoa){

        Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
        if (pessoaSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
        return pessoaRepository.save(pessoaSalva);
    }

}
