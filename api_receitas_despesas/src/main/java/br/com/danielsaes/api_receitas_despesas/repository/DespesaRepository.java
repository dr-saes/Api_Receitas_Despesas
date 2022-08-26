package br.com.danielsaes.api_receitas_despesas.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.danielsaes.api_receitas_despesas.modelo.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

	@Query("SELECT mesDespesa, descricao FROM Despesa  WHERE mesDespesa = :mesDespesa and anoDespesa = :anoDespesa and descricao = :descricao")
	List<Despesa> findByAnoMesEDescricao(@Param("mesDespesa") int mesDespesa, @Param("anoDespesa") int anoDespesa, @Param("descricao") String descricao);
	
	Page<Despesa> findByDescricao(String descricao, Pageable paginacao);

	Page<Despesa> findByAnoDespesaAndMesDespesa(int anoDespesa, int mesDespesa, Pageable paginacao);
	
	@Query("SELECT SUM(valorDespesa) FROM Despesa WHERE anoDespesa = :anoDespesa and mesDespesa = :mesDespesa")
	Optional<BigDecimal> FindBySumValorDespesaAnoDespesaAndMesDespesa(int anoDespesa, int mesDespesa);

	@Query("SELECT SUM(valorDespesa) FROM Despesa WHERE anoDespesa = :anoDespesa and mesDespesa = :mesDespesa and tipoDespesa = 'OUTRAS'")
	Optional<BigDecimal> findBySumValorDespesaAndTipoDespesaOutras(int anoDespesa, int mesDespesa);
	
	@Query("SELECT SUM(valorDespesa) FROM Despesa WHERE anoDespesa = :anoDespesa and mesDespesa = :mesDespesa and tipoDespesa = 'ALIMENTACAO'")
	Optional<BigDecimal> findBySumValorDespesaAndTipoDespesaAlimentacao(int anoDespesa, int mesDespesa);
	
	@Query("SELECT SUM(valorDespesa) FROM Despesa WHERE anoDespesa = :anoDespesa and mesDespesa = :mesDespesa and tipoDespesa = 'SAUDE'")
	Optional<BigDecimal> findBySumValorDespesaAndTipoDespesaSaude(int anoDespesa, int mesDespesa);
	
	@Query("SELECT SUM(valorDespesa) FROM Despesa WHERE anoDespesa = :anoDespesa and mesDespesa = :mesDespesa and tipoDespesa = 'MORADIA'")
	Optional<BigDecimal> findBySumValorDespesaAndTipoDespesaMoradia(int anoDespesa, int mesDespesa);
	
	@Query("SELECT SUM(valorDespesa) FROM Despesa WHERE anoDespesa = :anoDespesa and mesDespesa = :mesDespesa and tipoDespesa = 'TRANSPORTE'")
	Optional<BigDecimal> findBySumValorDespesaAndTipoDespesaTransporte(int anoDespesa, int mesDespesa);
	
	@Query("SELECT SUM(valorDespesa) FROM Despesa WHERE anoDespesa = :anoDespesa and mesDespesa = :mesDespesa and tipoDespesa = 'EDUCACAO'")
	Optional<BigDecimal> findBySumValorDespesaAndTipoDespesaEducacao(int anoDespesa, int mesDespesa);
	
	@Query("SELECT SUM(valorDespesa) FROM Despesa WHERE anoDespesa = :anoDespesa and mesDespesa = :mesDespesa and tipoDespesa = 'LAZER'")
	Optional<BigDecimal> findBySumValorDespesaAndTipoDespesaLazer(int anoDespesa, int mesDespesa);
	
	@Query("SELECT SUM(valorDespesa) FROM Despesa WHERE anoDespesa = :anoDespesa and mesDespesa = :mesDespesa and tipoDespesa = 'IMPREVISTOS'")
	Optional<BigDecimal> findBySumValorDespesaAndTipoDespesaImprevistos(int anoDespesa, int mesDespesa);
	
}
