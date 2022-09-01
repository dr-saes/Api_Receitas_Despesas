package br.com.danielsaes.api_receitas_despesas.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

@Entity
public class Receita extends RepresentationModel<Receita> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private BigDecimal valorReceita;
	private LocalDate dataReceita;
	private int mesReceita;
	private int anoReceita;
	

	public Receita() {
	}

	public Receita(String descricao, LocalDate dataReceita, BigDecimal valorReceita, int mesReceita, int anoReceita) {
		this.descricao = descricao;
		this.dataReceita = dataReceita;
		this.valorReceita = valorReceita;
		this.mesReceita = mesReceita;
		this.anoReceita = anoReceita;
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
		Receita other = (Receita) obj;
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

	public LocalDate getDataReceita() {
		return dataReceita;
	}

	public void setDataReceita(LocalDate dataReceita) {
		this.dataReceita = dataReceita;
	}

	public BigDecimal getValorReceita() {
		return valorReceita;
	}

	public void setValorReceita(BigDecimal valorReceita) {
		this.valorReceita = valorReceita;
	}

	public int getMesReceita() {
		return mesReceita;
	}

	public void setMesReceita(int mesReceita) {
		this.mesReceita = mesReceita;
	}

	public int getAnoReceita() {
		return anoReceita;
	}

	public void setAnoReceita(int anoReceita) {
		this.anoReceita = anoReceita;
	}
	
	

}
