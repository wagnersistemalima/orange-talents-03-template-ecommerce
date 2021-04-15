package br.com.zupeacademy.wagner.mercadolivre.produto;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupeacademy.wagner.mercadolivre.compartilhado.Emails;
import br.com.zupeacademy.wagner.mercadolivre.exceptions.ResourceNotFoundException;
import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

// Classe responsavel por receber as requisiçoes das compras dos produtos, processar e responder ao cliente

@RestController
@RequestMapping(value = "/produtos")
public class CompraController {

	// dependencia para a classe contendo a logica para enviar email

	@Autowired
	private Emails emails;                                                           // 1

	// objeto responsavel por acessar o banco de dados

	@Autowired
	private EntityManager manager;

	// 1º end point / o cliente efetua uma compra / insert dados da compra

	@Transactional
	@PostMapping(value = "/{id}/compras")
	public ResponseEntity<?> insertCompra(@PathVariable("id") Long id, @Valid @RequestBody CompraProdutoRequest request,
			@Valid @AuthenticationPrincipal Usuario cliente) throws BindException {

		// produto a ser comprado

		Produto produto = manager.find(Produto.class, id);                                       // 1

		// validação
		if (produto == null) {                                                                    // 1
			throw new ResourceNotFoundException("O id do produto não foi encontrado");
		}

		// se a validação estiver correta true abate do estoque

		if (produto.abateDoestoque(request.getQuantidade())) {                                     // 1

			// logica da compra do produto

			CompraProduto entity = new CompraProduto(request.getQuantidade(), produto, cliente, request.getGateway()); // 1
			manager.persist(entity);

			// envia o email ao dono do produto, avisando o interesse do cliente em comprar
			// o produto

			emails.novaCompra(entity);

			GatewayPagamento gateweyPagamento = request.getGateway();                                               // 1

			// se o pagamento escolhido pelo cliente for pag-seguro

			if (gateweyPagamento.equals(GatewayPagamento.PAG_SEGURO)) { 
				String url_Pag_Seguro = UriComponentsBuilder.fromPath("/retorno-pagseguro/{id}")
						.buildAndExpand(entity.getId()).toString();
				return ResponseEntity.ok("pagseguro.com/" + entity.getId() + "?redirectUrl=" + url_Pag_Seguro);
			}

			// se o pagamento escolhido pelo cliente for pay-pal

			else if (gateweyPagamento.equals(GatewayPagamento.PAY_PAL)) {                                            // 1
				String url_pay_pal = UriComponentsBuilder.fromPath("/retorno-paypal/{id}")
						.buildAndExpand(entity.getId()).toString();
				return ResponseEntity.ok("paypal.com/" + entity.getId() + "?redirectUrl=" + url_pay_pal);
			}

		}

		// se a erro na validação do estoque, lança exception do propio framework

		BindException problemaComEstoque = new BindException(request, "compraRequest");
		problemaComEstoque.reject(null, "Não foi possivel realizar a compra, devido aõ estoque");
		throw problemaComEstoque;
	}

}
