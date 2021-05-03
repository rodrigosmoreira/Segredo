package com.br.usuariosApi.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.br.usuariosApi.modelo.Endereco;

public class EnderecoDto {

	@NotNull
	@Pattern(message = "CEP está com formato invalido", regexp = "[0-9]{5}-[0-9]{3}")
	private String cep;
	@NotEmpty
	private String logradouro;
	@NotEmpty
	private String numero;
	@NotEmpty
	private String bairro;
	@NotEmpty
	private String cidade;
	@NotEmpty
	private String estado;
	@NotEmpty
	@Email
	private String emailUsuario;
	
	public EnderecoDto() {
	}
	
	public EnderecoDto(String cep, String logradouro, String numero, String bairro, String cidade, String estado, String emailUsuario) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.emailUsuario = emailUsuario;
	}

	public EnderecoDto(Endereco endereco) {
		this(endereco.getCep(), 
				endereco.getLogradouro(), 
				endereco.getNumero(), 
				endereco.getBairro(), 
				endereco.getCidade(), 
				endereco.getEstado(), "");
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

}
