package br.com.zupeacademy.wagner.mercadolivre.fechamentoCompra;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

// objeto para trafegar dados do cliente para api, com dados do pagSeguro

public class RetornoPagSeguroRequest implements Serializable, RetornoGatewayPagamento{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	@NotBlank
	private String idTransacao;
	
	@NotNull
	private StatusRetornoPagSeguroEnum status;
	
	// construtor com argumentos
	
	@JsonCreator
	public RetornoPagSeguroRequest(@NotBlank String idTransacao,
			@NotNull StatusRetornoPagSeguroEnum status) {
		this.idTransacao = idTransacao;
		this.status = status;
	}
	
	// getters

	public String getIdDopagamento() {
		return idTransacao;
	}

	public StatusRetornoPagSeguroEnum getStatusDaCompra() {
		return status;
	}

	
	// metodo para converter a requisição em entidade
	
	@Override
	public Transacao toTransacao(CompraProduto compra) {
		
		return new Transacao(status.normaliza(), idTransacao, compra);
	}

}
