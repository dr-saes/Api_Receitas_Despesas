package br.com.danielsaes.api_receitas_despesas.controller;


import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.danielsaes.api_receitas_despesas.controller.dto.DespesaDto;
import br.com.danielsaes.api_receitas_despesas.controller.dto.DespesaDtoListagem;
import br.com.danielsaes.api_receitas_despesas.controller.form.AtualizacaoDespesaForm;
import br.com.danielsaes.api_receitas_despesas.controller.form.DespesaForm;
import br.com.danielsaes.api_receitas_despesas.modelo.Despesa;
import br.com.danielsaes.api_receitas_despesas.repository.DespesaRepository;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

	@Autowired
	private DespesaRepository despesaRepository;

	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeDespesas", allEntries = true)
	public ResponseEntity<?> cadastrarDespesa(@RequestBody @Valid DespesaForm form,
			UriComponentsBuilder uriBuilder) throws Exception {

		Despesa despesa = new Despesa();
		despesa = form.toDespesa();

		try {
			if (!despesaRepository.findByAnoMesEDescricao(despesa.getMesDespesa(),despesa.getAnoDespesa(), despesa.getDescricao()).isEmpty()) {
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Despesa Duplicada");
		}

		despesaRepository.save(despesa);
		URI uri = uriBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
		return ResponseEntity.created(uri).body(new DespesaDto(despesa));
	}

	@GetMapping
	@Cacheable(value = "listaDeDespesas") 
	public Page<DespesaDto> listarDespesas(@RequestParam (required = false)String descricao,
			@PageableDefault(sort = "dataDespesa", direction = Direction.ASC, page = 0, size = 100) Pageable paginacao) {

		if(descricao == null) {
			Page<Despesa> listaDespesas = despesaRepository.findAll(paginacao);
			return DespesaDto.converterLista(listaDespesas);
		}else {
			Page<Despesa> listaDespesas = despesaRepository.findByDescricao(descricao, paginacao);
			return DespesaDto.converterLista(listaDespesas);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> listarPorId(@PathVariable Long id) {

		Optional<Despesa> despesa = despesaRepository.findById(id);
		if (despesa.isPresent() & despesa != null) {
			return ResponseEntity.ok(new DespesaDto(despesa));
		} 
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");

	}

	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeDespesas", allEntries = true)
	public ResponseEntity<Object> atualizar(@PathVariable Long id,
			@RequestBody @Valid AtualizacaoDespesaForm form) {
		Optional<Despesa> optional = despesaRepository.findById(id);
		if (optional.isPresent()) {
			Optional<Despesa> despesa = form.atualizar(id, despesaRepository);
			return ResponseEntity.ok(new DespesaDto(despesa));
		} 
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeDespesas", allEntries = true)
	public ResponseEntity<Object> deletar (@PathVariable Long id){
		Optional<Despesa> despesa = despesaRepository.findById(id);
		if(despesa.isPresent()) {
			despesaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
	}
	
	@GetMapping("/{anoDespesa}/{mesDespesa}")
	public Page<Object> listarPorAno(@PathVariable int anoDespesa,@PathVariable int mesDespesa,
			@PageableDefault(sort = "dataDespesa", direction = Direction.ASC, page = 0, size = 100) Pageable paginacao) {
		
		Page<Despesa> listaAnoMes = despesaRepository.findByAnoDespesaAndMesDespesa(anoDespesa, mesDespesa, paginacao);
		
		if(!listaAnoMes.isEmpty()) {
			return DespesaDtoListagem.converterLista(listaAnoMes);
		}
		return Page.empty();
	}
}
