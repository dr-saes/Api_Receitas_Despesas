package br.com.danielsaes.api_receitas_despesas.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import br.com.danielsaes.api_receitas_despesas.modelo.Receita;
import br.com.danielsaes.api_receitas_despesas.repository.ReceitaRepository;

@ExtendWith(MockitoExtension.class)
class ReceitaControllerTest {

	@InjectMocks
	private ReceitaController receitaController;
	
	@Mock
	private ReceitaRepository receitaRepository;
	
	@Test
	void deveriaCadastrarUmaReceita() {
		List<Receita> receitas = receitas();
		
		
	}

	private List<Receita> receitas() {
		List<Receita> receitas = new ArrayList<>();
		
		Receita receita1 = new Receita("Salario",
							LocalDate.now(),
							new BigDecimal("10000"), 01, 2022);
		
		Receita receita2 = new Receita("Salario",
				LocalDate.now(),
				new BigDecimal("10000"), 02, 2022);
		
		Receita receita3 = new Receita("Salario",
				LocalDate.now(),
				new BigDecimal("10000"), 03, 2022);
		
		receitas.add(receita1);
		receitas.add(receita2);
		receitas.add(receita2);
		
		return receitas;
	}

}
