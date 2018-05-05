package com.marcooliveira.pontointeligente.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Marco Antônio on 03/05/2018
 */
public class PassowordUtils {

    private static final Logger log = LoggerFactory.getLogger(PassowordUtils.class);

    /**
     * Gera senha criptografada com o BCrypt
     * @param senha
     * @return String
     */
    public static String geraBCrypt(String senha){
        if (senha == null){
            return senha;
        }

        log.info("Gerando hash com o BCrypt.");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(senha);
    }
}
