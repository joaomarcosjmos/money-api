package com.application.moneyapi.api.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@ConfigurationProperties("aplicacao")
public class ApplicationApiProperty {

    private String origemPermitida = "http://localhost:8000";

    @Getter
    private Seguranca seguranca = new Seguranca();


    public static class Seguranca{

        @Getter
        @Setter
        private boolean enableHttps;
    }

}
