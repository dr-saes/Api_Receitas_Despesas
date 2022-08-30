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

import br.com.danielsaes.api_receitas_despesas.modelo.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

	@Query("SELECT mesReceita, descricao FROM Receita  WHERE mesReceita = :mesReceita and anoReceita = :anoReceita and descricao = :descricao")
	List<Receita> findByMesAnoEDescricao(@Param("mesReceita") int mesReceita,@Param("anoReceita") int anoReceita, @Param("descricao") String descricao);

	Page<Receita> findByDescricao(String descricao, Pageable paginacao);

	Page<Receita> findByAnoReceitaAndMesReceita(int anoReceita, int mesReceita, Pageable paginacao);
	
	@Query("SELECT SUM(valorReceita) FROM Receita WHERE anoReceita = :anoReceita and mesReceita = :mesReceita")
	Optional<BigDecimal> FindBySumValorReceitaAnoReceitaAndMesReceita(int anoReceita, int mesReceita);

	
}




