package com.br.usuariosApi.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class EnderecoViaCepDto {
	
	@NotNull
	@Pattern(message = "CEP está com formato invalido", regexp = "[0-9]{5}-[0-9]{3}")
	private String cep;
	@NotEmpty
	@Email
	private String emailUsuario;
	
	public EnderecoViaCepDto() {
	}

	public EnderecoViaCepDto(String cep, String emailUsuario) {
		this.cep = cep;
		this.emailUsuario = emailUsuario;
	}

	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getEmailUsuario() {
		return emailUsuario;
	}
	
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
}
