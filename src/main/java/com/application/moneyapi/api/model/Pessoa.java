package com.application.moneyapi.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull
    @Size(min = 3, max = 20)
    private String nome;

    @NotNull
    private  boolean ativo;

    @Embedded
    private Endereco endereco;

    @JsonIgnore
    @Transient
    public boolean isInativo(){
        return !this.ativo;
    }

}
