package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

// entidade

@Entity
public class CompraProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Positive
	private int quantidade;
	
	@Enumerated                  // para representar uma coluna específica em um banco de dados
	@NotNull
	private GatewayPagamento gateway;
	
	// associação com o produto / varias compras está relacionada com um produto
	
	@NotNull
	@ManyToOne
	@Valid
	private Produto produto;
	
	// associação com o usuario cliente / varias compras está relacionada com um cliente
	
	@NotNull
	@ManyToOne
	@Valid
	private Usuario cliente;
	
	// construtor default
	
	@Deprecated
	public CompraProduto() {
		
	}
	
	// construtor com argumentos

	public CompraProduto(@NotNull @Positive int quantidade, @NotNull @Valid Produto produto,
			@NotNull @Valid Usuario cliente,@NotNull GatewayPagamento gateway) {
		this.quantidade = quantidade;
		this.produto = produto;
		this.cliente = cliente;
		this.gateway = gateway;
	}
	
	// getters

	public Long getId() {
		return id;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public Produto getProduto() {
		return produto;
	}
	
	

	public GatewayPagamento getGateway() {
		return gateway;
	}

	public Usuario getCliente() {
		return cliente;
	}
	
	// hashCode & equals comparando pelo cliente

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		CompraProduto other = (CompraProduto) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		return true;
	}


}
