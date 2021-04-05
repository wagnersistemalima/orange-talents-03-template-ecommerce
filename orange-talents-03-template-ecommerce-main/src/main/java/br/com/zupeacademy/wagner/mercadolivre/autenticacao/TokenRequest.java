package br.com.zupeacademy.wagner.mercadolivre.autenticacao;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;

//objeto para trafegar o token

public class TokenRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	private String token;
	private String tipo;
	
	// construtor personalizado
	
	@Deprecated
	public TokenRequest() {
		
	}
	
	@JsonCreator
	public TokenRequest(String token, String tipo) {
		this.token = token;
		this.tipo = tipo;
	}

	public String getToken() {
		return token;
	}

	public String getTipo() {
		return tipo;
	}
	

}
