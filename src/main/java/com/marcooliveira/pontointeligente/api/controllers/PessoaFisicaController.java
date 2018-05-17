package com.marcooliveira.pontointeligente.api.controllers;

import com.marcooliveira.pontointeligente.api.dtos.PessoaFisicaDto;
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
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Created by Marco Antônio on 16/05/2018
 */
@RestController
@RequestMapping("/api/cadastro-pf")
@CrossOrigin(origins = {"*"})
public class PessoaFisicaController {

    private static final Logger log = LoggerFactory.getLogger(PessoaFisicaController.class);

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private FuncionarioService funcionarioService;

    /**
     * Cadastra pessoa fisica no sistema
     *
     * @param pessoaFisicaDto
     * @param result
     * @return ResponseEntity<Response<PessoaFisicaDto>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<PessoaFisicaDto>>
                    cadastrar(@Valid @RequestBody PessoaFisicaDto pessoaFisicaDto, BindingResult result)
                throws NoSuchAlgorithmException {
        log.info("Cadastrando Pessoa Fisica {}", pessoaFisicaDto);
        Response<PessoaFisicaDto> response = new Response<>();

        Funcionario funcionario = this.converterDtoParaFuncionario(pessoaFisicaDto, result);

        validarDadosExigentes(pessoaFisicaDto, result);

        if (result.hasErrors()){
            log.info("Erro validando dados de cadastro pf {}", result.getAllErrors());
            result.getAllErrors().forEach(objectError -> response.getErrors().add(objectError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(pessoaFisicaDto.getCnpj());

        empresa.ifPresent(emp -> funcionario.setEmpresa(emp) );
        this.funcionarioService.persistir(funcionario);
        response.setData(this.converterFuncionarioParaDTO(funcionario));
        return ResponseEntity.ok(response);
    }

    /**
     * Valida Empresa e Funcionario já estão cadastrados no sistema
     *
     * @param pessoaFisicaDto
     * @param result
     */
    private void validarDadosExigentes(PessoaFisicaDto pessoaFisicaDto, BindingResult result) {
        Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(pessoaFisicaDto.getCnpj());
        if (empresa.isPresent()){
            result.addError(new ObjectError("empresa", "Empresa não Cadastrada."));
        }
        this.funcionarioService.buscarPorCpf(pessoaFisicaDto.getCpf())
                .ifPresent(func -> result.addError(new ObjectError("funcionario", "Cpf já cadastrado.")));
        this.funcionarioService.buscarPorEmail(pessoaFisicaDto.getEmail())
                .ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já cadastrado.")) );
    }

    /**
     * Converte um funcionario para PessoaFisicaDto para retorno no Response
     *
     * @param funcionario
     * @return PessoaFisicaDto
     */
    private PessoaFisicaDto converterFuncionarioParaDTO(Funcionario funcionario) {
        PessoaFisicaDto pessoaFisicaDto = new PessoaFisicaDto();
        pessoaFisicaDto.setId(funcionario.getId());
        pessoaFisicaDto.setNome(funcionario.getNome());
        pessoaFisicaDto.setCpf(funcionario.getCpf());
        pessoaFisicaDto.setEmail(funcionario.getEmail());
        pessoaFisicaDto.setCnpj(funcionario.getEmpresa().getCnpj());
        funcionario.getQtdHorasAlmocoOpt()
                .ifPresent(qtdHorasAlmoco -> pessoaFisicaDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
        funcionario.getQtdHorasTrabalhoDiaOpt()
                .ifPresent(qtdHorasDia -> pessoaFisicaDto.setQtdHorasTrabalhadasDia(Optional.of(Float.toString(qtdHorasDia))));
        funcionario.getValorHoraOpt()
                .ifPresent(valorHoras -> pessoaFisicaDto.setValorHora(Optional.of(valorHoras.toString())));
        return pessoaFisicaDto;
    }

    /**
     *
     *
     * @param pessoaFisicaDto
     * @param result
     * @return Funcionario
     * @throws NoSuchAlgorithmException
     */
    private Funcionario converterDtoParaFuncionario(
            PessoaFisicaDto pessoaFisicaDto, BindingResult result) throws NoSuchAlgorithmException{
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(pessoaFisicaDto.getNome());
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setCpf(pessoaFisicaDto.getCpf());
        funcionario.setSenha(PassowordUtils.geraBCrypt(pessoaFisicaDto.getSenha()));
        funcionario.setEmail(pessoaFisicaDto.getEmail());
        pessoaFisicaDto.getQtdHorasAlmoco()
                .ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
        pessoaFisicaDto.getQtdHorasTrabalhadasDia()
                .ifPresent(qtdHorasDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasDia)));
        pessoaFisicaDto.getValorHora()
                .ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
        return funcionario;

    }
}
