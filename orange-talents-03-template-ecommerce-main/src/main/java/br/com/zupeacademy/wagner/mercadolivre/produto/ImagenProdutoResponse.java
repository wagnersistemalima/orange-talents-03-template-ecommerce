package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;

// objeto para trafegar dados de resposta entre a aplicação e o cliente, carregar os links das imagens

public class ImagenProdutoResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	private String link;
	
	// construtor personalizado
	
	@JsonCreator
	public ImagenProdutoResponse(ImagenProduto entity) {
		this.link = entity.getLink();
	}

	public String getLink() {
		return link;
	}
	
	

}
