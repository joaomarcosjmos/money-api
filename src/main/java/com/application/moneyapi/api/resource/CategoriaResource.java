package com.application.moneyapi.api.resource;

import com.application.moneyapi.api.event.RecursoCriadoEvent;
import com.application.moneyapi.api.model.Categoria;
import com.application.moneyapi.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Buscar todas as categorias
     * salvas no banco de dados
     * @return Retorna lista de todas categorias
     */
    @GetMapping //Buscar dados
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')") //Anotação usada para chamer as roles
    public List<Categoria> listar(){
        return categoriaRepository.findAll();
    }

    /**
     * Buscar dados por código
     * @param codigo Recebe o código referente ao dado salvo
     * @return Retorna a informação buscado pelo código
     */
    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')") //Usado hasScope para definir o escopo
    public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo){
        Categoria categoria = categoriaRepository.findOne(codigo);
        return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

    /**
     * Pesistir dados no BD
     * @param categoria Dado informado para persistir bi BD
     * @param response requisição recebida
     * @Valid Usado na vaçidação dos campos
     * @return Retorna Locale do dado salvo
     *
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Categoria> criar(@Valid @RequestBody  Categoria categoria, HttpServletResponse response){
        Categoria categoriaSalva = categoriaRepository.save(categoria);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //Retorna um código 204 - Ok mas sem retorno
    public void remover(@PathVariable Long codigo){
        categoriaRepository.delete(codigo);
    }

}
