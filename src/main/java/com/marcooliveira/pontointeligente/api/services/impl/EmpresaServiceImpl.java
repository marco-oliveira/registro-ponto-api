package com.marcooliveira.pontointeligente.api.services.impl;

import com.marcooliveira.pontointeligente.api.entities.Empresa;
import com.marcooliveira.pontointeligente.api.repositories.EmpresaRepository;
import com.marcooliveira.pontointeligente.api.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Marco Antônio on 03/05/2018
 */

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public Optional<Empresa> buscarPorCnpj(String cnpj) {
        log.info("Buscando Empresa pelo CNPJ {}", cnpj);
        return Optional.ofNullable(this.empresaRepository.findByCnpj(cnpj));
    }

    @Override
    public Empresa persistir(Empresa empresa) {
        log.info("Persistindo Empresa: {}", empresa);
        return this.empresaRepository.save(empresa);
    }
}
