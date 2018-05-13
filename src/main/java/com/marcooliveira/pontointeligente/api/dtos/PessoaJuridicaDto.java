package com.marcooliveira.pontointeligente.api.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


/**
 * Created by Marco Antônio on 04/05/2018
 */
public class PessoaJuridicaDto {


    private Long id;

    @NotBlank(message = "Nome não pode estar vazio.")
    @Length(min = 3, max = 200, message = "Nome deve estar entre 3 e 300 caracteres.")
    private String nome;

    @NotBlank(message = "Email não pode estar vazio.")
    @Length(min = 5, max = 200, message = "Email deve estar entre 5 e 200 caracteres.")
    @Email(message = "Email inválido.")
    private String email;

    @NotBlank(message = "Senha não pode estar vazia.")
    private String senha;

    @NotBlank(message = "Cpf não pode estar vazio.")
    @CPF(message = "Cpf inválido.")
    private String cpf;

    @NotBlank(message = "Razão Social não pode estar vazio.")
    @Length(min = 3, max = 200, message = "Razão Social deve estar entre 5 e 300 caracteres.")
    private String razaoSocial;

    @NotBlank(message = "Cnpj não pode estar vazio.")
    @CNPJ(message = "Cnpj inválido.")
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

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "PessoaJuridicaDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}
