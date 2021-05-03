package com.br.usuariosApi.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.br.usuariosApi.config.validacao.CampoValidacao;
import com.br.usuariosApi.config.validacao.CampoUnico;
import com.br.usuariosApi.controller.dto.UsuarioDto;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false )
	private String nome;
	@Column(unique = true, nullable = false )
	@CampoUnico(campo = CampoValidacao.EMAIL)
	private String email;
	@Column(unique = true, nullable = false)
	@CampoUnico(campo = CampoValidacao.CPF)
	private Long cpf;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(nullable = false )
	private LocalDate dataNascimento;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Endereco> enderecos = new ArrayList<>();
	
	public Usuario() {
	}

	public Usuario(UsuarioDto form) {
		this.nome = form.getNome();
		this.email = form.getEmail();
		this.cpf = Long.valueOf(form.getCpf().replaceAll("[^0-9]", ""));
		this.dataNascimento = form.getDataNascimento();
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

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", cpf=" + cpf + ", dataNascimento="
				+ dataNascimento + ", enderecos=" + enderecos + "]";
	}
	
	
}
