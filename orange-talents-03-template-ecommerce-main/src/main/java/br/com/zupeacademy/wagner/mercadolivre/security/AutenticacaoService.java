package br.com.zupeacademy.wagner.mercadolivre.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;
import br.com.zupeacademy.wagner.mercadolivre.usuario.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

	// dependencia para a camada de acesso a dados

	@Autowired
	private UsuarioRepository repository;

	// metodo da interface

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = repository.findByEmail(username);
		if (usuario.isPresent()) {
			return usuario.get();
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados invalidos");
	}
	
	// metodo 2
	
	//@Override
	//public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	//	Usuario usuario = repository.findByEmail(username);
	//	if (usuario == null) {
	//		throw new UsernameNotFoundException("e-mail não encontrado");
	//	}
	//	return usuario;
	//}

	// 1 forma de buscar usuario autenticado
	public Usuario authenticated() {
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuario;
	}

}
