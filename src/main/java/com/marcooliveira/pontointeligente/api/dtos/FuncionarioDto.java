package com.marcooliveira.pontointeligente.api.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

/**
 * Created by Marco Antônio on 05/05/2018
 */
public class FuncionarioDto {

    private Long id;

    @NotBlank(message = "Nome não pode estar vazio.")
    @Length(min = 3, max = 200, message = "Nome deve conter entre 5 e 200 caracteres.")
    private String nome;

    @NotBlank(message = "Email não pode ser vazio.")
    @Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
    @Email(message="Email inválido.")
    private String email;

    private Optional<String> senha = Optional.empty();

    private Optional<String> valorHora = Optional.empty();

    private Optional<String> qtdHoraTrabalhoDia = Optional.empty();

    private Optional<String> qtdHorasAlmoco = Optional.empty();

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

    public Optional<String> getSenha() {
        return senha;
    }

    public void setSenha(Optional<String> senha) {
        this.senha = senha;
    }

    public Optional<String> getValorHora() {
        return valorHora;
    }

    public void setValorHora(Optional<String> valorHora) {
        this.valorHora = valorHora;
    }

    public Optional<String> getQtdHoraTrabalhoDia() {
        return qtdHoraTrabalhoDia;
    }

    public void setQtdHoraTrabalhoDia(Optional<String> qtdHoraTrabalhoDia) {
        this.qtdHoraTrabalhoDia = qtdHoraTrabalhoDia;
    }

    public Optional<String> getQtdHorasAlmoco() {
        return qtdHorasAlmoco;
    }

    public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
        this.qtdHorasAlmoco = qtdHorasAlmoco;
    }

    @Override
    public java.lang.String toString() {
        return "FuncionarioDto{" +
                "id=" + id +
                ", nome=" + nome +
                ", email=" + email +
                ", senha=" + senha +
                ", valorHora=" + valorHora +
                ", qtdHoraTrabalhoDia=" + qtdHoraTrabalhoDia +
                ", qtdHorasAlmoco=" + qtdHorasAlmoco +
                '}';
    }
}
