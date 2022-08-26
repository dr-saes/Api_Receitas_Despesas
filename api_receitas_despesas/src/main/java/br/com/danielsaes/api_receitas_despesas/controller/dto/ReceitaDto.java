package br.com.danielsaes.api_receitas_despesas.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.danielsaes.api_receitas_despesas.modelo.Receita;

public class ReceitaDto {

	private Long id;
	private String descricao;
	private LocalDate dataReceita;
	private BigDecimal valorReceita;

	public ReceitaDto() {
	}

	public ReceitaDto(Receita receita) {
		this.id = receita.getId();
		this.descricao = receita.getDescricao();
		this.dataReceita = receita.getDataReceita();
		this.valorReceita = receita.getValorReceita();
	}
	
	public ReceitaDto(Optional<Receita> receita) {
		this.id = receita.get().getId();
		this.descricao = receita.get().getDescricao();
		this.dataReceita = receita.get().getDataReceita();
		this.valorReceita = receita.get().getValorReceita();
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

	public static Page<ReceitaDto> converterLista(Page<Receita> listaReceitas) {
		return listaReceitas.map(ReceitaDto::new);
	}


}
