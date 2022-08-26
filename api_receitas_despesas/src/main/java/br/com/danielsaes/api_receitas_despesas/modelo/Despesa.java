package br.com.danielsaes.api_receitas_despesas.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Despesa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private BigDecimal valorDespesa;
	private LocalDate dataDespesa;
	private int mesDespesa;
	private int anoDespesa;
	
	@Enumerated(EnumType.STRING)
	private TipoDespesa tipoDespesa;
	
	
	public Despesa() {
		
	}
	
	public Despesa(String descricao, LocalDate dataDespesa, BigDecimal valorDespesa, int mesDespesa, TipoDespesa tipoDespesa, int anoDespesa) {
		this.descricao = descricao;
		this.dataDespesa = dataDespesa;
		this.valorDespesa = valorDespesa;
		this.mesDespesa = mesDespesa;
		this.tipoDespesa = tipoDespesa;
		this.anoDespesa = anoDespesa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Despesa other = (Despesa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public void setDataDespesa(LocalDate dataDespesa) {
		this.dataDespesa = dataDespesa;
	}

	public BigDecimal getValorDespesa() {
		return valorDespesa;
	}

	public void setValorDespesa(BigDecimal valorDespesa) {
		this.valorDespesa = valorDespesa;
	}

	public LocalDate getDatadespesa() {
		return dataDespesa;
	}

	public void setDatadespesa(LocalDate dataDespesa) {
		this.dataDespesa = dataDespesa;
	}

	public int getMesDespesa() {
		return mesDespesa;
	}

	public void setMesDespesa(int mesDespesa) {
		this.mesDespesa = mesDespesa;
	}

	public TipoDespesa getTipoDespesa() {
		return tipoDespesa;
	}

	public void setTipoDespesa(TipoDespesa tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}

	public int getAnoDespesa() {
		return anoDespesa;
	}

	public void setAnoDespesa(int anoDespesa) {
		this.anoDespesa = anoDespesa;
	}
	


}
