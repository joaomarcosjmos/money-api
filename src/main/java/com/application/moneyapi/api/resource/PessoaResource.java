package com.application.moneyapi.api.resource;

import com.application.moneyapi.api.event.RecursoCriadoEvent;
import com.application.moneyapi.api.model.Pessoa;
import com.application.moneyapi.api.repository.PessoaRepository;
import com.application.moneyapi.api.service.PessoaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Buscar todas as pessoas
     * salvas no banco de dados
     */
    @GetMapping // Buscar todas as pessoas salvas
    public List<Pessoa> listar(){
        return pessoaRepository.findAll();
    }

    /**
     * Buscar dados por código
     * @param codigo Recebe o código referente ao dado salvo
     * @return Retorna a informação buscado pelo código
     */
    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo){
        Pessoa pessoa = pessoaRepository.findOne(codigo);
        return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
    }

    /**
     * Pesistir dados no BD
     * @param pessoa Dado informado para persistir bi BD
     * @param response Recebe a requisição
     * @Valid Usado na vaçidação dos campos
     * @return Retorna Locale do dado salvo
     *
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //Retorna um código 204 - Ok mas sem retorno
    public void remover(@PathVariable Long codigo){
        pessoaRepository.delete(codigo);
    }

    @PutMapping("/{codigo}")
    ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
        Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
        return ResponseEntity.ok(pessoaSalva);
    }

    @PutMapping("/{codigo}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo){
        pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
    }

}
