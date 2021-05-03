package com.br.usuariosApi.config.validacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.br.usuariosApi.exception.UsuarioNaoCadastradoException;

@RestControllerAdvice
public class CampoErroHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<CampoErroDto> handle(MethodArgumentNotValidException exception) {
		ArrayList<CampoErroDto> errosCampo = new ArrayList<>();
		
		List<FieldError> erros = exception.getBindingResult().getFieldErrors();
		
		erros.forEach(erro -> {
			String mensagemErro = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
				
			errosCampo.add(new CampoErroDto(erro.getField(), mensagemErro));
		});
		
		return errosCampo;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public List<CampoErroDto> handle(ConstraintViolationException exception) {
		ArrayList<CampoErroDto> errosCampo = new ArrayList<>();
		
		Set<ConstraintViolation<?>> erros = exception.getConstraintViolations();
		
		erros.forEach(erro -> {
			errosCampo.add(new CampoErroDto(erro.getPropertyPath().toString(), erro.getMessageTemplate()));
		});
		
		return errosCampo;
	}
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(UsuarioNaoCadastradoException.class)
	public List<CampoErroDto> handle(UsuarioNaoCadastradoException exception) {
		return new ArrayList<>(Arrays.asList(new CampoErroDto("emailUsuario", "não foi encontrando um usuario com o email informado")));
	}
	
}
