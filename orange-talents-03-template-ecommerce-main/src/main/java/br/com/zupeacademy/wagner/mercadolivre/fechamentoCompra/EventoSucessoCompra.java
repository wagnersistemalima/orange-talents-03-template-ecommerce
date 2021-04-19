package br.com.zupeacademy.wagner.mercadolivre.fechamentoCompra;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.zupeacademy.wagner.mercadolivre.outrosSistemas.Emails;



@Service
public class EventoSucessoCompra {

	// dependencia para emails

	@Autowired
	private Emails emails;

	// dependencia para nota fiscal

	@Autowired
	private NotaFiscal notaFiscal;

	// dependencia para Rankin

	@Autowired
	private Ranking ranking;
	
	// metodo para retornar os eventos 

	public ResponseEntity<?> retornoEventos(@NotNull @Valid CompraProduto compra) {
		if (compra.isCompraFinalizadaComSucesso()) {                                            // 1
			
			Assert.isTrue(compra.isCompraFinalizadaComSucesso(), "Erro na transação");

			// comunicar com o setor de nota fiscal

			notaFiscal.processa(compra);

			// comunicar com o sistema de ranking dos vendedores.

			ranking.processa(compra);

			// enviar email para quem comprou

			emails.compraFinalizadaComSucesso(compra);

		}
		return ResponseEntity.ok().build();
	}

}
