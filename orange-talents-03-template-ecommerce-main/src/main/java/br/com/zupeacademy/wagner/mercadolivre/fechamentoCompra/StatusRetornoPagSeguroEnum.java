package br.com.zupeacademy.wagner.mercadolivre.fechamentoCompra;

public enum StatusRetornoPagSeguroEnum {
	
	// constantes
	
	SUCESSO,
	ERRO;
	
	// construtor
	
	@Deprecated
	StatusRetornoPagSeguroEnum() {
		
	}
	
	// metodo para converter o status de pagamento da requisição com o status da transação

	public StatusTransacaoEnum normaliza() {
		if (this.equals(SUCESSO)) {
			return StatusTransacaoEnum.sucesso;
		}
		return StatusTransacaoEnum.erro;
	}


}
