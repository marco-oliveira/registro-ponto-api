package com.marcooliveira.pontointeligente.api.services;


import com.marcooliveira.pontointeligente.api.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Created by Marco Antônio on 03/05/2018
 */

public interface LancamentoService {

    /**
     * Retorna uma lista paginada de lancamentos por funcionarios
     *
     * @param funcionarioId
     * @param pageable
     * @return Page<Lancamento>
     */
    Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, Pageable pageable);

    /**
     * Busca um lançamento pelo seu ID
     *
     * @param id
     * @return Optional<Lancamento>
     */
    Optional<Lancamento> buscarPorId(Long id);

    /**
     * Persiste um Lançamento no banco de dados
     *
     * @param lancamento
     * @return Lançamento
     */
    Lancamento persistir(Lancamento lancamento);

    /**
     * Remove um lancamento pelo ID
     *
     * @param id
     */
    void remover(Long id);
}
