package com.br.usuariosApi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.br.usuariosApi.controller.dto.EnderecoDto;

@FeignClient(url="https://viacep.com.br/ws/", name = "viacep")
public interface CepClient {
	@GetMapping("{cep}/json")
	EnderecoDto getEndereco(@PathVariable String cep);
}
