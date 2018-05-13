package com.marcooliveira.pontointeligente.api.controllers;

import com.marcooliveira.pontointeligente.api.dtos.PessoaJuridicaDto;
import com.marcooliveira.pontointeligente.api.entities.Empresa;
import com.marcooliveira.pontointeligente.api.entities.Funcionario;
import com.marcooliveira.pontointeligente.api.enums.PerfilEnum;
import com.marcooliveira.pontointeligente.api.response.Response;
import com.marcooliveira.pontointeligente.api.services.EmpresaService;
import com.marcooliveira.pontointeligente.api.services.FuncionarioService;
import com.marcooliveira.pontointeligente.api.utils.PassowordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Marco Antônio on 05/05/2018
 */
@RestController
@RequestMapping("/api/cadastro-pj")
@CrossOrigin(origins = {"*"})
public class PessoaJuridicaController {

    private static final Logger log = LoggerFactory.getLogger(PessoaJuridicaController.class);

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private FuncionarioService funcionarioService;

    /**
     * Cadastra Pessoa Juridica no Sistema.
     *
     * @param pessoaJuridicaDto
     * @param result
     * @return ResponseEntity<Response<PessoaJuridicaDto>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<PessoaJuridicaDto>> 
            cadastrar(@Valid @RequestBody PessoaJuridicaDto pessoaJuridicaDto,
                      BindingResult result) throws NoSuchAlgorithmException {
            log.info("Cadastrando PJ : {}", pessoaJuridicaDto);

        Response<PessoaJuridicaDto> response = new Response<>();

        validarDadosExistentes(pessoaJuridicaDto, result);
        Empresa empresa = this.converterDtoParaEmpresa(pessoaJuridicaDto);
        Funcionario funcionario = this.converterDtoParaFuncionario(pessoaJuridicaDto, result);

        if (result.hasErrors()){
            log.info("Erro validando dados de cadastro PJ: {}", result.getAllErrors());
            //Se existir algum erro, adiciona na lista de erros do Response
            result.getAllErrors().forEach(objectError -> response.getErrors().add(objectError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.empresaService.persistir(empresa);
        funcionario.setEmpresa(empresa);
        this.funcionarioService.persistir(funcionario);

        response.setData(this.converterPessoaJuridicaDto(funcionario));

        return ResponseEntity.ok(response);


    }

    /**
     * Popula Dto para ser usado na Response
     *
     * @param funcionario
     * @return PessoaJuridicaDto
     */
    private PessoaJuridicaDto converterPessoaJuridicaDto(Funcionario funcionario) {
        PessoaJuridicaDto pessoaJuridicaDto = new PessoaJuridicaDto();
        pessoaJuridicaDto.setId(funcionario.getId());
        pessoaJuridicaDto.setNome(funcionario.getNome());
        pessoaJuridicaDto.setCpf(funcionario.getCpf());
        pessoaJuridicaDto.setEmail(funcionario.getEmail());
        pessoaJuridicaDto.setCnpj(funcionario.getEmpresa().getCnpj());
        pessoaJuridicaDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());

        return pessoaJuridicaDto;
    }

    /**
     * Converte DTO para Funcionario
     *
     * @param pessoaJuridicaDto
     * @param result
     * @return Funcionario
     * @throws NoSuchAlgorithmException
     */
    private Funcionario converterDtoParaFuncionario(
            PessoaJuridicaDto pessoaJuridicaDto, BindingResult result) throws NoSuchAlgorithmException{
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(pessoaJuridicaDto.getNome());
        funcionario.setEmail(pessoaJuridicaDto.getEmail());
        funcionario.setCpf(pessoaJuridicaDto.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
        funcionario.setSenha(PassowordUtils.geraBCrypt(pessoaJuridicaDto.getSenha()));
        return funcionario;
    }

    /**
     *
     * Converte DTO para Empresa
     *
     * @param pessoaJuridicaDto
     * @return empresa
     */
    private Empresa converterDtoParaEmpresa(PessoaJuridicaDto pessoaJuridicaDto) {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(pessoaJuridicaDto.getRazaoSocial());
        empresa.setCnpj(pessoaJuridicaDto.getCnpj());
        return empresa;
    }

    /**
     * Valida se empresa ou funcionario já existem na base de dados
     *
     * @param pessoaJuridicaDto
     * @param result
     */
    private void validarDadosExistentes(PessoaJuridicaDto pessoaJuridicaDto, BindingResult result) {
        this.empresaService.buscarPorCnpj(pessoaJuridicaDto.getCnpj())
                .ifPresent(empresa -> result.addError(new ObjectError("empresa",
                        "Empresa já Existente")));

        this.funcionarioService.buscarPorCpf(pessoaJuridicaDto.getCpf())
                .ifPresent(funcionario -> result.addError(new ObjectError("funcionario",
                        "Funcionario já existente.")));

        this.funcionarioService.buscarPorEmail(pessoaJuridicaDto.getEmail())
                .ifPresent(funcionario -> result.addError(new ObjectError("funcionario",
                        "Funcionario já existente.")));
    }

}
