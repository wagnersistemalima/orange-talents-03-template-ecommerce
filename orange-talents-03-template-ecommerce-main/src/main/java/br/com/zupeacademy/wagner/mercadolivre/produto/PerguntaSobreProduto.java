package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

// entidade

@Entity
public class PerguntaSobreProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	// atributos basicos

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	private String titulo;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant dataRegistro;

	// associação para o produto / varias perguntas esta associada a um produto

	@Valid
	@NotNull
	@ManyToOne
	private Produto produto;

	// associação para o usuario cliente / varias perguntas pertence a um usuario

	@Valid
	@NotNull
	@ManyToOne
	private Usuario cliente;

	// construtor default

	@Deprecated
	public PerguntaSobreProduto() {

	}

	// construtor com argumentos

	public PerguntaSobreProduto(@NotBlank String titulo, @Valid @NotNull Produto produto,
			@Valid @NotNull Usuario cliente) {
		this.titulo = titulo;
		this.produto = produto;
		this.cliente = cliente;
	}

	// getters

	public String getTitulo() {
		return titulo;
	}

	public Produto getProduto() {
		return produto;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public Instant getDataRegistro() {
		return dataRegistro;
	}

	public long getId() {
		return id;
	}

	// hashCode e equals comparando pelo titulo

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		PerguntaSobreProduto other = (PerguntaSobreProduto) obj;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}


	// metodo auxiliar para sempre que for salvar uma pergunta sobre o produto no banco, armezanar na
	// dataRegistro o instante atual.

	@PrePersist
	public void prePersist() {
		dataRegistro = Instant.now();
	}

	

}
