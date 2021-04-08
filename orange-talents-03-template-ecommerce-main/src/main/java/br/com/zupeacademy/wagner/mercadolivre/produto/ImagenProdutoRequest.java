package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.zupeacademy.wagner.mercadolivre.exceptions.ResourceNull;

// Objeto para trafegar dados do cliente para a aplicação

public class ImagenProdutoRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	// atributos basicos

	// -> MultipartFile para fazer upload de arquivos / imagens

	@Size(min = 1)
	@NotNull(message = "O campo nao pode ser nulo")
	private List<MultipartFile> imagens = new ArrayList<>();

	// construtor com argumentos

	@JsonCreator
	public ImagenProdutoRequest(@Size(min = 1) @NotNull List<MultipartFile> imagens) {
		if (imagens == null) {
			throw new ResourceNull("O campo não pode estar nulo");
		}

		this.imagens = imagens;

	}

	public List<MultipartFile> getImagens() {
		return imagens;
	}

}
