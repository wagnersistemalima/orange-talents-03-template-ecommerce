package br.com.zupeacademy.wagner.mercadolivre.categoria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controlador Rest, objeto responsavel por receber as requisições do cliente, processar e responder

@RestController
@RequestMapping(value = "catogorias")
public class CategoriaController {
	
	// objeto para acessar o banco
	
	@PersistenceContext
	private EntityManager manager;
	
	// 1 end point / insert / resposta 200 ok
	
	@Transactional
	@PostMapping
	public ResponseEntity<CategoriaRequest> insert(@Valid @RequestBody CategoriaRequest request) {
		Categoria entity = request.toModel(manager);
		manager.persist(entity);
		return ResponseEntity.ok().build();
	}

}
