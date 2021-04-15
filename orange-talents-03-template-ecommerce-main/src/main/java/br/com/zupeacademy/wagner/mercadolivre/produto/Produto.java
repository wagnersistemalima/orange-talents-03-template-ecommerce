package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import br.com.zupeacademy.wagner.mercadolivre.categoria.Categoria;
import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

// entidade

@Entity
@Table(name = "tb_produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	// atributos basicos

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Campo obrigatorio")
	@Column(unique = true)
	private String nome;

	@NotNull
	@Positive
	private BigDecimal valor;

	
	@NotNull
	private int quantidade;

	@NotBlank
	@Length(max = 1000)
	private String descricao;
	
	//associação para CaracteristicaProduto / um produto possui varias caracteristicas
	
	@OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
	 private Set<CaracteristicaProduto> caracteristicas = new HashSet<>();

	// associação para categoria e usuario
	
	// um produto esta relacionado com uma categoria / Categoria esta realcionado com varios produtos

	@NotNull
	@Valid
	@ManyToOne
	private Categoria categoria;
	
	// um produto esta relacionado com um usuario / Usuario esta relacionado com varios produtos

	@Valid
	@NotNull
	@ManyToOne
	private Usuario usuarioLogado;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant dataRegistro;
	
	// associação com ImagenProduto / um produto pode estar associado com varias imagens
	
	@OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE) //guando atualizar o produto atualiza junto as imagens
	private Set<ImagenProduto> imagens = new HashSet<>();
	
	
	// associação com as perguntas, vai ser ordenadas / um produto pode estar associado com varias perguntas
	
	@OneToMany(mappedBy = "produto")
	private Set<PerguntaSobreProduto> perguntas = new HashSet<>();
	
	// associação com as opinioes / um produto está associado a varias opinioes
	
	@OneToMany(mappedBy = "produto")
	private Set<OpiniaoProduto> opinioes = new HashSet<>();

	// construtor default

	@Deprecated
	public Produto() {

	}

	// construtor com argumentos

	public Produto(@NotBlank(message = "Campo obrigatorio") String nome, @NotNull @Positive BigDecimal valor,
			 @NotNull int quantidade, @NotBlank @Length(max = 1000) String descricao,
			@NotNull @Valid Categoria categoria, @NotNull @Valid Usuario usuarioLogado, @Valid List<CaracteristicaRequest> caracteristicas) {
		super();
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.categoria = categoria;
		this.usuarioLogado = usuarioLogado;
		this.caracteristicas.addAll(caracteristicas.stream()
				.map(caracteristica -> caracteristica.toModel(this)).collect(Collectors.toSet()));
		
		Assert.isTrue(this.caracteristicas.size() >= 3,
               "Todo produto precisa ter no mínimo 3 ou mais características");
    
	}

	// Getters

	public Long getId() {
		return id;
	}
	

	public Set<OpiniaoProduto> getOpinioes() {
		return opinioes;
	}

	public Set<PerguntaSobreProduto> getPerguntas() {
		return perguntas;
	}

	public Set<CaracteristicaProduto> getCaracteristicas() {
		return caracteristicas;
	}
	

	public Set<ImagenProduto> getImagens() {
		return imagens;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public Categoria getCategoria() {
		return categoria;
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


	public Instant getDataRegistro() {
		return dataRegistro;
	}

	public String getDescricao() {
		return descricao;
	}
	
	// HashCode & Equals comparando pelo nome

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Produto other = (Produto) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	
	// metodo auxiliar para sempre que for salvar um produto no banco, armezanar na
	// datacadastro o instante atual.
	
	@PrePersist
	public void prePersist() {
		dataRegistro = Instant.now();
	}
	
	// metodo para associar imagem ao produto

	public void associarImagens(Set<String> listaDeLinks) {
		Set<ImagenProduto> imagens = listaDeLinks.stream().map(link -> new ImagenProduto(link, this)).collect(Collectors.toSet());
		this.imagens.addAll(imagens); 
	}
	
	// metodo para abater estoque do produto

	public boolean abateDoestoque(@Positive int quantidadeRequest) {
		// validação
		
		if (this.quantidade >= 0 && this.quantidade >= quantidadeRequest) {
			this.quantidade -= quantidadeRequest;
			return true;
		}
		
		return false;
	}
	
	

}
