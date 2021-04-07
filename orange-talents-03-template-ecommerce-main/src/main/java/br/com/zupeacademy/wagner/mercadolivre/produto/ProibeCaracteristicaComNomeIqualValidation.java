package br.com.zupeacademy.wagner.mercadolivre.produto;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// classe contendo a logica de validação para caracteristicas do produto, não pode ter caracteristicas iquais

public class ProibeCaracteristicaComNomeIqualValidation implements Validator {
	
	// 1 metodo, verifica se a classe é produtoRequest ou filha

	@Override
	public boolean supports(Class<?> clazz) {
		
		return ProdutoRequest.class.isAssignableFrom(clazz);
	}
	
	// 2 metodo regra de validação, só vai realizar a logica se não tiver erros

	@Override
	public void validate(Object target, Errors errors) {
		if (errors.hasErrors()) {
			return;
		}
		ProdutoRequest request = (ProdutoRequest) target;
		
		if (request.temCaracteristicasIguais()) {           // se retornar ture
			errors.rejectValue("caracteristicas", null, "Você está tentando cadastrar uma caracteristica já existente");
		}

	}

}
