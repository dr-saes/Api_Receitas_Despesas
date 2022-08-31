  package br.com.danielsaes.api_receitas_despesas.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AutenticacaoControllerTest {
 
	@Autowired  
	private MockMvc mockMvc;

	@Test
	void deveriaAutenticarUsuarioComDadosCorretos() throws Exception {
		URI uri = new URI("/auth");
		String json = "{\"nome\":\"davi\", \"senha\":\"123456\"}";

		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.tipo").value("Bearer"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").isString());
	}
	
	@Test
	void naoDeveriaAutenticarUsuarioComDadosIncorretos() throws Exception {
		URI uri = new URI("/auth");
		String json = "{\"nome\":\"davi\", \"senha\":\"123456000000\"}";

		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
}