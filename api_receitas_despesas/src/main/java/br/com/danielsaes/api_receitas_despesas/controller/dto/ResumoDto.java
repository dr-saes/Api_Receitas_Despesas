package br.com.danielsaes.api_receitas_despesas.controller.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.RepresentationModel;

public class ResumoDto extends RepresentationModel<ResumoDto>{
	
	private BigDecimal somaReceitas;
	private BigDecimal somaDespesas;
	private BigDecimal saldoFinalDoMes;
	private List<Object> listaDeDespesaPorCategoria;	
	
	public ResumoDto(Optional<BigDecimal> resumoReceitaAnoMes, Optional<BigDecimal> resumoDespesaAnoMes,
			Optional<BigDecimal> saldoFinalDoMes,List<Object> listaDeDespesaPorCategoria
			) {
		
		
		
		this.somaReceitas = resumoReceitaAnoMes.get();
		this.somaDespesas = resumoDespesaAnoMes.get();
		this.saldoFinalDoMes = saldoFinalDoMes.get();
		this.listaDeDespesaPorCategoria = listaDeDespesaPorCategoria;
		
	}
	

	public ResumoDto(Optional<BigDecimal> resumoSomaReceitaAnoMes) {
	}


	public BigDecimal getSomaReceitas() {
		return somaReceitas;
	}

	public void setSomaReceitas(BigDecimal somaReceitas) {
		this.somaReceitas = somaReceitas;
	}

	public BigDecimal getSomaDespesas() {
		return somaDespesas;
	}

	public void setSomaDespesas(BigDecimal somaDespesas) {
		this.somaDespesas = somaDespesas;
	}

	public BigDecimal getSaldoFinalDoMes() {
		return saldoFinalDoMes;
	}

	public void setSaldoFinalDoMes(BigDecimal saldoFinalDoMes) {
		this.saldoFinalDoMes = saldoFinalDoMes;
	}


	public List<Object> getlistaDeDespesaPorCategoria() {
		return listaDeDespesaPorCategoria;
	}


	public void setlistaDeDespesaPorCategoria(List<Object> listaDeDespesaPorCategoria) {
		this.listaDeDespesaPorCategoria = listaDeDespesaPorCategoria;
	}


	public static ResumoDto converterLista(Optional<BigDecimal> resumoSomaReceitaAnoMes) {
		return new ResumoDto(resumoSomaReceitaAnoMes);
	}



	
	}

	
	
