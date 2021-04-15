package br.com.zupeacademy.wagner.mercadolivre.exceptions;

public class CaracteristicasDoProdutoIgualException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	// construtor 
	
	public CaracteristicasDoProdutoIgualException(String msg) {
		super(msg);
	}

}
