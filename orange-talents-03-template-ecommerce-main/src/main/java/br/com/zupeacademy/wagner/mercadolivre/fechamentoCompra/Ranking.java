package br.com.zupeacademy.wagner.mercadolivre.fechamentoCompra;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Ranking implements ServicoExterno{
	
	@Override
	public void processa(CompraProduto compra) {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> rankingRequest = Map.of("idCompra", compra.getId(),
				"idVendedor", compra.getProduto().getUsuarioLogado().getId());
		
		restTemplate.postForEntity("http://localhost:8080/ranking", rankingRequest, String.class);
	}

}
