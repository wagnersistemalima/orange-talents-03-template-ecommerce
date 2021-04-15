package br.com.zupeacademy.wagner.mercadolivre.produto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

@RestController
@RequestMapping(value = "/produtos")
public class OpiniaoProdutoController {

	@PersistenceContext
	private EntityManager manager;

	// 2 end point / Usuario cliente logado adiciona opiniao ao produto

	@Transactional
	@PostMapping(value = "/{id}/opinioes")
	public ResponseEntity<?> insertOpiniao(@Valid @PathVariable("id") Long id,
			@Valid @RequestBody OpiniaoProdutoRequest request, @Valid @AuthenticationPrincipal Usuario cliente) {

		Produto produto = manager.find(Produto.class, id);                          //1
		OpiniaoProduto entity = request.toModel(produto, cliente);                     // 1
		manager.persist(entity);
		return ResponseEntity.ok().build();
	}

}
