package br.com.zupeacademy.wagner.mercadolivre.produto;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupeacademy.wagner.mercadolivre.exceptions.ResourceNotFoundException;
import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {
	
	// objeto para acessar o banco
	
	@PersistenceContext
	private EntityManager manager;

	// 1º end point, insert produto, resposta 200 ok
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> insertProduto(@Valid @RequestBody ProdutoRequest request, @Valid @AuthenticationPrincipal Usuario usuarioLogado) {
		Produto entity = request.toModel(manager, usuarioLogado);          //1
		manager.persist(entity);
		return ResponseEntity.ok().build();
	}
	
	// 2º end point / mostrar detalhes do produto
	
	@Transactional
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") @Valid @NotNull Long id) {
		Produto entity = manager.find(Produto.class, id);
		
		
		if (entity == null) {                                                             //1
			throw new ResourceNotFoundException("O id do produto não foi encontrado");
		}
		
		return ResponseEntity.ok().body(new ProdutoDetalheResponse(entity));
	}
	
	

}
