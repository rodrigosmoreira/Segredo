package com.br.usuariosApi.controller.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.br.CPF;

import com.br.usuariosApi.modelo.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

public class UsuarioDto {

	@NotEmpty
	private String nome;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotNull
	@CPF
	private String cpf;
	
	@NotNull
	@Past
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	public UsuarioDto() {
	}
	
	public UsuarioDto(Usuario usuario) {
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.cpf = String.valueOf(usuario.getCpf())
						.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})","$1.$2.$3-$4");
		this.dataNascimento = usuario.getDataNascimento();
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
