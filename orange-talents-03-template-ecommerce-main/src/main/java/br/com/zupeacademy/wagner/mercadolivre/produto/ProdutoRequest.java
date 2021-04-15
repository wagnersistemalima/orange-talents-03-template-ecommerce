package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.zupeacademy.wagner.mercadolivre.categoria.Categoria;
import br.com.zupeacademy.wagner.mercadolivre.exceptions.CaracteristicasDoProdutoIgualException;
import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;
import br.com.zupeacademy.wagner.mercadolivre.validation.ExistsId;
import br.com.zupeacademy.wagner.mercadolivre.validation.UniqueValue;

// objeto para trafegar dados do cliente para aplicação

public class ProdutoRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	@NotBlank(message = "Campo obrigatorio")
	@UniqueValue(domainClass = Produto.class, fieldName = "nome")
	private String nome;
	
	@NotNull
	@Positive
	private BigDecimal valor;
	
	@Positive
	@NotNull
	private int quantidade;
	
	@NotBlank
	@Length(max = 1000)
	private String descricao;
	
	@NotNull
	@ExistsId(domainClass = Categoria.class, fieldName = "id")
	private Long idCategoria;
	
	@Size(min = 3, message = "minimo treis caracteristicas")
	@Valid
	private List<CaracteristicaRequest> caracteristicas = new ArrayList<>();
	
	
	// construtor com argumentos
	
	@JsonCreator
	public ProdutoRequest(@NotBlank(message = "Campo obrigatorio") String nome, @NotNull @Positive BigDecimal valor,
			@Positive @NotNull int quantidade, @NotBlank @Length(max = 1000) String descricao,
			@NotNull Long idCategoria, @Size(min = 3) @Valid List<CaracteristicaRequest> caracteristicas) {
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.idCategoria = idCategoria;		
		this.caracteristicas.addAll(caracteristicas);
		
		if (temCaracteristicasIguais() == true) {
			throw new CaracteristicasDoProdutoIgualException("Você está tentando cadastrar um produto com  caracteristicas iguais");
		}
	}
	
	// getters

	public String getNome() {
		return nome;
	}

	

	public BigDecimal getValor() {
		return valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}
	

	public List<CaracteristicaRequest> getCaracteristicas() {
		return caracteristicas;
	}

	// metodo para converter request em entidade

	public Produto toModel(EntityManager manager,@AuthenticationPrincipal @Valid Usuario usuarioLogado) {
		Categoria categoria = manager.find(Categoria.class, idCategoria);
		return new Produto(nome, valor, quantidade, descricao, categoria, usuarioLogado, caracteristicas);
	}

	
	// metodo para verificar um estado interno da requisição

	public boolean temCaracteristicasIguais() {
		HashSet<String> nomesIguais = new HashSet<>();          // lista de string, não aceira repetição
		
		for (CaracteristicaRequest caracteristica: this.caracteristicas ) {
			if (!nomesIguais.add(caracteristica.getNome())) {          // se nao adicionar = true, tem nomes iguais
				
				return true;
			}
		}
		
		return false;                                                  // se adicionar = false, nao tem nomes iguais
	}

	@Override
	public String toString() {
		return "ProdutoRequest [nome=" + nome + ", valor=" + valor + ", quantidade=" + quantidade + ", descricao="
				+ descricao + ", idCategoria=" + idCategoria + ", caracteristicas=" + caracteristicas + "]";
	}
	
}
