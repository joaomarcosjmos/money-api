package com.application.moneyapi.api.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {
    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
       // $2a$10$z9XM4tJb1tHIaWcgvrJH7ekpd1fdR4g4YY6AdVaC.UA9QKzFbt9iG

    }
}
