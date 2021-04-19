package br.com.zupeacademy.wagner.mercadolivre.produto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupeacademy.wagner.mercadolivre.outrosSistemas.Emails;
import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

@RestController
@RequestMapping(value = "/produtos")
public class PerguntaSobreProdutoController {

	// dependencia para a classe contendo a logica para enviar email

	@Autowired
	private Emails emails;                                                              // 1

	// objeto para acessar o banco

	@PersistenceContext
	private EntityManager manager;

	// 3º end point / Usuario logado adiciona pergunta sobre o produto / insert /
	// resposta 200

	@Transactional
	@PostMapping(value = "/{id}/perguntas")
	public ResponseEntity<?> insertPergunta(@PathVariable("id") Long id,
			@RequestBody @Valid PerguntaSobreProdutoRequest request, @Valid @AuthenticationPrincipal Usuario cliente) {
		Produto produto = manager.find(Produto.class, id);                       //1

		PerguntaSobreProduto entity = request.toModel(produto, cliente);                     // 1
		manager.persist(entity);

		// logica para enviar a pergunta por email

		emails.novaPergunta(entity);

		return ResponseEntity.ok().build();
	}

}
