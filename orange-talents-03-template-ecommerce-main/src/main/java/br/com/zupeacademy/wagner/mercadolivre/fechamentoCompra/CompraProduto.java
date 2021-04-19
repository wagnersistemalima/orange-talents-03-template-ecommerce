package br.com.zupeacademy.wagner.mercadolivre.fechamentoCompra;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;


import br.com.zupeacademy.wagner.mercadolivre.produto.Produto;
import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

// entidade

@Entity
public class CompraProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Transient                                               // JPA vai ignorar o atributo no banco
	private boolean compraFinalizadaComSucesso = false;
	
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
	
	// associação com a transação / uma compra esta relacionada com varias transaçoes / guando atualizar a compra ,
	// mergei as transaçoes
	
	
	// MERGE – disparado toda vez que uma alteração é executada em uma entity. Essa alteração pode acontecer
	// ao final de uma transação com a qual uma managed Entity foi alterada, ou pelo comando entityManager
	
	@OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
	private Set<Transacao> transacoes = new HashSet<>();

	
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
	
	// metodo com a logica de adicionar a transação

	public void adicionaTransacao(@Valid RetornoGatewayPagamento request) {
		
		Transacao novaTransacao = request.toTransacao(this);   // transforma a request em entidade
		
		// não suportamos que duas transaçoes idTransação iguais sejam adicionada a compra
		
		
		Assert.isTrue(!this.transacoes.contains(novaTransacao), "Já existe uma transação igual a essa processada ");
		
		// para cada id da compra só pode ter um status com sucesso 
		
		Set<Transacao> transacoesConcluidaComSucesso = this.transacoes.stream()
				.filter(Transacao :: concluidaComSucesso)
				.collect(Collectors.toSet());
		
		// validação 
		
		Assert.isTrue(transacoesConcluidaComSucesso.isEmpty(), "Essa compra já foi concluida com sucesso");
		Assert.isTrue(transacoesConcluidaComSucesso.size() <= 1, "Falha no sistema!");
		
		// se passar das validaçoes adiciona a transação
		
		
		this.transacoes.add(request.toTransacao(this));
		this.compraFinalizadaComSucesso = true;
		
	}
	
	public boolean isCompraFinalizadaComSucesso() {
		return compraFinalizadaComSucesso;
	}
	
}
