package com.application.moneyapi.api.resource;


import com.application.moneyapi.api.event.RecursoCriadoEvent;
import com.application.moneyapi.api.model.Categoria;
import com.application.moneyapi.api.model.Lancamento;
import com.application.moneyapi.api.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @GetMapping
    public List<Lancamento> listar(){
        return lancamentoRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo){
        Lancamento lancamento = lancamentoRepository.findOne(codigo);
        return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
    }

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Pesistir dados no BD
     * @param lancamento informado para persistir bi BD
     * @param response requisição recebida
     * @Valid Usado na vaçidação dos campos
     * @return Retorna Locale do dado salvo
     *
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody  Lancamento lancamento, HttpServletResponse response){
        Lancamento lancamentoSalva = lancamentoRepository.save(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
    }


}
