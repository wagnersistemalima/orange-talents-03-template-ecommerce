package br.com.zupeacademy.wagner.mercadolivre.perfil;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

// Entidade que representa os perfis de usuario

@Entity
public class Perfil implements Serializable, GrantedAuthority{
	
	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	// construtor default
	
	@Deprecated
	public Perfil() {
		
	}
	
	// construtor personalizado

	public Perfil(String nome) {
		this.nome = nome;
	}
	
	// getters

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	// HashCode & Equals

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
		Perfil other = (Perfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	// metodo da interface

	@Override
	public String getAuthority() {

		return nome;
	}
	

}
