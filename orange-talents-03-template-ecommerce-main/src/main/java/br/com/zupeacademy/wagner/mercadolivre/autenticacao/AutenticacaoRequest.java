package br.com.zupeacademy.wagner.mercadolivre.autenticacao;

import java.io.Serializable;

import javax.validation.constraints.Email;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.fasterxml.jackson.annotation.JsonCreator;

//  objeto para trafegar dados da requisição de autenticação do cliente

public class AutenticacaoRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	// atributos basicos
	@Email
	private String email;
	private String senha;


	// construtor com argumentos

	@JsonCreator
	public AutenticacaoRequest(@Email String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	public String getNome() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	// metodo para converter

	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(email, senha);
	}

}
