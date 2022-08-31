package br.com.danielsaes.api_receitas_despesas.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DespesaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(roles = {"ADMIN","USUARIO"})
	void deveriaDevolver409PoisDespesaComMesmoNome_AnoEMesJaExistente() throws Exception {
		URI uri = new URI("/despesas");
		String json = "{\"descricao\" : \"Mercado\" , \"valorDespesa\" : \"2000\" , \"dataDespesa\" : \"01/10/2024\" , \"tipoDespesa\" : \"ALIMENTACAO\"}";
		
		mockMvc .perform(MockMvcRequestBuilders.post(uri)
				.content(json).contentType(MediaType.APPLICATION_JSON)) 
				.andExpect(MockMvcResultMatchers.status().isConflict());
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN","USUARIO"})
	void deveriaDevolver201PoisDespesaCriada() throws Exception {
		URI uri = new URI("/despesas");
		String json = "{\"descricao\" : \"Mercado\" , \"valorDespesa\" : \"2000\" , \"dataDespesa\" : \"01/02/2012\" , \"tipoDespesa\" : \"ALIMENTACAO\"}";
		
		mockMvc .perform(MockMvcRequestBuilders.post(uri)
				.content(json).contentType(MediaType.APPLICATION_JSON)) 
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN","USUARIO"})
	void deveriaDevolver201PoisDespesaCriadaComAtribuirTipo_OUTRASPoisTipoNaoInfomado() throws Exception {
		URI uri = new URI("/despesas");
		String json = "{\"descricao\" : \"Pneu\" , \"valorDespesa\" : \"2000\" , \"dataDespesa\" : \"01/02/2012\"}";
		
		mockMvc .perform(MockMvcRequestBuilders.post(uri)
				.content(json).contentType(MediaType.APPLICATION_JSON)) 
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test 
	@WithMockUser(roles = {"ADMIN","USUARIO"})
	void deveriaDevolver200EListarTodasAsDespesasPorOrdemData() throws Exception {
		URI uri = new URI("/despesas");

		mockMvc .perform(MockMvcRequestBuilders.get(uri))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test 
	@WithMockUser(roles = {"ADMIN","USUARIO"})
	void deveriaDevolver200EListarDespesasComADescricaoPassadaNoParametro() throws Exception {
		URI uri = new URI("/despesas?descricao=mercado");

		mockMvc .perform(MockMvcRequestBuilders.get(uri))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test 
	@WithMockUser(roles = {"ADMIN","USUARIO"})
	void deveriaDevolver200EListarDespesasPorID() throws Exception {
		URI uri = new URI("/despesas/51");

		mockMvc .perform(MockMvcRequestBuilders.get(uri))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test 
	@WithMockUser(roles = {"ADMIN","USUARIO"})
	void deveriaDevolver404ComIDInexistente() throws Exception {
		URI uri = new URI("/despesas/12222222222");

		mockMvc .perform(MockMvcRequestBuilders.get(uri))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN","USUARIO"})
	void deveriaDevolver200PoisDespesaAtualizada() throws Exception {
		URI uri = new URI("/despesas/44");
		String json = "{\"descricao\" : \"Mercado\" , \"valorDespesa\" : \"1000\" , \"dataDespesa\" : \"01/01/2025\" , \"tipoDespesa\" : \"ALIMENTACAO\"}";

		mockMvc .perform(MockMvcRequestBuilders.put(uri)
				.content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser(roles = {"ADMIN","USUARIO"})
	void deveriaDevolver404PoisIdInexistente() throws Exception {
		URI uri = new URI("/despesas/3323333");
		String json = "{\"descricao\" : \"Mercado\" , \"valorDespesa\" : \"2000\" , \"dataDespesa\" : \"01/01/2025\" , \"tipoDespesa\" : \"ALIMENTACAO\"}";

		mockMvc .perform(MockMvcRequestBuilders.put(uri)
				.content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver200PoisIdExistente() throws Exception {
		URI uri = new URI("/despesas/57");

		mockMvc .perform(MockMvcRequestBuilders.delete(uri))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver404ComIdInexistente() throws Exception {
		URI uri = new URI("/despesas/38333333");

		mockMvc .perform(MockMvcRequestBuilders.delete(uri))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	@WithMockUser(roles = "Usuario")
	void deveriaDevolver403ENaoDeletarPoisIdInexistenteEUsuarioNaoAutorizado() throws Exception {
		URI uri = new URI("/despesas/38333333");

		mockMvc .perform(MockMvcRequestBuilders.delete(uri))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	@WithMockUser(roles = "Usuario")
	void deveriaDevolver403NaoDeletarPoisIdExistenteEUsuarioNaoAutorizado() throws Exception {
		URI uri = new URI("/despesas/38");

		mockMvc .perform(MockMvcRequestBuilders.delete(uri))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test 
	@WithMockUser(roles = {"ADMIN","USUARIO"})
	void deveriaDevolver200EListarDespesasPorAnoEMes() throws Exception {
		URI uri = new URI("/despesas/2024/1");

		mockMvc .perform(MockMvcRequestBuilders.get(uri))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test 
	@WithMockUser(roles = {"ADMIN","USUARIO"})
	void deveriaDevolver404EPoisParamentroDeBuscaNaoEncontardo() throws Exception {
		URI uri = new URI("/despesas/2015/12");

		mockMvc .perform(MockMvcRequestBuilders.get(uri))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
