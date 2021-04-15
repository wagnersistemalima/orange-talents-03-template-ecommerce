package br.com.zupeacademy.wagner.mercadolivre.compartilhado;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zupeacademy.wagner.mercadolivre.produto.CompraProduto;
import br.com.zupeacademy.wagner.mercadolivre.produto.PerguntaSobreProduto;

// classe contendo a logica de enviar email

//from -> nome para aparecer no provedor de email
	// body -> corpo do email
	// titulo -> assunto
	// emailCliente -> remetente
	// emailDonoProduto -> destinatario

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
	
	// metodo para enviar um email para o vendedor do produto, informando que o cliente quer comprar o produto
	
	public void novaCompra(CompraProduto compra) {
		mailer.send("<html>...<html>", "Compra de produto", compra.getCliente().getEmail(), "novaCompraNossoMercadoLivre", compra.getProduto().getUsuarioLogado().getEmail());
	}

}
