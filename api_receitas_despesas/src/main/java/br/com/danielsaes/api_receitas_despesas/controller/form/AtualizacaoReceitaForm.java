package br.com.danielsaes.api_receitas_despesas.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.danielsaes.api_receitas_despesas.modelo.Receita;
import br.com.danielsaes.api_receitas_despesas.repository.ReceitaRepository;

public class AtualizacaoReceitaForm {
	 
	@NotBlank
	@NotBlank
	@NotNull
	private String descricao;

	@NotBlank
	@Pattern(regexp = "^\\d+(\\.\\d+{2})?$")
	@NotNull
	private String valorReceita;

	@NotNull
	@Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
	@NotBlank
	private String dataReceita;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt-br"));
	

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getValorReceita() {
		return valorReceita;
	}

	public void setValorReceita(String valorReceita) {
		this.valorReceita = valorReceita;
	}

	public String getDataReceita() {
		return dataReceita;
	}

	public void setDataReceita(String dataReceita) {
		this.dataReceita = dataReceita;
	}
	public Optional<Receita> atualizar(Long id, ReceitaRepository receitaRepository) {
		Optional<Receita> receita = receitaRepository.findById(id);
		receita.get().setDescricao(this.descricao);
		receita.get().setValorReceita(new BigDecimal(this.valorReceita));
		receita.get().setDataReceita(LocalDate.parse(this.dataReceita, formatter));
		receita.get().setMesReceita(LocalDate.parse(this.dataReceita, formatter).getMonthValue());
		receita.get().setAnoReceita(LocalDate.parse(this.dataReceita, formatter).getYear());
		return receita;
	}
	

}
