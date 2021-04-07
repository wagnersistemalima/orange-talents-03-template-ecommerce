package br.com.zupeacademy.wagner.mercadolivre.produto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {
	
	// objeto para acessar o banco
	
	@PersistenceContext
	private EntityManager manager;
	
	// validação customizada
	
	@InitBinder                                                 // para configurar o controoler com coisas adicionais / validação
	public void init(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new ProibeCaracteristicaComNomeIqualValidation());
	}
	

	// 1º end point, insert, resposta 200 ok
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> insert(@Valid @RequestBody ProdutoRequest request, @Valid @AuthenticationPrincipal Usuario usuarioLogado) {
		Produto entity = request.toModel(manager, usuarioLogado);
		manager.persist(entity);
		return ResponseEntity.ok().build();
	}

}
