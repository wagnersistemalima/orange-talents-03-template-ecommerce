package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;

// objeto para trafegar dados de resposta, entre a aplicação e o cliente, contendo os dados de opiniao dos clientes 
// sobre os produtos

public class OpiniaoProdutoResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	 private int nota;
	
	 private String titulo;
	 
	 private String descricao;
	 
	 private String donoOpiniao;
	 
	 
	 // construtor com argumentos
	 
	 @JsonCreator
	 public OpiniaoProdutoResponse(OpiniaoProduto entity) {
		 this.nota = entity.getNota();
		 this.titulo = entity.getTitulo();
		 this.descricao = entity.getDescricao();
		 this.donoOpiniao = entity.getCliente().getEmail();
		 
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

	public String getDonoOpiniao() {
		return donoOpiniao;
	}


}
