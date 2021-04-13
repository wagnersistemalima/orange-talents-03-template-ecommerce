package br.com.zupeacademy.wagner.mercadolivre.compartilhado;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zupeacademy.wagner.mercadolivre.produto.PerguntaSobreProduto;

// classe contendo a logica de enviar email

@Service
public class Emails {
	
	// dependencia para a interface
	
	@Autowired
	private Mailer mailer;
	
	// metodo para enviar um email contendo uma pergunta do cliente sobre o produto, este email sera enviado ao dono
	// do produto
	
	public void novaPergunta(@NotNull @Valid PerguntaSobreProduto perguntaSobreProduto ) {
		mailer.send("<html>...<html>", perguntaSobreProduto.getTitulo(), perguntaSobreProduto.getCliente().getEmail(),"novaPerguntaNossoMercadoLivre.com" , perguntaSobreProduto.getProduto().getUsuarioLogado().getEmail());
	}

}
