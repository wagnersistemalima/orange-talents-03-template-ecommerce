package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonCreator;

// objeto para trafegar dados entre a o cliente e a aplicação, dados da compra

public class CompraProdutoRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	@NotNull
	@Positive
	private int quantidade;
	
	@NotNull
	private GatewayPagamento gateway;
	
	// construtor com argumentos
	
	@JsonCreator
	public CompraProdutoRequest(@NotNull @Positive int quantidade, @NotNull GatewayPagamento gateway) {
		this.quantidade = quantidade;
		this.gateway = gateway;
	}

	public int getQuantidade() {
		return quantidade;
	}
	
	public GatewayPagamento getGateway() {
		return gateway;
	}


}
