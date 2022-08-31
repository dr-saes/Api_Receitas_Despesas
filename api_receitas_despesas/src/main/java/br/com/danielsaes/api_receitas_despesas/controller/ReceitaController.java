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

import br.com.danielsaes.api_receitas_despesas.controller.dto.ReceitaDto;
import br.com.danielsaes.api_receitas_despesas.controller.dto.ReceitaDtoListagem;
import br.com.danielsaes.api_receitas_despesas.controller.form.AtualizacaoReceitaForm;
import br.com.danielsaes.api_receitas_despesas.controller.form.ReceitaForm;
import br.com.danielsaes.api_receitas_despesas.modelo.Receita;
import br.com.danielsaes.api_receitas_despesas.repository.ReceitaRepository;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

	
	private ReceitaRepository receitaRepository;
	
	@Autowired
	public ReceitaController(ReceitaRepository receitaRepository) {
		this.receitaRepository = receitaRepository;
	}
	 
	public ReceitaController() {
	} 

	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeReceitas", allEntries = true)
	public ResponseEntity<?> cadastrarReceita(@RequestBody @Valid ReceitaForm form,
			UriComponentsBuilder uriBuilder) throws Exception {

		Receita receita = new Receita();
		receita = form.toReceita();

		try {
			if (!receitaRepository.findByMesAnoEDescricao(receita.getMesReceita(), receita.getAnoReceita(), receita.getDescricao()).isEmpty()) {
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Receita Duplicada");
		}

		receitaRepository.save(receita);
		URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
		return ResponseEntity.created(uri).body(new ReceitaDto(receita)) ;
	}

	@GetMapping 
	@Cacheable(value = "listaDeReceitas")
	public Page<ReceitaDto> listarReceitas(@RequestParam(required = false) String descricao,
			@PageableDefault(sort = "dataReceita", direction = Direction.ASC, page = 0, size = 100) Pageable paginacao) {

		if (descricao == null) {
			Page<Receita> listaReceitas = receitaRepository.findAll(paginacao);
			return ReceitaDto.converterLista(listaReceitas);
		} else {
			Page<Receita> listaReceitas = receitaRepository.findByDescricao(descricao, paginacao);
			return ReceitaDto.converterLista(listaReceitas);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> listarPorId(@PathVariable Long id) {

		Optional<Receita> receita = receitaRepository.findById(id);
		if (receita.isPresent() & receita != null) {
			return ResponseEntity.ok(new ReceitaDto(receita));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
	}

	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeReceitas", allEntries = true)
	public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoReceitaForm form) {
		Optional<Receita> optional = receitaRepository.findById(id);
		if (optional.isPresent()) {
			Optional<Receita> receita = form.atualizar(id, receitaRepository);
			return ResponseEntity.ok(new ReceitaDto(receita));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
	}

	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeReceitas", allEntries = true)
	public ResponseEntity<Object> deletar(@PathVariable Long id) {
		Optional<Receita> receita = receitaRepository.findById(id);
		if (receita.isPresent()) {
			receitaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
	}
	
	
	@GetMapping("/{anoReceita}/{mesReceita}")
	public Object listarPorAno(@PathVariable int anoReceita,@PathVariable int mesReceita,
			@PageableDefault(sort = "dataReceita", direction = Direction.ASC, page = 0, size = 100) Pageable paginacao) {

		Page<Receita> listaAnoMes = receitaRepository.findByAnoReceitaAndMesReceita(anoReceita, mesReceita, paginacao);
		
		if(!listaAnoMes.isEmpty()) {
			return ReceitaDtoListagem.converter(listaAnoMes);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta inexistente");
	}
	 
}  
