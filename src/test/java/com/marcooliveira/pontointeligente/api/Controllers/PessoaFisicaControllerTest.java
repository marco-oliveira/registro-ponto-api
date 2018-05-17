package com.marcooliveira.pontointeligente.api.Controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcooliveira.pontointeligente.api.dtos.PessoaFisicaDto;
import com.marcooliveira.pontointeligente.api.entities.Empresa;
import com.marcooliveira.pontointeligente.api.entities.Funcionario;
import com.marcooliveira.pontointeligente.api.enums.PerfilEnum;
import com.marcooliveira.pontointeligente.api.services.EmpresaService;
import com.marcooliveira.pontointeligente.api.services.FuncionarioService;
import com.marcooliveira.pontointeligente.api.utils.PassowordUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.Optional;


/**
 * Created by Marco Antônio on 16/05/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PessoaFisicaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FuncionarioService funcionarioService;

    @MockBean
    private EmpresaService empresaService;

    private static final String URL_BASE = "/api/cadastro-pf";
    private static final Long ID = 1L;
    private static final String NOME = "Marco Antônio";
    private static final String RAZAO_SOCIAL = "Teste ltda";
    private static final String CNPJ = "35358133000119" ;
    private static final String CPF = "27472215040" ;
    private static final String EMAIL = "teste@teste.com.br";
    private static final String SENHA = PassowordUtils.geraBCrypt("123456");
    private static final Date DATA = new Date();
    private static final String VALOR_HORA = null ;

    @Test
    @WithMockUser
    public void testeCadastrarPessoaFisicaComEmpresaNaoCadastrada() throws Exception{
        Funcionario funcionario = obterDadosFuncionario();
        BDDMockito.when(this.empresaService.buscarPorCnpj(CNPJ)).thenReturn(Optional.of(funcionario.getEmpresa()));

        BDDMockito.given(this.funcionarioService.persistir(Mockito.any(Funcionario.class))).willReturn(funcionario);
        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(this.obterJsonRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    private String obterJsonRequisicaoPost() throws JsonProcessingException {
        PessoaFisicaDto pessoaFisicaDto = new PessoaFisicaDto();
        pessoaFisicaDto.setId(ID);
        pessoaFisicaDto.setNome(NOME);
        pessoaFisicaDto.setCnpj(CNPJ);
        pessoaFisicaDto.setEmail(EMAIL);
        pessoaFisicaDto.setCpf(CPF);
        pessoaFisicaDto.setValorHora(null);
        pessoaFisicaDto.setQtdHorasAlmoco(null);
        pessoaFisicaDto.setQtdHorasTrabalhadasDia(null);
        pessoaFisicaDto.setSenha(SENHA);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(pessoaFisicaDto);
    }


    private Funcionario obterDadosFuncionario(){
        Funcionario funcionario = new Funcionario();
        funcionario.setId(ID);
        funcionario.setNome(NOME);
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setCpf(CPF);
        funcionario.setSenha(SENHA);
        funcionario.setEmail(EMAIL);
        funcionario.setEmpresa(new Empresa());
        funcionario.getEmpresa().setCnpj(CNPJ);
        funcionario.getEmpresa().setId(ID);
        funcionario.getEmpresa().setRazaoSocial(RAZAO_SOCIAL);
        funcionario.getEmpresa().setDataCriacao(DATA);
        funcionario.getEmpresa().setDataAtualizacao(DATA);
        return funcionario;
    }
}
