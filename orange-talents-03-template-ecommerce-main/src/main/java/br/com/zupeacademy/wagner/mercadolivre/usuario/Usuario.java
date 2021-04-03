package br.com.zupeacademy.wagner.mercadolivre.usuario;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// Entidade

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	// atributos basicos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	@Email(message = "Favor entrar com um email válido")
	@Column(unique = true)
	private String login;
	
	@Size(min = 6)
	@NotBlank(message = "Campo obrigatório")
	private String senha;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant dataCadastro;
	
	// construtor default
	
	@Deprecated
	public Usuario() {
		
	}
	
	// construtor com argumento

	public Usuario(
			@NotBlank(message = "Campo obrigatório") @Email(message = "Favor entrar com um email válido") String login,
			@Size(min = 6) @NotBlank(message = "Campo obrigatório") String senha) {
		this.login = login;
		this.senha = senha;
	}
	
	// getters

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	public Instant getDataCadastro() {
		return dataCadastro;
	}
	
	// metodo auxiliar para sempre que for salvar um usuario no banco, armezanar na datacadastro o instante atual.
	
	@PrePersist
	public void prePersist() {
		dataCadastro = Instant.now();
	}

}
