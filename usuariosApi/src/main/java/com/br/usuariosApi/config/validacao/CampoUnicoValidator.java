package com.br.usuariosApi.config.validacao;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.br.usuariosApi.config.BeanUtil;
import com.br.usuariosApi.repository.UsuarioRepository;

public class CampoUnicoValidator implements ConstraintValidator<CampoUnico, Object> {

	private UsuarioRepository repository;
	private EntityManager em;

	private CampoValidacao campo;

	@Override
	public void initialize(CampoUnico constraintAnnotation) {
		campo = constraintAnnotation.campo();
		repository = BeanUtil.getBean(UsuarioRepository.class);
		em = BeanUtil.getBean(EntityManager.class);
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		try {
			// Forçar para a transação ser somente de leitura resetando o entity manager dessa maneira
	        em.setFlushMode(FlushModeType.COMMIT);
	        
	        if(CampoValidacao.CPF.equals(campo)) {
				return repository.findByCpf((Long) value).isEmpty();
			}

			if (CampoValidacao.EMAIL.equals(campo)) {
				return repository.findByEmail((String) value).isEmpty();
			}
	    } finally {
	        em.setFlushMode(FlushModeType.AUTO);
	    }

		return true;
	}

}
