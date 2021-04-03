package br.com.zupeacademy.wagner.mercadolivre.categoria;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.zupeacademy.wagner.mercadolivre.validation.ExistsId;
import br.com.zupeacademy.wagner.mercadolivre.validation.UniqueValue;

//Objeto para trafegar dados entre o cliente e a aplicação

public class CategoriaRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	@NotBlank(message = "Campo obrigatorio")
	@UniqueValue(domainClass = Categoria.class, fieldName = "nome", message = "Você está tentando cadastrar uma categoria já existente")
	private String nome;
	
	@Positive
	@ExistsId(domainClass = Categoria.class, fieldName = "id", message = "Erro de validação, o id da categoria mae não existe")
	private Long idCategoriaMae;
	
	// construtor com argumento nome
	
	@JsonCreator
	public CategoriaRequest(@NotBlank(message = "Campo obrigatorio") String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public Long getIdCategoriaMae() {
		return idCategoriaMae;
	}
	
	// metodo com a logica para criar uma categoria sem associação com a categoria mae, e tambem com associação

	public Categoria toModel(EntityManager manager) {
		Categoria categoria;
		Categoria categoriaMae;
		
		if (this.idCategoriaMae != null) {
			categoriaMae = manager.find(Categoria.class, idCategoriaMae);   // construtor personalizado
			categoria = new Categoria(nome, categoriaMae);
			
		}
		else {
			categoria = new Categoria(nome);         // construtor com apenas nome
		}
		return categoria;
	}

}
