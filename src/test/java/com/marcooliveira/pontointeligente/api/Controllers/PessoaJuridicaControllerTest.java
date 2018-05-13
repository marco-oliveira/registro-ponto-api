package com.marcooliveira.pontointeligente.api.Controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcooliveira.pontointeligente.api.dtos.PessoaJuridicaDto;
import com.marcooliveira.pontointeligente.api.utils.PassowordUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


/**
 * Created by Marco Antônio on 06/05/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PessoaJuridicaControllerTest {

    @Autowired
    private MockMvc mvc;

    private static final String URL_BASE = "/api/cadastro-pj";
    private static final Long ID = 1L;
    private static final String NOME_RESPONSAVEL = "Marco Antônio";
    private static final String RAZAO_SOCIAL = "Teste ltda";
    private static final String CNPJ = "35358133000119" ;
    private static final String CPF = "27472215040" ;
    private static final String EMAIL = "teste@teste.com.br";
    private static final String SENHA = PassowordUtils.geraBCrypt("123456");


    @Test
    @WithMockUser
    public void testCadastrarPessoaJuridica() throws Exception{

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
        .content(this.obterJsonRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.nome").value(NOME_RESPONSAVEL))
                .andExpect(jsonPath("$.data.razaoSocial").value(RAZAO_SOCIAL))
                .andExpect(jsonPath("$.data.cnpj").value(CNPJ))
                .andExpect(jsonPath("$.data.cpf").value(CPF))
                .andExpect(jsonPath("$.data.email").value(EMAIL))
                .andExpect(jsonPath("$.data.senha").isEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());
    }


    private String obterJsonRequisicaoPost() throws JsonProcessingException {
        PessoaJuridicaDto pessoaJuridicaDto = new PessoaJuridicaDto();

        pessoaJuridicaDto.setId(ID);
        pessoaJuridicaDto.setNome(NOME_RESPONSAVEL);
        pessoaJuridicaDto.setRazaoSocial(RAZAO_SOCIAL);
        pessoaJuridicaDto.setCnpj(CNPJ);
        pessoaJuridicaDto.setCpf(CPF);
        pessoaJuridicaDto.setEmail(EMAIL);
        pessoaJuridicaDto.setSenha(SENHA);
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(pessoaJuridicaDto);
    }

}
