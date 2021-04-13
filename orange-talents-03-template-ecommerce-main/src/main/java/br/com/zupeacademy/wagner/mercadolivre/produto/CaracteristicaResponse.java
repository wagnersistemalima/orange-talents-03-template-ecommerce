package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;

// objeto para trafegar dados de resposta entre a aplicação e o cliente

public class CaracteristicaResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	private String nome;
	
	private String descricao;
	
	
	// construtor personalizado recebendo uma entidade de caracteristica do produto
	
	@JsonCreator
	public CaracteristicaResponse(CaracteristicaProduto entity) {
		this.nome = entity.getNome();
		this.descricao = entity.getDescricao();
	}
	

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}


}
