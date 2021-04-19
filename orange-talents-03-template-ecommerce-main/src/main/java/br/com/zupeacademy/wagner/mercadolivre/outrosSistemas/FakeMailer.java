package br.com.zupeacademy.wagner.mercadolivre.outrosSistemas;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

// classe fake para enviar email, assinando o contrato com a interface Mailer

@Component
@Primary
public class FakeMailer implements Mailer{

	@Override
	public void send(String body, String titulo, String emailCliente, String from, String emailDonoProduto) {
		
		System.out.println(body);
		System.out.println(titulo);
		System.out.println(emailCliente);
		System.out.println(from);
		System.out.println(emailDonoProduto);
	}

}
