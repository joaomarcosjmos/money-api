package com.application.moneyapi.api.resource;

import com.application.moneyapi.api.event.RecursoCriadoEvent;
import com.application.moneyapi.api.model.Pessoa;
import com.application.moneyapi.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
    public Pessoa buscarPeloCodigo(@PathVariable Long codigo){
        return pessoaRepository.findOne(codigo);
    }


    @Autowired
    private ApplicationEventPublisher publisher;

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

}
