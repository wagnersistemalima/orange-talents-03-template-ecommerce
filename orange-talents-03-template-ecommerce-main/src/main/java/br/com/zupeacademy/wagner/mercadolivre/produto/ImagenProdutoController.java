package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupeacademy.wagner.mercadolivre.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/produtos")
public class ImagenProdutoController {
	
	// objeto para acessar o banco
	
	@PersistenceContext
	private EntityManager manager;
	
	// dependencia para o enviador de arquivos
	
	@Autowired
	EnvioDeArquivos envioDeArquivos;                                                      //1

	// 1º end point / adicionar imagem do produto, com usuario logado / resposta 200 ok

	@Transactional
	@PostMapping(value = "/{id}/imagens")
	public ResponseEntity<?> insertImagen(@PathVariable("id") Long id, @Valid ImagenProdutoRequest request) {

		// 1 passo enviar as imagens para o local de armazenamento fake, e receber os
		// links das imagens

		Set<String> listaDeLinks = envioDeArquivos.envia(request.getImagens());

		// 2 passo associar os links com produtos

		Produto entity = manager.find(Produto.class, id);                                 // 1

		if (entity == null) {                                                              // 1
			throw new ResourceNotFoundException("O id do produto não foi encontrado");
		}
		entity.associarImagens(listaDeLinks);
		manager.merge(entity);

		return ResponseEntity.ok().build();
	}

}
