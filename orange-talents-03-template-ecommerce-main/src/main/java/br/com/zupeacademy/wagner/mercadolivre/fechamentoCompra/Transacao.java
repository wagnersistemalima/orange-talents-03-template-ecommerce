package br.com.zupeacademy.wagner.mercadolivre.fechamentoCompra;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Transacao implements Serializable {

	private static final long serialVersionUID = 1L;

	// atributos basico
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusTransacaoEnum status;

	@NotBlank
	private String idTransacaoGateway;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant dataRegistro;
	
	// associação com a Compra, varias transaçoes pode esta relacionada com uma compra
	
	@NotNull
	@Valid
	@ManyToOne
	private CompraProduto compra;
	
	// construtor default
	
	@Deprecated
	public Transacao() {
		
	}

	// construtor

	public Transacao(@NotNull StatusTransacaoEnum status, @NotBlank String idTransacaoGateway,@NotNull @Valid CompraProduto compra) {
		this.status = status;
		this.idTransacaoGateway = idTransacaoGateway;
		this.compra = compra;
	}

	// metodo auxiliar para sempre que for salvar uma transação, armezanar na
	// dataRegistro o instante atual.

	@PrePersist
	public void prePersist() {
		dataRegistro = Instant.now();
	}
	
	// getters

	public StatusTransacaoEnum getStatus() {
		return status;
	}

	public String getIdTransacao() {
		return idTransacaoGateway;
	}

	public Instant getDataRegistro() {
		return dataRegistro;
	}
	
	// HashCode & equals comparando pelo id

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTransacaoGateway == null) ? 0 : idTransacaoGateway.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transacao other = (Transacao) obj;
		if (idTransacaoGateway == null) {
			if (other.idTransacaoGateway != null)
				return false;
		} else if (!idTransacaoGateway.equals(other.idTransacaoGateway))
			return false;
		return true;
	}
	
	// metodo para verificar o status da transação
	
	public boolean concluidaComSucesso() {
		return this.status.equals(StatusTransacaoEnum.sucesso);
	}

}
