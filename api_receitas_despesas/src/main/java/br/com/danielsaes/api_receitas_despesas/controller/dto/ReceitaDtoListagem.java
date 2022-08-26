package br.com.danielsaes.api_receitas_despesas.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;

import br.com.danielsaes.api_receitas_despesas.modelo.Receita;

public class ReceitaDtoListagem {

	private String descricao;
	private LocalDate dataReceita;
	private BigDecimal valorReceita;

	public ReceitaDtoListagem() {
	}

	public ReceitaDtoListagem(Receita receita) {
		this.descricao = receita.getDescricao();
		this.dataReceita = receita.getDataReceita();
		this.valorReceita = receita.getValorReceita();
	}


	public ReceitaDtoListagem(Page<Receita> listaAnoMes) {
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataReceita() {
		return dataReceita;
	}

	public void setDataCriacao(LocalDate dataReceita) {
		this.dataReceita = dataReceita;
	}

	public BigDecimal getValorReceita() {
		return valorReceita;
	}

	public void setValorReceita(BigDecimal valorReceita) {
		this.valorReceita = valorReceita;
	}


	public static Page<Object> converter(Page<Receita> listaAnoMesPage) {
		return listaAnoMesPage.map(ReceitaDtoListagem::new);
	}


}
