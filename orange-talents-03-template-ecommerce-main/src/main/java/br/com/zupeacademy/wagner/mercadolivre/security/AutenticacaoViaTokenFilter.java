package br.com.zupeacademy.wagner.mercadolivre.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;
import br.com.zupeacademy.wagner.mercadolivre.usuario.UsuarioRepository;

// classe contendo a logica de pegar o token no cabeçalho da requisição e verificar se ta ok, e autenticar no Spring

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	AutenticacaoService autenticacaoService;

	// atributos basico

	private TokenService tokenService;
	private UsuarioRepository repository;

	// construtor default

	@Deprecated
	public AutenticacaoViaTokenFilter() {
	}

	// construtor com argumentos

	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}

	// metodo da interface

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = recuperarToken(request);
		boolean valido = tokenService.isTokenValido(token);
		if (valido) {
			autenticarCliente(token);
		}
		filterChain.doFilter(request, response);
	}

	private void autenticarCliente(String token) {
		Long idUsuario = tokenService.getIdUsuario(token);
		Usuario usuario = repository.findById(idUsuario).get();
		UserDetails userDetails = autenticacaoService.loadUserByUsername("moderador@email.com");
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

	}

	// metodo privado para recuperar token

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}

}
