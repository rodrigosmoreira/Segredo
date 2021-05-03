package com.br.usuariosApi.controller.helper;

import java.text.MessageFormat;

import com.br.usuariosApi.config.validacao.CampoErroDto;
import com.br.usuariosApi.controller.dto.DetalhesUsuarioDtoTest;
import com.br.usuariosApi.controller.dto.EnderecoDto;
import com.br.usuariosApi.controller.dto.EnderecoViaCepDto;
import com.br.usuariosApi.controller.dto.UsuarioDtoTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class UsuarioControllerTestHelper {

	private ObjectWriter writter = new ObjectMapper().writer().withDefaultPrettyPrinter();

	public String retornaJsonUsuario(String nome, String email, String cpf, String dataNascimento) throws JsonProcessingException {
		return writter.writeValueAsString(new UsuarioDtoTest(nome, email, cpf, dataNascimento));
	}

	public String retornaJsonCampoErro(String campo, String erro) throws JsonProcessingException {
		return MessageFormat.format("[{0}]", writter.writeValueAsString(new CampoErroDto(campo, erro)));
	}

	public String retornaJsonEnderecoViaCep(String cep, String emailUsuario) throws JsonProcessingException {
		return writter.writeValueAsString(new EnderecoViaCepDto(cep, emailUsuario));
	}
	
	public String retornaJsonEndereco(String cep, String logradouro, String numero, String bairro, String cidade, String estado, String emailUsuario) throws JsonProcessingException {
		return writter.writeValueAsString(new EnderecoDto(cep, logradouro, numero, bairro, cidade, estado, emailUsuario));
	}
	
	public String retornaJson(DetalhesUsuarioDtoTest detalhesUsuarioDtoTest) throws JsonProcessingException {
		return writter.writeValueAsString(detalhesUsuarioDtoTest);
	}
	
}
