package com.marcooliveira.pontointeligente.api.repositories;

import com.marcooliveira.pontointeligente.api.entities.Empresa;
import com.marcooliveira.pontointeligente.api.entities.Funcionario;
import com.marcooliveira.pontointeligente.api.entities.Lancamento;
import com.marcooliveira.pontointeligente.api.enums.PerfilEnum;
import com.marcooliveira.pontointeligente.api.enums.TipoEnum;
import com.marcooliveira.pontointeligente.api.utils.PassowordUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Marco Antônio on 03/05/2018
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private Long funcionarioId;

    @Before
    public void setUp() throws Exception{
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
        Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
        this.funcionarioId = funcionario.getId();

        this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
        this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
    }

    @After
    public void tearDown() throws Exception{
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testBuscarLancamentoPorFuncionarioId(){
        List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(this.funcionarioId);
        assertEquals(2, lancamentos.size());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testBuscarLancamentoPorFuncionarioIdPaginado(){
        PageRequest page = new PageRequest(0, 10);
        Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(this.funcionarioId, page);
        assertEquals(2, lancamentos.getTotalElements());
    }

    private Lancamento obterDadosLancamentos(Funcionario funcionario){
        Lancamento lancamento = new Lancamento();
        lancamento.setData(new Date());
        lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
        lancamento.setFuncionario(funcionario);
        return lancamento;
    }

    private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Siclano de Tal");
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PassowordUtils.geraBCrypt("123456"));
        funcionario.setCpf("82905323000");
        funcionario.setEmail("fulano@email.com.br");
        funcionario.setEmpresa(empresa);
        return funcionario;

    }

    private Empresa obterDadosEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa Fulano de Tal");
        empresa.setCnpj("91998846000118");
        return empresa;
    }
}
