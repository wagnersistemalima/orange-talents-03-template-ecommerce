package br.com.zupeacademy.wagner.mercadolivre.usuario;

import java.io.Serializable;

public class UsuarioResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	private Long id;
	private String login;
	
	// construtor  
	
	public UsuarioResponse(Usuario entity) {
		this.id = entity.getId();
		this.login = entity.getLogin();
		
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

}
