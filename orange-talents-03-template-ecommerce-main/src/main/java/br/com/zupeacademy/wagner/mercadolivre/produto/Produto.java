package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import br.com.zupeacademy.wagner.mercadolivre.categoria.Categoria;
import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

// entidade

@Entity
@Table(name = "tb_produto")
public class Produto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Campo obrigatorio")
	private String nome;
	
	@NotNull
	@Positive
	private BigDecimal valor;
	
	@Positive
	@NotNull
	private int quantidade;
	
	@NotBlank
	@Length(max = 1000)
	private String descricao;
	
	// associação para categoria e usuario
	
	@NotNull
	@Valid
	@ManyToOne
	private Categoria categoria;
	
	@Valid
	@NotNull
	@ManyToOne
	private Usuario usuarioLogado;
	
	// construtor default
	
	@Deprecated
	public Produto() {
		
	}
	
	// construtor com argumentos

	public Produto(@NotBlank(message = "Campo obrigatorio") String nome, @NotNull @Positive BigDecimal valor,
			@Positive @NotNull int quantidade, @NotBlank @Length(max = 1000) String descricao,
			@NotNull @Valid Categoria categoria,@NotNull @Valid Usuario usuarioLogado) {
		super();
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.categoria = categoria;
		this.usuarioLogado = usuarioLogado;
	}
	
	// Getters

	public Long getId() {
		return id;
	}


	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public Categoria getCategoriaMae() {
		return categoria.getCategoriaMae();
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public String getDescricao() {
		return descricao;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", valor=" + valor + ", quantidade=" + quantidade
				+ ", descricao=" + descricao + ", categoria=" + categoria + ", usuarioLogado=" + usuarioLogado + "]";
	}

	
	

}
