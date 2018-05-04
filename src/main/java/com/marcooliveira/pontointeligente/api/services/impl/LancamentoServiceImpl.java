package com.marcooliveira.pontointeligente.api.services.impl;

import com.marcooliveira.pontointeligente.api.entities.Lancamento;
import com.marcooliveira.pontointeligente.api.repositories.LancamentoRepository;
import com.marcooliveira.pontointeligente.api.services.LancamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Marco Antônio on 03/05/2018
 */

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Override
    public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, Pageable pageable) {
        log.info("Busca lançamentos do funcionario de ID {} ", funcionarioId);
        return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageable);
    }

    @Override
    public Optional<Lancamento> buscarPorId(Long id) {
        log.info("Busca lançamento pelo ID {}", id);
        return this.lancamentoRepository.findById(id);
    }

    @Override
    public Lancamento persistir(Lancamento lancamento) {
        log.info("Persistindo no banco o Lançamento: {}", lancamento);
        return this.lancamentoRepository.save(lancamento);
    }

    @Override
    public void remover(Long id) {
        log.info("Removendo do banco o lançamento de Id {}", id);
        this.lancamentoRepository.deleteById(id);
    }
}
