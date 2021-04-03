package br.com.zupeacademy.wagner.mercadolivre.usuario;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.zupeacademy.wagner.mercadolivre.validation.UniqueValue;

// Objeto para trafegar dados entre o cliente e a aplicação

public class UsuarioRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;

	// atributos basico
	
	@NotBlank(message = "Campo obrigatório")
	@Email(message = "Favor entrar com um email válido")
	@UniqueValue(domainClass = Usuario.class, fieldName = "login", message = "Desculpe, mas já existe usuario com este email")
	private String login;
	
	@Size(min = 6, message = "A senha deve ter no minimo 6 caracter")
	@NotBlank(message = "Campo obrigatório")
	private String senha;
	
	// construtor argumentos
	
	@JsonCreator
	public UsuarioRequest(
			@NotBlank(message = "Campo obrigatório") @Email(message = "Favor entrar com um email válido") String login,
			@Size(min = 6) @NotBlank(message = "Campo obrigatório") String senha) {
		this.login = login;
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

}
