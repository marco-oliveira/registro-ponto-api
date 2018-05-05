package com.marcooliveira.pontointeligente.api.dtos;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

/**
 * Created by Marco Antônio on 05/05/2018
 */

public class PessoaFisicaDto {

    private Long id;

    @NotBlank(message = "O nome não pode estar vazio.")
    @Length(min = 3, max = 200, message = "O nome deve estar entre 3 e 200 caracteres.")
    private String nome;

    @NotEmpty(message = "Email não pode ser vazio.")
    @Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
    @Email(message = "Email inválido.")
    private String email;

    @NotBlank(message = "A senha não pode estar vazia.")
    private String senha;

    @CPF(message = "Cpf inválido.")
    @NotBlank(message = "O Cpf não pode estar vazio.")
    private String cpf;

    private Optional<String> valorHora = Optional.empty();

    private Optional<String> qtdHorasTrabalhadasDia = Optional.empty();

    private Optional<String> qtdHorasAlmoco = Optional.empty();

    @CNPJ(message = "Cnpj inválido.")
    @NotBlank(message = "O Cnpj não pode estar vazio.")
    private String cnpj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Optional<String> getValorHora() {
        return valorHora;
    }

    public void setValorHora(Optional<String> valorHora) {
        this.valorHora = valorHora;
    }

    public Optional<String> getQtdHorasTrabalhadasDia() {
        return qtdHorasTrabalhadasDia;
    }

    public void setQtdHorasTrabalhadasDia(Optional<String> qtdHorasTrabalhadasDia) {
        this.qtdHorasTrabalhadasDia = qtdHorasTrabalhadasDia;
    }

    public Optional<String> getQtdHorasAlmoco() {
        return qtdHorasAlmoco;
    }

    public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
        this.qtdHorasAlmoco = qtdHorasAlmoco;
    }

    @Override
    public String toString() {
        return "PessoaFisicaDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                ", valorHora=" + valorHora +
                ", qtdHorasTrabalhadasDia=" + qtdHorasTrabalhadasDia +
                ", qtdHorasAlmoco=" + qtdHorasAlmoco +
                '}';
    }
}
