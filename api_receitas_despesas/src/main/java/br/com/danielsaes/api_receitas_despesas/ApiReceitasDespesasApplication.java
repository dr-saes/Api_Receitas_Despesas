package br.com.danielsaes.api_receitas_despesas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport 
@EnableCaching 
public class ApiReceitasDespesasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiReceitasDespesasApplication.class, args);
		//System.out.println(new BCryptPasswordEncoder().encode("123456"));  
	}

}
     