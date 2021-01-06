package com.application.moneyapi.api.service;

import com.application.moneyapi.api.model.Lancamento;
import com.application.moneyapi.api.model.Pessoa;
import com.application.moneyapi.api.repository.LancamentoRepository;
import com.application.moneyapi.api.repository.PessoaRepository;
import com.application.moneyapi.api.service.Exception.PessoaInexistenteOuInativoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;



    public Lancamento salvar(Lancamento lancamento) {
         Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
         if(pessoa == null || pessoa.isInativo()){
             throw new PessoaInexistenteOuInativoException();
         }
        return lancamentoRepository.save(lancamento);
    }

    public Lancamento atualizar(Long codigo, Lancamento lancamento){
        Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);

        if(lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())){
            validarPessoa(lancamento);
        }

        BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

        return lancamentoRepository.save(lancamento);
    }

    public void validarPessoa(Lancamento lancamento){
        Pessoa pessoa = null;
        if (lancamento.getPessoa().getCodigo() != null){
            pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
        }

        if (pessoa == null || pessoa.isInativo()){
            throw new PessoaInexistenteOuInativoException();
        }
    }

    public Lancamento buscarLancamentoExistente(Long codigo){
        Lancamento lancamentoSalvo = lancamentoRepository.findOne(codigo);

        if (lancamentoSalvo == null){
            throw new IllegalArgumentException();
        }
        return lancamentoSalvo;
    }
}
