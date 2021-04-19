package br.com.zupeacademy.wagner.mercadolivre.outrosSistemas;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

public class RankingRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	// atributos basicos

	@NotNull
	private Long idCompra;

	@NotNull
	private Long idVendedor;
	
	// construtor com argumentos
	
	@JsonCreator
	public RankingRequest(@NotNull Long idCompra, @NotNull Long idVendedor) {
		this.idCompra = idCompra;
		this.idVendedor = idVendedor;
	}

	public Long getIdCompra() {
		return idCompra;
	}

	public Long getIdVendedor() {
		return idVendedor;
	}

	@Override
	public String toString() {
		return "RankingRequest [idCompra=" + idCompra + ", idVendedor=" + idVendedor + "]";
	}
	
	

}
