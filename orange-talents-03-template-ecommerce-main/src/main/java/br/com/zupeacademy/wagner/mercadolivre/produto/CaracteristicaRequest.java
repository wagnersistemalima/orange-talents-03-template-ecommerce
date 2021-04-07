package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

// objeto para receber as caracteristicas do produtoRequest

public class CaracteristicaRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	// atributos basicos
	
	@Valid
	@NotBlank
	private String nome;
	
	@Valid
	@NotBlank
	private String descricao;
	
	// construtor com argumento

	public CaracteristicaRequest(@Valid @NotBlank String nome, @Valid @NotBlank String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}


	public String getDescricao() {
		return descricao;
	}
	
	// metodo para converter a request em entidade

	public CaracteristicaProduto toModel(Produto produto) {
		
		return new CaracteristicaProduto(nome, descricao, produto);
	}

	@Override
	public String toString() {
		return "CaracteristicaRequest [nome=" + nome + ", descricao=" + descricao + "]";
	}
	

}
