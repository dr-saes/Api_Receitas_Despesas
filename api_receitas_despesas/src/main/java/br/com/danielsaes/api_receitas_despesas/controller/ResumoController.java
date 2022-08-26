package br.com.danielsaes.api_receitas_despesas.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.danielsaes.api_receitas_despesas.controller.dto.ResumoDto;
import br.com.danielsaes.api_receitas_despesas.repository.DespesaRepository;
import br.com.danielsaes.api_receitas_despesas.repository.ReceitaRepository;

@RestController
@RequestMapping("/resumo")
public class ResumoController {

	@Autowired
	private ReceitaRepository receitaRepository;
	@Autowired
	private DespesaRepository despesaRepository;

	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<Object> resumoPorAnoMes(@PathVariable int ano, @PathVariable int mes) {

		try {
			Optional<BigDecimal> resumoSomaReceitaAnoMes = receitaRepository.FindBySumValorReceitaAnoReceitaAndMesReceita(ano, mes);
			Optional<BigDecimal> resumoSomaDespesaAnoMes = despesaRepository.FindBySumValorDespesaAnoDespesaAndMesDespesa(ano, mes);
			Optional<BigDecimal> saldoFinalDoMes = Optional.ofNullable(resumoSomaReceitaAnoMes.get().subtract(resumoSomaDespesaAnoMes.get()));
			
			Optional<BigDecimal> somaDeDespesasOutras = despesaRepository.findBySumValorDespesaAndTipoDespesaOutras(ano, mes);
			Optional<BigDecimal> somaDeDespesasAlimentacao = despesaRepository.findBySumValorDespesaAndTipoDespesaAlimentacao(ano, mes);
			Optional<BigDecimal> somaDeDespesasSaude = despesaRepository.findBySumValorDespesaAndTipoDespesaSaude(ano, mes);
			Optional<BigDecimal> somaDeDespesasMoradia = despesaRepository.findBySumValorDespesaAndTipoDespesaMoradia(ano, mes);
			Optional<BigDecimal> somaDeDespesasTransporte = despesaRepository.findBySumValorDespesaAndTipoDespesaTransporte(ano, mes);
			Optional<BigDecimal> somaDeDespesasEducacao = despesaRepository.findBySumValorDespesaAndTipoDespesaEducacao(ano, mes);
			Optional<BigDecimal> somaDeDespesasLazer = despesaRepository.findBySumValorDespesaAndTipoDespesaLazer(ano, mes);
			Optional<BigDecimal> somaDeDespesasImprevistos = despesaRepository.findBySumValorDespesaAndTipoDespesaImprevistos(ano, mes);
			
			List<Object> listaDeDespesaPorCategoria = new ArrayList<>();
			
			ArrayList<Object> outras = new ArrayList<>();
			outras.add("Outras");
			outras.add(somaDeDespesasOutras.orElse(BigDecimal.ZERO));
			
			List<Object> alimentacao =  new ArrayList<>();
			alimentacao.add("Alimentacao");
			alimentacao.add(somaDeDespesasAlimentacao.orElse(BigDecimal.ZERO));
			
			List<Object> saude = new ArrayList<>();
			saude.add("Saude");
			saude.add(somaDeDespesasSaude.orElse(BigDecimal.ZERO));
			
			List<Object> moradia = new ArrayList<>();
			moradia.add("Moradia");
			moradia.add(somaDeDespesasMoradia.orElse(BigDecimal.ZERO));
			
			List<Object> transporte = new ArrayList<>();
			transporte.add("Transporte");
			transporte.add(somaDeDespesasTransporte.orElse(BigDecimal.ZERO));
			
			List<Object> educacao = new ArrayList<>();
			educacao.add("Educacao");
			educacao.add(somaDeDespesasEducacao.orElse(BigDecimal.ZERO));
			
			List<Object> lazer = new ArrayList<>();
			lazer.add("Lazer");
			lazer.add(somaDeDespesasLazer.orElse(BigDecimal.ZERO));
			
			List<Object> imprevistos = new ArrayList<>();
			imprevistos.add("Imprevistos");
			imprevistos.add(somaDeDespesasImprevistos.orElse(BigDecimal.ZERO));
			
			listaDeDespesaPorCategoria.add(outras);
			listaDeDespesaPorCategoria.add(alimentacao);
			listaDeDespesaPorCategoria.add(saude);
			listaDeDespesaPorCategoria.add(moradia);
			listaDeDespesaPorCategoria.add(transporte);
			listaDeDespesaPorCategoria.add(educacao);
			listaDeDespesaPorCategoria.add(lazer);
			listaDeDespesaPorCategoria.add(imprevistos);
			
			return ResponseEntity.ok(new ResumoDto(resumoSomaReceitaAnoMes, resumoSomaDespesaAnoMes,
					saldoFinalDoMes,listaDeDespesaPorCategoria)); 
		
			
		} catch (java.util.NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parametro de pequisa não encontrado!");
		}
		 
	}
}