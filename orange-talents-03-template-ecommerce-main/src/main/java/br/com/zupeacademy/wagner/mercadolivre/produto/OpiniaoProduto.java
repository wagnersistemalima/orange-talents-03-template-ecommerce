package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

// Entidade

@Entity
public class OpiniaoProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Min(1)
	@Max(5)
	private int nota;
	
	@NotBlank
	private String titulo;
	
	@NotBlank
	@Length(max = 500)
	private String descricao;
	
	// associação com produto / varias opinioes para um produto
	
	@Valid
	@NotNull
	@ManyToOne
	private Produto produto;
	
	// associação com usuario / varias opinioes para um usuario
	
	@Valid
	@NotNull
	@ManyToOne
	private Usuario cliente;
	
	// construtor default
	
	@Deprecated
	public OpiniaoProduto() {
		
	}
	
	// construtor com argumentos

	public OpiniaoProduto( @Min(1) @Max(5) int nota, @NotBlank String titulo,
			@NotBlank @Length(max = 500) String descricao, @Valid @NotNull Produto produto,
			@Valid @NotNull Usuario cliente) {
		this.nota = nota;
		this.titulo = titulo;
		this.descricao = descricao;
		this.produto = produto;
		this.cliente = cliente;
	}
	
	// getters

	public Long getId() {
		return id;
	}

	public int getNota() {
		return nota;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Produto getProduto() {
		return produto;
	}

	public Usuario getCliente() {
		return cliente;
	}
	
	// hashCode & equals comparando pelo titulo e descrição

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		OpiniaoProduto other = (OpiniaoProduto) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

}
