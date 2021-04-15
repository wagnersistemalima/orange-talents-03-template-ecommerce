package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

// objeto para trafegar dados de resposta do detalhe do produto,  da api para o cliente

public class ProdutoDetalheResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos 
	
	private Long id;
	
	private String nome;
	
	private BigDecimal valor;
	
	private int quantidade;
	
	private String descricao;
	
	private String emailvendedor;
	
	private int totalDeNotas; 
	
	private double mediaNotas;
	
	// vai aceitar uma lista de objeto de resposta da caracteristica do produto
	
	private Set<CaracteristicaResponse> caracteristicas = new HashSet<>();
	
	// vai aceitar uma lista de objeto de resposta de imagem do produto
	
	private Set<ImagenProdutoResponse> linkImagens = new HashSet<>();
	
	// vai aceitar uma lista de objeto de perguntas sobre o produto
	
	private Set<PerguntaSobreProdutoResponse> perguntas = new HashSet<>();
	
	// vai aceitar uma lista de objeto  de opinioes sobre o produto
	
	private Set<OpiniaoProdutoResponse> opinioes = new HashSet<>();
	
	
	// construtor personalizado recebendo uma entidade produto
	
	@JsonCreator
	public ProdutoDetalheResponse(@NotNull @Valid Produto entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.valor = entity.getValor();
		this.descricao = entity.getDescricao();
		this.quantidade = entity.getQuantidade();
		this.emailvendedor = entity.getUsuarioLogado().getEmail();
		
		// caracteristicas
		
		this.caracteristicas = entity.getCaracteristicas().stream()
				.map(caracteristica -> new CaracteristicaResponse(caracteristica)).collect(Collectors.toSet());
		
		// imagens
		
		this.linkImagens = entity.getImagens().stream()
				.map(imagem -> new ImagenProdutoResponse(imagem)).collect(Collectors.toSet());
		
		// perguntas
		
		this.perguntas = entity.getPerguntas().stream()
				.map(pergunta -> new PerguntaSobreProdutoResponse(pergunta)).collect(Collectors.toSet());
		
		// opinioes
		
		this.opinioes = entity.getOpinioes().stream()
				.map(opiniao -> new OpiniaoProdutoResponse(opiniao)).collect(Collectors.toSet());
		
		// total de notas = tamanho da lista
		
		this.totalDeNotas = entity.getOpinioes().size();
		
		// reduce(o valor inicial, acumulador, uma função para combinar o resultado)
		
		Integer somaDasNotas = entity.getOpinioes().stream().map(opiniao -> opiniao.getNota()).reduce(0, Integer::sum);
		
		// media da nota = soma das notas / totalDeNotas		
		
		this.mediaNotas = (somaDasNotas == 0) ? 0 : (double) (somaDasNotas / this.totalDeNotas);
				
	}
	
	
	// getters

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public String getVendedor() {
		return emailvendedor;
	}

	public Set<CaracteristicaResponse> getCaracteristicas() {
		return caracteristicas;
	}

	public Set<ImagenProdutoResponse> getLinkImagens() {
		return linkImagens;
	}

	public String getDescricao() {
		return descricao;
	}


	public Set<PerguntaSobreProdutoResponse> getPerguntas() {
		return perguntas;
	}


	public Set<OpiniaoProdutoResponse> getOpinioes() {
		return opinioes;
	}


	public int getTotalDeNotas() {
		return totalDeNotas;
	}


	public double getMediaNotas() {
		return mediaNotas;
	}


}
