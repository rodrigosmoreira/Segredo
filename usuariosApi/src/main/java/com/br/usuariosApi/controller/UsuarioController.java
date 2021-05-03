package com.br.usuariosApi.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.br.usuariosApi.client.CepClient;
import com.br.usuariosApi.controller.dto.DetalhesUsuarioDto;
import com.br.usuariosApi.controller.dto.EnderecoDto;
import com.br.usuariosApi.controller.dto.EnderecoViaCepDto;
import com.br.usuariosApi.controller.dto.UsuarioDto;
import com.br.usuariosApi.exception.UsuarioNaoCadastradoException;
import com.br.usuariosApi.modelo.Endereco;
import com.br.usuariosApi.modelo.Usuario;
import com.br.usuariosApi.repository.EnderecoRepository;
import com.br.usuariosApi.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private CepClient cepService;
	
	@PostMapping
	public ResponseEntity<UsuarioDto> cadastrarUsuario(@RequestBody @Valid UsuarioDto form, UriComponentsBuilder uriBuilder) {
		Usuario usuario = new Usuario(form);
		
		usuarioRepository.save(usuario);
		
		URI uri = uriBuilder.path("/usuario/{email}").buildAndExpand(usuario.getEmail()).toUri();
		
		return ResponseEntity.created(uri).body(form);
	}
	
	@PostMapping("/enderecoViaCep")
	public ResponseEntity<EnderecoDto> cadastrarEnderecoViaCep(@RequestBody @Valid EnderecoViaCepDto form, UriComponentsBuilder uriBuilder) {
		
		EnderecoDto enderecoViaCep = cepService.getEndereco(form.getCep());
		
		enderecoViaCep.setEmailUsuario(form.getEmailUsuario());
		
		return cadastrarEndereco(enderecoViaCep, uriBuilder);
	}
	
	@PostMapping("/endereco")
	public ResponseEntity<EnderecoDto> cadastrarEndereco(@RequestBody @Valid EnderecoDto form, UriComponentsBuilder uriBuilder) {
		
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(form.getEmailUsuario());
		
		if(usuarioOptional.isPresent()) {
			Endereco endereco = new Endereco(form);
			
			endereco.setUsuario(usuarioOptional.get());
			
			enderecoRepository.save(endereco);
			
			URI uri = uriBuilder.path("/usuario/{email}").buildAndExpand(form.getEmailUsuario()).toUri();
			
			return ResponseEntity.created(uri).body(form);
		}
		
		throw new UsuarioNaoCadastradoException();
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<DetalhesUsuarioDto> detalhesCadastroUsuario(@PathVariable @NotEmpty @Email String email) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
		
		if (usuarioOptional.isPresent()) {
			return ResponseEntity.ok(new DetalhesUsuarioDto(usuarioOptional.get()));
		}

		throw new UsuarioNaoCadastradoException();
	}
	
}
