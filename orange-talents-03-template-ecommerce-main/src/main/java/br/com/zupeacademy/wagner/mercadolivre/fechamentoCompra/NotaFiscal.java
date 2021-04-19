package br.com.zupeacademy.wagner.mercadolivre.fechamentoCompra;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// classe contendo a logica para processar uma nota fiscal

@Service
public class NotaFiscal implements ServicoExterno{
	
	@Override
	public void processa(CompraProduto compra) {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> notaFiscalRequest = Map.of("idCompra", compra.getId(),
				"idCliente", compra.getCliente().getId());
		
		restTemplate.postForEntity("http://localhost:8080/notas-fiscais", notaFiscalRequest, String.class);
	}

}
