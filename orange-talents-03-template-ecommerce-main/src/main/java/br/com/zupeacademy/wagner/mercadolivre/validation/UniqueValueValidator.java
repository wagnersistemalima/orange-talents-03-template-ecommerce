package br.com.zupeacademy.wagner.mercadolivre.validation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.Assert;

//classe que tem a logica para validar campos com dados que não podem ser cadastrados com duplicidade


public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object>{
	
	// atributos basicos
	
	private String domainAttribute;                             // nome do atributo
	private Class<?> klass;									// nome da classe

	// objeto para acessar o banco
	
	@PersistenceContext
	private EntityManager manager;
	
	// metodo para inicializar os atributos
	
	@Override
	public void initialize(UniqueValue params) {
		domainAttribute = params.fieldName();
		klass = params.domainClass();
	}


	
	// metodo para validar campos que não podem ter duplicidade

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Query query = manager.createQuery("select 1 from "+klass.getName()+" where "+domainAttribute+"=:value");
		query.setParameter("value", value);
		List<?> list = query.getResultList();
		Assert.state(list.size() <=1, "Foi encontrado mais de um "+klass+" com o atributo "+domainAttribute+" = "+value);
		
		return list.isEmpty();            // só estará valido se a lista estiver vazia = true
	}

}
