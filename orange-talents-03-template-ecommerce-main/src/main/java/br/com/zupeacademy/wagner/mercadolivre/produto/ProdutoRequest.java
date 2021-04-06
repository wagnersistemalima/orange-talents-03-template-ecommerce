package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.zupeacademy.wagner.mercadolivre.categoria.Categoria;
import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;
import br.com.zupeacademy.wagner.mercadolivre.validation.ExistsId;

// objeto para trafegar dados do cliente para aplicação

public class ProdutoRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
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
	
	@NotNull
	@ExistsId(domainClass = Categoria.class, fieldName = "id")
	private Long idCategoria;
	
	// construtor com argumentos
	
	@JsonCreator
	public ProdutoRequest(@NotBlank(message = "Campo obrigatorio") String nome, @NotNull @Positive BigDecimal valor,
			@Positive @NotNull int quantidade, @NotBlank @Length(max = 1000) String descricao,
			@NotNull Long idCategoria) {
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.idCategoria = idCategoria;
	}
	
	// getters

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

	public Long getIdCategoria() {
		return idCategoria;
	}

	@Override
	public String toString() {
		return "ProdutoRequest [nome=" + nome + ", valor=" + valor + ", quantidade=" + quantidade + ", descricao="
				+ descricao + ", idCategoria=" + idCategoria + "]";
	}

	public Produto toModel(EntityManager manager,@AuthenticationPrincipal @Valid Usuario usuarioLogado) {
		Categoria categoria = manager.find(Categoria.class, idCategoria);
		return new Produto(nome, valor, quantidade, descricao, categoria, usuarioLogado);
	}


	
}
