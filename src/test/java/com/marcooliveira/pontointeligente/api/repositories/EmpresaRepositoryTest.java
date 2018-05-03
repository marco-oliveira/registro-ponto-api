package com.marcooliveira.pontointeligente.api.repositories;

import com.marcooliveira.pontointeligente.api.entities.Empresa;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
/**
 * Created by Marco Antônio on 03/05/2018
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String CNPJ = "91998846000118";

    @Before
    public void setUp() throws Exception{
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa Fulana de Tal");
        empresa.setCnpj(CNPJ);
        this.empresaRepository.save(empresa);
    }

    @After
    public final void tearDown(){
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testBuscarPorCnpj(){
        Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);
        assertEquals(CNPJ, empresa.getCnpj());

    }
}
