package br.com.danielsaes.api_receitas_despesas.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.danielsaes.api_receitas_despesas.repository.UsuarioRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;
	 
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests()
			
			.antMatchers(HttpMethod.POST,"/auth").permitAll()
			
			.antMatchers(HttpMethod.GET,"/despesas/**").hasAnyRole("USUARIO","ADMIN")
			.antMatchers(HttpMethod.GET,"/receitas/**").hasAnyRole("USUARIO","ADMIN")
			
//			.antMatchers(HttpMethod.GET,"/despesas/**").permitAll()
//			.antMatchers(HttpMethod.GET,"/receitas/**").permitAll()
			
			.antMatchers(HttpMethod.DELETE,"/receitas/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE,"/despesas/**").hasRole("ADMIN")
			
			.antMatchers(HttpMethod.POST,"/receitas/**").hasAnyRole("USUARIO","ADMIN")
			.antMatchers(HttpMethod.POST,"/despesas/**").hasAnyRole("USUARIO","ADMIN")
			
			.antMatchers(HttpMethod.GET,"/resumo/**").hasRole("ADMIN")
			
			
			.anyRequest().authenticated()
		.and()	
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository),
					UsernamePasswordAuthenticationFilter.class);
		
		
		return http.build(); 
		
		
	}
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
		return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**"); 
		
	}
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception { 
		return authenticationConfiguration.getAuthenticationManager(); 
		}

	
	
//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123456"));
//	}
}



	

