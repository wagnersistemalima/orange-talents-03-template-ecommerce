package br.com.zupeacademy.wagner.mercadolivre.autenticacao;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupeacademy.wagner.mercadolivre.security.TokenService;

// Controlador Rest, responsavel por receber as requisiçoes de autenticação, processar e responder

@RestController
@RequestMapping(value = "/auth")
public class AutenticacaoController {

	// dependencia para a classe AuthenticationManager

	@Autowired
	private AuthenticationManager authManager;

	// dependencia para a classe tokenService no pacote de segurança

	@Autowired
	private TokenService tokenService;

	// 1 end point / receber dados via autenticação / post

	@PostMapping
	public ResponseEntity<TokenRequest> autenticar(@Valid @RequestBody AutenticacaoRequest request) {

		UsernamePasswordAuthenticationToken dadosLogin = request.converter();
		
		try {
			Authentication authenticatiom = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authenticatiom);
			return ResponseEntity.ok().body(new TokenRequest(token, "Bearer"));
		}
		catch (AuthenticationCredentialsNotFoundException e) {
			return ResponseEntity.badRequest().build();
		}


	}
}
