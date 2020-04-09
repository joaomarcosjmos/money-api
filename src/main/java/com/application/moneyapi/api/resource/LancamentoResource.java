package com.application.moneyapi.api.resource;


import com.application.moneyapi.api.event.RecursoCriadoEvent;
import com.application.moneyapi.api.execeptionhandler.MoneyExeceptionHandler;
import com.application.moneyapi.api.model.Lancamento;
import com.application.moneyapi.api.repository.LancamentoRepository;
import com.application.moneyapi.api.repository.filter.LancamentoFilter;
import com.application.moneyapi.api.repository.projection.ResumoLancamento;
import com.application.moneyapi.api.service.Exception.PessoaInexistenteOuInativoException;
import com.application.moneyapi.api.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping //Busca com paginação
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
        return lancamentoRepository.filtrar(lancamentoFilter, pageable);
    }

    @GetMapping(params = "resumo") //Busca com paginação do resumo de lancamento
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable){
        return lancamentoRepository.resumir(lancamentoFilter, pageable);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo){
        Lancamento lancamento = lancamentoRepository.findOne(codigo);
        return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
    }

    /**
     * Pesistir dados no BD
     * @param lancamento informado para persistir bi BD
     * @param response requisição recebida
     * @Valid Usado na vaçidação dos campos
     * @return Retorna Locale do dado salvo
     *
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody  Lancamento lancamento, HttpServletResponse response){
        Lancamento lancamentoSalva = lancamentoService.salvar(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
    }

    @ExceptionHandler({PessoaInexistenteOuInativoException.class})
    public ResponseEntity<Object> handlePessoaInexistenteOuInativoException(PessoaInexistenteOuInativoException ex){
        String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String mensagemDensenvolvedor = ex.toString();
        List<MoneyExeceptionHandler.Erro> erros = Collections.singletonList(new MoneyExeceptionHandler.Erro(mensagemDensenvolvedor, mensagemUsuario));
        return ResponseEntity.badRequest().body(erros);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //Retorna um código 204 - Ok mas sem retorno
    @PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
    public void remover(@PathVariable Long codigo){
        lancamentoRepository.delete(codigo);
    }

}
