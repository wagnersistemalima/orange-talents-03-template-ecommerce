package br.com.zupeacademy.wagner.mercadolivre.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Classe contendo as configuraçoes de segurança


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	// 1º método será de configuração de autenticação auth,  a parte de controle de acesso

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		super.configure(auth);
	}
	
	// 2º método será de  configurações de Autorização / a parte de url , quem pode acessar aquela url
	// rota / a área de perfil de acesso
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	}
	
	// 3º método para configuração de recursos estáticos (requisições para arquivos javascript, css, imagens, etc..)
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**");
	}



}
