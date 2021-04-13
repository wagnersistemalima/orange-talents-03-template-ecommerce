package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
public class CaracteristicaProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basico
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	@Length(max = 1000)
	private String descricao;
	
	// associação com produtos, caracteristica pertence a um produto
	
	@NotNull
	@Valid
	@ManyToOne
	private Produto produto;
	
	// construtor default
	
	@Deprecated
	public CaracteristicaProduto() {
		
	}
	
	// construtor com argumentos

	public CaracteristicaProduto(@NotBlank String nome, @NotBlank @Length(max = 1000) String descricao,
			@NotNull @Valid Produto produto) {
		this.nome = nome;
		this.descricao = descricao;
		this.produto = produto;
	}
	
	
	public String getNome() {
		return nome;
	}
	

	public Produto getProduto() {
		return produto;
	}
	

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	// hashCode & equals comparando pelo produto
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		CaracteristicaProduto other = (CaracteristicaProduto) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	
}
