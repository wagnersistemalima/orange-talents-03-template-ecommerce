package br.com.zupeacademy.wagner.mercadolivre.fechamentoCompra;


public interface RetornoGatewayPagamento {
	
	// retorna uma nova transação do gataway especifico
	
	Transacao toTransacao(CompraProduto compra);

}
