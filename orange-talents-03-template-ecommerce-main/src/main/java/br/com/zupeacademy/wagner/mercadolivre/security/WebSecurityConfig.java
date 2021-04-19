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
				.antMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMINISTRADOR")       // inserir usuarios
				
				
				.antMatchers(HttpMethod.POST, "/produtos/*/imagens").hasRole("VENDEDOR")      // inserir imagem produto
				.antMatchers(HttpMethod.POST, "/produtos").hasRole("VENDEDOR")                 // inserir produto
				.antMatchers(HttpMethod.GET, "/produtos/*").hasRole("VENDEDOR")               // mostrar detalhes do produto
				
				
				.antMatchers(HttpMethod.POST, "/produtos/*/opinioes").hasRole("CLIENTE")       // adicionar opiniao ao produto
				.antMatchers(HttpMethod.POST, "/produtos/*/perguntas").hasRole("CLIENTE")         // adicionar pergunta ao produto
				.antMatchers(HttpMethod.POST, "/produtos/*/compras").hasRole("CLIENTE")        // fazer uma compra
				.antMatchers(HttpMethod.GET, "/produtos/*").hasRole("CLIENTE")                   // ver detalhes do produto
				
				
				.antMatchers(HttpMethod.POST, "/produtos/retorno-pagseguro/*").hasRole("PAGSEGURO")  // retorno do gateway pagamento PagSeguro
				
				.antMatchers(HttpMethod.POST, "/produtos/retorno-paypal/*").hasRole("PAYPAL")      // retorno do gateway pagamento  PayPal
				
				
				.antMatchers(HttpMethod.POST, "/notas-fiscais").permitAll()              // sistema externo mokado nota fiscal   
				
				.antMatchers(HttpMethod.POST, "/ranking").permitAll()                  // sistema externo mokado ranking
				
				
				
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
