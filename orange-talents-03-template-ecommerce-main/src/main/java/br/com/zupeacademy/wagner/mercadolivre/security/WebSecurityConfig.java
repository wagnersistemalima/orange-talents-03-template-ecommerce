package br.com.zupeacademy.wagner.mercadolivre.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.zupeacademy.wagner.mercadolivre.usuario.UsuarioRepository;

// Classe contendo as configuraçoes de segurança

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository userRepository;

	// metodo para fazer injeção de dependencia para AuthenticationManager

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	// injeção de dependencia para a classe de autenticação

	@Autowired
	private AutenticacaoService autenticacaoService;

	// 1º método será de configuração de autenticação auth, a parte de controle de
	// acesso

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}

	// 2º método será de configurações de Autorização / a parte de url , quem pode
	// acessar aquela url
	// rota / a área de perfil de acesso

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/auth").permitAll()
				.antMatchers(HttpMethod.POST, "/usuarios").hasRole("MODERADOR")
				.antMatchers(HttpMethod.POST, "/produtos").hasRole("MODERADOR")
				.anyRequest().authenticated()
				.and().cors()
				.and().csrf().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, userRepository),UsernamePasswordAuthenticationFilter.class);
	}

	// 3º método para configuração de recursos estáticos (requisições para arquivos
	// javascript, css, imagens, etc..)

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
        .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**", "/h2-console/**");
	}
	
	// metodo main para criar uma senha criptografada hasch
	
	//public static void main(String[] args) {
	//System.out.println(new BCryptPasswordEncoder().encode("123456"));
	//}


}
