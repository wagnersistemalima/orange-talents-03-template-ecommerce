package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.zupeacademy.wagner.mercadolivre.exceptions.ResourceNotFoundException;
import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

// objeto para trafegar dados, usuario pergunta algo sobre o produto

public class PerguntaSobreProdutoRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	@NotBlank
	private String titulo;
	
	// construtor com argumento
	
	@JsonCreator
	public PerguntaSobreProdutoRequest(@NotBlank String titulo) {
		
		this.titulo = titulo;
	}
	
	// getters

	public String getTitulo() {
		return titulo;
	}

	// metodo para converter o objeto da requisição em entidade realizando a validação do produto

	public PerguntaSobreProduto toModel(@Valid @NotNull Produto produto, @Valid Usuario cliente) {
		if (produto == null) {
			throw new ResourceNotFoundException("O id do produto não foi encontrado!");
		}
		return new PerguntaSobreProduto(titulo, produto, cliente);
	}
	
	

}
