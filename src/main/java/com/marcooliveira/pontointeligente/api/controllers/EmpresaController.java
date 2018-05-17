package com.marcooliveira.pontointeligente.api.controllers;

import com.marcooliveira.pontointeligente.api.dtos.EmpresaDto;
import com.marcooliveira.pontointeligente.api.entities.Empresa;
import com.marcooliveira.pontointeligente.api.response.Response;
import com.marcooliveira.pontointeligente.api.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Marco Antônio on 17/05/2018
 */
@RestController
@RequestMapping("/api/empresa")
@CrossOrigin(origins = {"*"})
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<Response<EmpresaDto>> buscaPorCnpj(@PathVariable String cnpj){
        this.log.info("Buscando Empresa pelo Cnpj {}", cnpj);
        Response<EmpresaDto> response = new Response<EmpresaDto>();

        Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cnpj);

        if (!empresa.isPresent()){
            this.log.info("Empresa não encontrada para o Cnpj: {}", cnpj);
            response.getErrors().add("Empresa não encontrada para o Cnpj "+cnpj);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.converterEmpresaParaDto(empresa.get()));

        return ResponseEntity.ok(response);
    }

    private EmpresaDto converterEmpresaParaDto(Empresa empresa) {
        EmpresaDto empresaDto = new EmpresaDto();
        empresaDto.setId(empresa.getId());
        empresaDto.setRazaoSocial(empresa.getRazaoSocial());
        empresaDto.setCnpj(empresa.getCnpj());

        return empresaDto;

    }
}
