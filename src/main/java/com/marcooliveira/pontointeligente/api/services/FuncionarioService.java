package com.marcooliveira.pontointeligente.api.services;

import com.marcooliveira.pontointeligente.api.entities.Funcionario;

import java.util.Optional;

/**
 * Created by Marco Antônio on 03/05/2018
 */

public interface FuncionarioService {

    /**
     * Persistir novo Funcionario no Banco de Dados
     *
     * @param funcionario
     * @return Funcionario
     */
    Funcionario persistir(Funcionario funcionario);

    /**
     * Busca um funcionario por CPF
     *
     * @param cpf
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorCpf(String cpf);

    /**
     * Busca um funcionario por Email
     *
     * @param email
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorEmail(String email);

    /**
     * Busca um funcionario por Id
     *
     * @param id
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorId(Long id);


}
