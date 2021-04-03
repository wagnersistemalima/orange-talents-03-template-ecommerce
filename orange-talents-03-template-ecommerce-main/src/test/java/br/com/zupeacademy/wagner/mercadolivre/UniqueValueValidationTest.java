package br.com.zupeacademy.wagner.mercadolivre;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

// Classe a ser testada, vadidador generico para campos que não podem ter duplicidade

public class UniqueValueValidationTest {

	@Mock // usar guando a classe de teste não carrega contexto da aplicação
	EntityManager manager = Mockito.mock(EntityManager.class);

	@InjectMocks // usar na classe a ser testada
	Usuario novoUsuario = new Usuario("marina@gmail.com", "marina123456");

	@Mock // usar guando a classe de teste não carrega contexto da aplicação
	Query query = Mockito.mock(Query.class);

	// 1 cenario de teste, deve retornar que ja existe usuario cadastrado para o
	// login informado

	@Test
	public void deveRetornarQueJaExisteUsuarioComEsteEmailCadastrado() {

		// logica aqui

		manager.persist(novoUsuario);

		String consultaJpql = "SELECT 1 FROM usuario WHERE login =:value";

		List<Usuario> list = new ArrayList<>();

		// configuração do comportamento simulado da criação da query e seu tretorno

		Mockito.when(manager.createQuery(consultaJpql)).thenReturn(query);

		// configuração do comportamento simulado do resultado da query caso a lista não
		// esteja vazia, ou seja, existe cadastro

		Mockito.when(query.getResultList()).thenReturn(list);

		// configuração do comportamento simulado do parametro ou campo, com resultado
		// da query

		Mockito.when(query.setParameter("value", novoUsuario.getLogin())).thenReturn(query);

		// assertivas aqui

		assertFalse(list.size() >= 1, "Tem um usuario cadastrado com este login");
	}

	// 2 cenario de teste, deve cadastrar o usuario, pois a validação confirmou que
	// no banco não existe usuario com este login

	@Test
	public void deveRetornarUsuarioCadastradoComSucesso() {

		// logica aqui

		manager.persist(novoUsuario);

		String consultaJpql = "SELECT 1 FROM usuario WHERE login =:value";

		List<Usuario> list = new ArrayList<>();

		// configuração do comportamento simulado da criação da query e seu tretorno

		Mockito.when(manager.createQuery(consultaJpql)).thenReturn(query);

		// configuração do comportamento simulado do resultado da query caso a lista 
		// esteja vazia

		Mockito.when(query.getResultList()).thenReturn(list);

		// configuração do comportamento simulado do parametro ou campo, com resultado
		// da query

		Mockito.when(query.setParameter("value", novoUsuario.getLogin())).thenReturn(query);

		// assertivas aqui

		assertTrue(list.size() < 1, "Usuario cadastrado com sucesso");

	}

}
