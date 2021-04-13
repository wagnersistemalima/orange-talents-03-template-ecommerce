package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;

// objeto para trafegar dados de resposta entre a aplicação e o cliente, dados das perguntas

public class PerguntaSobreProdutoResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos basicos
	
	private String titulo;
	
	private Instant dataPergunta;
	
	
	private String donoDaPergunta;
	
	// construtor personalizado recebendo uma entidade de pergunta
	
	@JsonCreator
	public PerguntaSobreProdutoResponse(PerguntaSobreProduto entity) {
		this.titulo = entity.getTitulo();
		this.dataPergunta = entity.getDataRegistro();
		this.donoDaPergunta = entity.getCliente().getEmail();
	}

	public String getTitulo() {
		return titulo;
	}

	public Instant getDataPergunta() {
		return dataPergunta;
	}

	public String getDonoDaPergunta() {
		return donoDaPergunta;
	}

	
}
