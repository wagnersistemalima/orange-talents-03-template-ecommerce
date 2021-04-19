package br.com.zupeacademy.wagner.mercadolivre.outrosSistemas;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

// objeto para trafegar dados da requisição, dados da nota fiscal

public class NotaFiscalRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	@NotNull
	private Long idCompra;
	
	@NotNull
	private Long idCliente;
	
	// construtor com argumentos
	
	@JsonCreator
	public NotaFiscalRequest(@NotNull Long idCompra, @NotNull Long idCliente) {
		this.idCompra = idCompra;
		this.idCliente = idCliente;
	}
	
	// gatters

	public Long getIdCompra() {
		return idCompra;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	@Override
	public String toString() {
		return "NotaFiscalRequest [idCompra=" + idCompra + ", idCliente=" + idCliente + "]";
	}
	
	

}
