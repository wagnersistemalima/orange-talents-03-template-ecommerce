package br.com.zupeacademy.wagner.mercadolivre.usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controlador Rest, objeto que vai receber as requisiçoes, processar e responder ao cliente


@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {
	
	// objeto para transformar senha limpa em codigo passowordEncode
	
	@Autowired
	private BCryptPasswordEncoder passowordEncoder;
	
	@PersistenceContext
	private EntityManager manager;
	
	// 1º end point / insert / cadastrar usuario / resposta 200 ok
	
	@Transactional
	@PostMapping
	public ResponseEntity<UsuarioResponse> insert(@Valid @RequestBody UsuarioRequest request) {
		Usuario entity = new Usuario(request.getEmail(), passowordEncoder.encode(request.getSenha()));
		manager.persist(entity);
		return ResponseEntity.ok().body(new UsuarioResponse(entity));
		
	}

}
