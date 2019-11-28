package com.application.moneyapi.api.execeptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice //Classe responsável por observar aplicação
public class MoneyExeceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        String mensagemDensenvolvedor = ex.getCause().toString();
        List<Erro> erros = Arrays.asList(new Erro(mensagemDensenvolvedor, mensagemUsuario));
        return handleExceptionInternal(ex, erros , headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        List<Erro> erros = criarListaDeErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<Erro> criarListaDeErros(BindingResult bindingResult){
        List<Erro> erros = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String mensagemDesenvolvedor = fieldError.toString();
            erros.add(new Erro( mensagemDesenvolvedor, mensagemUsuario));
        }

        return erros;
    }

    public static class Erro {

        private String memensagemUsuario;
        private String mensagemDensenvolvedor;

        private Erro(String mensagemDensenvolvedor, String mensagemUsuario) {
            this.memensagemUsuario = mensagemUsuario;
            this.mensagemDensenvolvedor = mensagemDensenvolvedor;
        }

        public String getMemensagemUsuario() {
            return memensagemUsuario;
        }
        public void setMemensagemUsuario(String memensagemUsuario) {
            this.memensagemUsuario = memensagemUsuario;
        }
        public String getMensagemDensenvolvedor() {
            return mensagemDensenvolvedor;
        }
        public void setMensagemDensenvolvedor(String mensagemDensenvolvedor) {
            this.mensagemDensenvolvedor = mensagemDensenvolvedor;
        }
    }
}
