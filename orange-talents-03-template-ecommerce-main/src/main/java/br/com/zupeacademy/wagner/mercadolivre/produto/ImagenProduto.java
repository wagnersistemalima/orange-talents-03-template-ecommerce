package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

@Entity
public class ImagenProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basico
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@URL
	@NotBlank
	private String link;
	
	// associação para o produto / uma imagem esta associada a um produto / o produto esta associado a varias imagens
	
	@Valid
	@NotNull
	@ManyToOne
	private Produto produto;
	
	// construtor default
	
	@Deprecated
	public ImagenProduto() {
		
	}
	
	// construtor com argumentos 

	public ImagenProduto(@URL @NotBlank String link, @Valid @NotNull Produto produto) {
		this.link = link;
		this.produto = produto;
	}
	
	// getters

	public Long getId() {
		return id;
	}

	public String getLink() {
		return link;
	}

	public Produto getProduto() {
		return produto;
	}

	@Override
	public String toString() {
		return "ImagenProduto [id=" + id + ", link=" + link + "]";
	}
	
	// hashCode & equals comparando pelo link e por produto

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		ImagenProduto other = (ImagenProduto) obj;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}
	
	

}
