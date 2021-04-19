package br.com.zupeacademy.wagner.mercadolivre.outrosSistemas;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zupeacademy.wagner.mercadolivre.usuario.Usuario;

@RestController
public class OutrosSistemasExternoController {
	
	// endPoint para receber dados da compra e do cliente para gerar a nota fiscal
	
		@PostMapping(value = "/notas-fiscais") 
		public void inserNota(@RequestBody NotaFiscalRequest notaFiscalRequest,@AuthenticationPrincipal Usuario cliente) throws InterruptedException {
			System.out.println("Criando nota para  " + notaFiscalRequest);
			Thread.sleep(150);
		}
		
		// end point para receber dados da compra e do vendedor
		
		@PostMapping(value = "/ranking")
		public void insertRamking(@RequestBody RankingRequest rankingRequest, @AuthenticationPrincipal Usuario vendedor) {
			System.out.println("Criando nota para " + rankingRequest);
		}

}
