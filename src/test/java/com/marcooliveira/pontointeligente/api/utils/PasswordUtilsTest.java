package com.marcooliveira.pontointeligente.api.utils;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by Marco Antônio on 03/05/2018
 */
public class PasswordUtilsTest {

    private static final String SENHA = "123456";
    private  final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testSenhaNula() throws Exception{

        assertNull(PassowordUtils.geraBCrypt(null));
    }

    @Test
    public void testGerarHashSenha() throws Exception{

        String hash = PassowordUtils.geraBCrypt(SENHA);

        assertTrue(bCryptPasswordEncoder.matches(SENHA, hash));
    }
}
