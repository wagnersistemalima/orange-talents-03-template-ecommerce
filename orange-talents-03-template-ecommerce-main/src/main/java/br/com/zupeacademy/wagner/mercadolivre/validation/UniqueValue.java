package br.com.zupeacademy.wagner.mercadolivre.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

// classe generica, para representar uma anotação para o atributo que não pode ter duplicidade de cadastro no banco


@Documented
@Constraint(validatedBy = {UniqueValueValidator.class})
@Target({ FIELD})
@Retention(RUNTIME)
public @interface UniqueValue {
	
	String message() default "Validation error, este dado é unico e já existe cadastro no banco";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
	String fieldName();
	Class<?> domainClass();
}

