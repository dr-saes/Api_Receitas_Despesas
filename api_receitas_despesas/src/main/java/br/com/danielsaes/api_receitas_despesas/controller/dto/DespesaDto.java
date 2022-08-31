package br.com.danielsaes.api_receitas_despesas.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.danielsaes.api_receitas_despesas.enums.TipoDespesa;
import br.com.danielsaes.api_receitas_despesas.modelo.Despesa;

public class DespesaDto {

	private Long id;
	private String descricao;
	private LocalDate dataDespesa;
	private BigDecimal valorDespesa;
	private TipoDespesa tipoDespesa;

	public DespesaDto() {
	}

	public DespesaDto(Despesa despesa) {
		this.id = despesa.getId();
		this.descricao = despesa.getDescricao();
		this.dataDespesa = despesa.getDataDespesa();
		this.valorDespesa = despesa.getValorDespesa();
		this.tipoDespesa = despesa.getTipoDespesa();
	}
	
	public DespesaDto(Optional<Despesa> despesa) {
		this.id = despesa.get().getId();
		this.descricao = despesa.get().getDescricao();
		this.dataDespesa = despesa.get().getDataDespesa();
		this.valorDespesa = despesa.get().getValorDespesa();
		this.tipoDespesa = despesa.get().getTipoDespesa();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public static Page<DespesaDto> converterLista(Page<Despesa> listaDespesas) {
		return listaDespesas.map(DespesaDto::new);
	}
	
}
