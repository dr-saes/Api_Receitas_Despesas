package br.com.danielsaes.api_receitas_despesas.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;

import br.com.danielsaes.api_receitas_despesas.enums.TipoDespesa;
import br.com.danielsaes.api_receitas_despesas.modelo.Despesa;

public class DespesaDtoListagem {

	private String descricao;
	private LocalDate dataDespesa;
	private BigDecimal valorDespesa;
	private TipoDespesa tipoDespesa;

	public DespesaDtoListagem() {
	}

	public DespesaDtoListagem(Despesa despesa) {
		this.descricao = despesa.getDescricao();
		this.dataDespesa = despesa.getDataDespesa();
		this.valorDespesa = despesa.getValorDespesa();
		this.tipoDespesa = despesa.getTipoDespesa();
	}
	

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataDespesa() {
		return dataDespesa;
	}

	public void setDataCriacao(LocalDate dataDespesa) {
		this.dataDespesa = dataDespesa;
	}

	public BigDecimal getValorReceita() {
		return valorDespesa;
	}

	public void setValorReceita(BigDecimal valorDespesa) {
		this.valorDespesa = valorDespesa;
	}
	
	public TipoDespesa getTipoDespesa() {
		return tipoDespesa;
	}

	public void setTipoDespesa(TipoDespesa tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}

	public static Page<Object> converterLista(Page<Despesa> listaDespesas) {
		return listaDespesas.map(DespesaDtoListagem::new);
	}
	
}
