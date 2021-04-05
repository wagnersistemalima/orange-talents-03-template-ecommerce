package br.com.zupeacademy.wagner.mercadolivre.usuario;

import java.io.Serializable;

public class UsuarioResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	private Long id;
	private String email;
	
	// construtor  
	
	public UsuarioResponse(Usuario entity) {
		this.id = entity.getId();
		this.email = entity.getEmail();
		
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}


}
