package br.com.zupeacademy.wagner.mercadolivre.exceptions;

import java.io.Serializable;

// Classe auxiliar para carregar o nome do campo e as mensagem de respostas dos dados que não foram validados

public class FieldMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	private String fieldName;            
	private String message;
	
	// construtor default
	
	@Deprecated
	public FieldMessage() {
		
	}
	
	// construtor com argumentos

	public FieldMessage(String fieldName, String message) {
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getMessage() {
		return message;
	}
	
	

}
