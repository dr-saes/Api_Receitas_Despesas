package br.com.danielsaes.api_receitas_despesas.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ResumoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver200ListarResumoPorAnoEPorMes() throws Exception {
		URI uri = new URI("/resumo/2024/1");

		mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void deveriaDevolver404ComParametroInvalido() throws Exception {
		URI uri = new URI("/resumo/2050/1");

		mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());

	}

	@Test
	@WithMockUser(roles = "USUARIO")
	void deveriaDevolver403ENaoListarResumoPorAnoEPorMesPoisUsuarioNaoAutorizado() throws Exception {
		URI uri = new URI("/resumo/2024/1");

		mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isForbidden());

	}

	@Test
	@WithMockUser(roles = "USUARIO")
	void deveriaDevolver403ComParametroInvalidoEComUsuarioNaoAutorizado() throws Exception {
		URI uri = new URI("/resumo/2050/1");

		mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isForbidden());

	}
}
