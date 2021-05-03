package com.br.usuariosApi.controller.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.br.usuariosApi.modelo.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

public class DetalhesUsuarioDto {

	private String nome;
	private String cpf;
	private String email;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	private List<EnderecoDto> enderecos;
	
	public DetalhesUsuarioDto() {
	}

	public DetalhesUsuarioDto(String nome, Long cpf, String email, LocalDate dataNascimento, List<EnderecoDto> enderecos) {
		this.nome = nome;
		this.cpf = String.valueOf(cpf)
					.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})","$1.$2.$3-$4");
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.enderecos = enderecos;
	}

	public DetalhesUsuarioDto(Usuario usuario) {
		this(usuario.getNome(),
				usuario.getCpf(),
				usuario.getEmail(),
				usuario.getDataNascimento(),
				usuario.getEnderecos().
						stream().
						map(EnderecoDto::new).
						collect(Collectors.toList()));
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<EnderecoDto> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoDto> enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public String toString() {
		return "DetalhesUsuarioDto [nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", dataNascimento="
				+ dataNascimento + ", enderecos=" + enderecos + "]";
	}

}
