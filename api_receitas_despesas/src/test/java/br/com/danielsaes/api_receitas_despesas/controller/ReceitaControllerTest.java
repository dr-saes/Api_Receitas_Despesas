package br.com.danielsaes.api_receitas_despesas.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReceitaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver409PoisDescricaoComMesmoNome_AnoEMesJaExistente() throws Exception {
		URI uri = new URI("/receitas");
		String json = "{\"descricao\" : \"Salario\" , \"valorReceita\" : \"10000\" , \"dataReceita\" : \"01/01/2024\"}";

		mockMvc .perform(MockMvcRequestBuilders.post(uri)
				.content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isConflict());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver201PoisNovaReceitaCriada() throws Exception {
		URI uri = new URI("/receitas");
		String json = "{\"descricao\" : \"Salario\" , \"valorReceita\" : \"50000\" , \"dataReceita\" : \"01/01/2041\"}";

		mockMvc .perform(MockMvcRequestBuilders.post(uri)
				.content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test 
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver200EListarTodasAsReceitasPorOrdemData() throws Exception {
		URI uri = new URI("/receitas");

		mockMvc .perform(MockMvcRequestBuilders.get(uri))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test 
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver200EListarReceitasComADescricaoPassadaNoParametro() throws Exception {
		URI uri = new URI("/receitas?descricao=salario");

		mockMvc .perform(MockMvcRequestBuilders.get(uri))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test 
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver200EListarReceitasPorID() throws Exception {
		URI uri = new URI("/receitas/12");

		mockMvc .perform(MockMvcRequestBuilders.get(uri))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test 
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver404ComIDInexistente() throws Exception {
		URI uri = new URI("/receitas/121111111");

		mockMvc .perform(MockMvcRequestBuilders.get(uri))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver200PoisReceitaAtualizada() throws Exception {
		URI uri = new URI("/receitas/12");
		String json = "{\"descricao\" : \"Salario\" , \"valorReceita\" : \"50000\" , \"dataReceita\" : \"01/09/2040\"}";

		mockMvc .perform(MockMvcRequestBuilders.put(uri)
				.content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver404EDeletarPoisIdInexistente() throws Exception {
		URI uri = new URI("/receitas/123333333");
		String json = "{\"descricao\" : \"Salario\" , \"valorReceita\" : \"50000\" , \"dataReceita\" : \"01/09/2040\"}";

		mockMvc .perform(MockMvcRequestBuilders.put(uri)
				.content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver200PoisIdExistente() throws Exception {
		URI uri = new URI("/receitas/56");

		mockMvc .perform(MockMvcRequestBuilders.delete(uri))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver404PoisIdInexistente() throws Exception {
		URI uri = new URI("/receitas/43777777");

		mockMvc .perform(MockMvcRequestBuilders.delete(uri))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	@WithMockUser(roles = "Usuario")
	void deveriaDevolver403PoisReceitaNaoPodeSerAtualizadaUsuarioNaoAutorizado() throws Exception {
		URI uri = new URI("/receitas/12");
		String json = "{\"descricao\" : \"Salario\" , \"valorReceita\" : \"50000\" , \"dataReceita\" : \"01/09/2040\"}";

		mockMvc .perform(MockMvcRequestBuilders.put(uri)
				.content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = "Usuario")
	void deveriaDevolver403ENaoDeletarPoisIdInexistenteEUsuarioNaoAutorizado() throws Exception {
		URI uri = new URI("/receitas/123333333");
		String json = "{\"descricao\" : \"Salario\" , \"valorReceita\" : \"50000\" , \"dataReceita\" : \"01/09/2040\"}";

		mockMvc .perform(MockMvcRequestBuilders.put(uri)
				.content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(roles = "Usuario")
	void deveriaDevolver403PoisIdExistenteMasUsuarioNãoAutorizado() throws Exception {
		URI uri = new URI("/receitas/51");

		mockMvc .perform(MockMvcRequestBuilders.delete(uri))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(roles = "Usuario")
	void deveriaDevolver403PoisIdInexistenteMasUsuarioNãoAutorizado() throws Exception {
		URI uri = new URI("/receitas/43777777");

		mockMvc .perform(MockMvcRequestBuilders.delete(uri))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test 
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver200EListarReceitasPorAnoEMes() throws Exception {
		URI uri = new URI("/receitas/2024/1");

		mockMvc .perform(MockMvcRequestBuilders.get(uri))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
	
}