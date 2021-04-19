package br.com.zupeacademy.wagner.mercadolivre.fechamentoCompra;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

// objeto para trafegar dados da requisição para api
// asina o contrato com a interface, e implementa o metodo toTransacao

public class RetornoPayPalRequest implements Serializable, RetornoGatewayPagamento{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	@NotNull
	@Min(0)
	@Max(1)
	private int status;
	
	@NotBlank
	private String idTransacao;
	
	// construtor com argumentos
	
	@JsonCreator
	public RetornoPayPalRequest(@NotNull @Min(0) @Max(1) int status, @NotBlank String idTransacao) {
		
		this.status = status;
		this.idTransacao = idTransacao;
	}
	
	
	// gatters

	public String getIdTransacao() {
		return idTransacao;
	}

	

	public int getStatus() {
		return status;
	}
	
	// metodo para converter a request em entidade, normalizando o status
	
	@Override 
	public Transacao toTransacao(CompraProduto compra) {
		
		if (this.status == 0) {
			return new Transacao(StatusTransacaoEnum.erro, idTransacao, compra);
		}
		return new Transacao(StatusTransacaoEnum.sucesso, idTransacao, compra);
	}

}
