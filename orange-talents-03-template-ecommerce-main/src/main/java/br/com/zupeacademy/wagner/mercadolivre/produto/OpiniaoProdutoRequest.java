package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;
import br.com.zupeacademy.wagner.mercadolivre.validation.ExistsId;

// objeto para trafegar dados de opiniao do cliente para api

public class OpiniaoProdutoRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	
	@Positive
	@Min(1)
	@Max(5)
	private int nota;
	
	@NotBlank
	private String titulo;
	
	@NotBlank
	@Length(max = 500)
	private String descricao;
	
	@NotNull
	@ExistsId(domainClass = Produto.class, fieldName = "id")
	private Long idProduto;
	
	// construtor com argumentos 
	
	public OpiniaoProdutoRequest(@Positive @Min(1) @Max(5) int nota, @NotBlank String titulo,
			@NotBlank @Length(max = 500) String descricao, @NotNull Long idProduto) {
		this.nota = nota;
		this.titulo = titulo;
		this.descricao = descricao;
		this.idProduto = idProduto;
	}
	
	// getters

	public int getNota() {
		return nota;
	}


	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	@Override
	public String toString() {
		return "OpiniaoProdutoRequest [nota=" + nota + ", titulo=" + titulo + ", descricao=" + descricao
				+ ", idProduto=" + idProduto + "]";
	}

	public OpiniaoProduto toModel(@NotNull @Valid Produto produto, @Valid Usuario cliente) {
		
		return new  OpiniaoProduto(nota, titulo, descricao, produto, cliente);
	}
	

}
