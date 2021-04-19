package br.com.zupeacademy.wagner.mercadolivre.fechamentoCompra;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupeacademy.wagner.mercadolivre.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/produtos")
public class CompraProdutoFechamentoController {

	@Autowired
	EventoSucessoCompra eventoSucessoCompra;                                              //1

	// objeto para acessar o banco

	@PersistenceContext
	private EntityManager manager;

	// 1º end point / insert fechamento da compra pagseguro / dados form url encode

	@Transactional
	@PostMapping(value = "/retorno-pagseguro/{id}") // uri criada no momento da compra
	public ResponseEntity<?> processamentoPagSeguro(@Valid RetornoPagSeguroRequest request,   //1
			@PathVariable("id") Long id) {
		
		return processa(id, request);

	}

	// 2º end point / insert fechamento da compra payPal / receber dados form uri
	// encode

	@Transactional
	@PostMapping(value = "/retorno-paypal/{id}") // uri criada no momento da compra
	public ResponseEntity<?> processamentoPayPal(@Valid RetornoPayPalRequest request, @PathVariable("id") Long id) {
		// 1
		return processa(id, request);
	}

	// metodo contendo a logica para processar um pagamento

	public ResponseEntity<?> processa(Long id, RetornoGatewayPagamento retornoGatewayPagamento) { // 1
		CompraProduto compra = manager.find(CompraProduto.class, id); // 1

		if (compra == null) { // 1
			throw new ResourceNotFoundException("O id da compra não foi encontrado");
		}

		compra.adicionaTransacao(retornoGatewayPagamento);

		manager.merge(compra);

		return eventoSucessoCompra.retornoEventos(compra);

	}

}
