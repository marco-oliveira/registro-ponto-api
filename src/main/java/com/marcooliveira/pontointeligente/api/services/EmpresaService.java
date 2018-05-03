package com.marcooliveira.pontointeligente.api.services;

import com.marcooliveira.pontointeligente.api.entities.Empresa;

import java.util.Optional;

/**
 * Created by Marco Antônio on 03/05/2018
 */

public interface EmpresaService {

    /**
     * Busca uma Empresa por Cnpj
     *
     * @param cnpj
     * @return Optional<Empresa>
     */
    Optional<Empresa> buscarPorCnpj(String cnpj);

    /**
     * Cadastra no Banco de Dados uma nova Empresa
     *
     * @param empresa
     * @return Empresa
     */
    Empresa persistir(Empresa empresa);
}
