package com.br.usuariosApi.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class DetalhesUsuarioDtoTest {

	private String nome;
	private String cpf;
	private String email;
	private String dataNascimento;
	private List<EnderecoDto> enderecos = new ArrayList<>();

	public DetalhesUsuarioDtoTest(String nome, String email, String cpf, String dataNascimento, List<EnderecoDto> enderecos) {
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.enderecos = enderecos;
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

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<EnderecoDto> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoDto> enderecos) {
		this.enderecos = enderecos;
	}

}
