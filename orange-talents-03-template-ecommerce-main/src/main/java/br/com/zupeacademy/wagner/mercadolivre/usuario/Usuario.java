package br.com.zupeacademy.wagner.mercadolivre.usuario;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.zupeacademy.wagner.mercadolivre.perfil.Perfil;

// Entidade

@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails, Serializable{
	
	private static final long serialVersionUID = 1L;

	// atributos basicos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	@Email(message = "Favor entrar com um email válido")
	@Column(unique = true)
	private String email;
	
	@Size(min = 6)
	@NotBlank(message = "Campo obrigatório")
	private String senha;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant dataCadastro;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Perfil> perfis = new ArrayList<>();
	
	// construtor default
	
	@Deprecated
	public Usuario() {
		
	}
	
	// construtor com argumentos / apenas para a classe de teste
	
	public Usuario(
			@NotBlank(message = "Campo obrigatório") @Email(message = "Favor entrar com um email válido") String email,
			@Size(min = 6) @NotBlank(message = "Campo obrigatório") String senha) {
		this.email = email;
		this.senha = senha;
	}
	
	// construtor personalizado

	public Usuario(
			@NotBlank(message = "Campo obrigatório") @Email(message = "Favor entrar com um email válido") String email,
			@Size(min = 6) @NotBlank(message = "Campo obrigatório") String senha,@NotNull Perfil perfil) {
		this.email = email;
		this.senha = senha;
		this.perfis.add(perfil);
	}
	
	// getters

	public Long getId() {
		return id;
	}
	
	

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public Instant getDataCadastro() {
		return dataCadastro;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	// metodo auxiliar para sempre que for salvar um usuario no banco, armezanar na datacadastro o instante atual.

	@PrePersist
	public void prePersist() {
		dataCadastro = Instant.now();
	}
	
	// metodos da interface UserDetails

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.perfis;
	}

	@Override
	public String getPassword() {
		
		return this.senha;
	}

	@Override
	public String getUsername() {
		
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", senha=" + senha + ", dataCadastro=" + dataCadastro
				+ ", perfis=" + perfis + "]";
	}
	
	

}
