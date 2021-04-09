package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {
	
	// dependencia para o enviador de arquivos
	
	@Autowired
	EnvioDeArquivos envioDeArquivos;
	
	// objeto para acessar o banco
	
	@PersistenceContext
	private EntityManager manager;
	
	// validação customizada
	
	@InitBinder(value = "ProdutoRequest")                                                 // para configurar o controoler com coisas adicionais / validação
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
	
	// 2º end point / adicionar imagem do produto, com usuario logado / resposta 200 ok
	
	@Transactional
	@PostMapping(value = "/{id}/imagens")
	public ResponseEntity<?> insertImagen(@PathVariable Long id, @Valid ImagenProdutoRequest request) {
		
		// 1 passo enviar as imagens para o local de armazenamento fake, e receber os links das imagens
		
		Set<String> listaDeLinks = envioDeArquivos.envia(request.getImagens());
		
		// 2 passo associar os links com produtos
		
		Produto entity = manager.find(Produto.class, id);
		entity.associarImagens(listaDeLinks);
		manager.merge(entity);
		
		return ResponseEntity.ok().build();
	}
	
	// 3 end point / Usuario cliente logado adiciona opiniao ao produto
	
	@Transactional
	@PostMapping(value = "/{id}/opinioes")
	public ResponseEntity<?> insertOpiniao(@PathVariable Long id,@Valid @RequestBody OpiniaoProdutoRequest request, @Valid @AuthenticationPrincipal Usuario cliente) {
		
		Produto produto = manager.find(Produto.class, id);
		OpiniaoProduto entity = request.toModel(produto, cliente);
		manager.persist(entity);
		return ResponseEntity.ok().build();
	}

}
