package br.com.zupeacademy.wagner.mercadolivre.compartilhado;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public interface Mailer {
	
	// from -> nome para aparecer no provedor de email
	// body -> corpo do email
	// titulo -> assunto
	// emailCliente -> remetente
	// emailDonoProduto -> destinatario

	public void send(@NotBlank String body,@NotBlank String titulo,@Email @NotBlank String emailCliente,@NotBlank String from ,@Email @NotBlank String emailDonoProduto);
		
	

}
