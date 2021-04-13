package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import br.com.zupeacademy.wagner.mercadolivre.exceptions.ResourceNotFoundException;
import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

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
	
	
	// construtor com argumentos 
	
	public OpiniaoProdutoRequest(@Positive @Min(1) @Max(5) int nota, @NotBlank String titulo,
			@NotBlank @Length(max = 500) String descricao) {
		this.nota = nota;
		this.titulo = titulo;
		this.descricao = descricao;
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


	public OpiniaoProduto toModel(@NotNull @Valid Produto produto, @Valid Usuario cliente) {
		
		if (produto == null) {
			throw new ResourceNotFoundException("O id do produto não foi encontrado");
		}
		
		return new  OpiniaoProduto(nota, titulo, descricao, produto, cliente);
	}
	

}
