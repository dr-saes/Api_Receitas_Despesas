package br.com.danielsaes.api_receitas_despesas.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
	public ResponseEntity<?> cadastrarReceita(@RequestBody @Valid ReceitaForm form, UriComponentsBuilder uriBuilder)
			throws Exception {

		Receita receita = new Receita();
		receita = form.toReceita();

		try {
			if (!receitaRepository
					.findByMesAnoEDescricao(receita.getMesReceita(), receita.getAnoReceita(), receita.getDescricao())
					.isEmpty()) {
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Receita Duplicada");
		}

		receitaRepository.save(receita);
		URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
		return ResponseEntity.created(uri).body(new ReceitaDto(receita));
	}

	@GetMapping
	@Cacheable(value = "listaDeReceitas")
	public ResponseEntity<?> listarReceitas(@RequestParam(required = false) String descricao,
			@PageableDefault(sort = "dataReceita", direction = Direction.ASC, page = 0, size = 100) Pageable paginacao) {

		if (descricao == null) {
			Page<Receita> listaReceitas = receitaRepository.findAll(paginacao);
			if (listaReceitas.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista inexistente");
			} else {
				Page<ReceitaDto> listaDto = ReceitaDto.converterLista(listaReceitas);
				for (ReceitaDto receita : listaDto) {
					long id = receita.getId();
					
					receita
						.add(linkTo(methodOn(ReceitaController.class).listarPorId(id, paginacao))
							.withRel("Listagem por Id")
							.withHref("http://localhost:8080/receitas/" + receita.getId())
							.withType("GET"));
					receita
						.add(linkTo(methodOn(ReceitaController.class).listarPorId(id, paginacao))
							.withRel("Lista com a descricao: " + "'" + receita.getDescricao() + "'")
							.withHref("http://localhost:8080/receitas?descricao=" + receita.getDescricao())
							.withType("GET"));
					
					receita
					.add(linkTo(methodOn(ReceitaController.class).listarPorId(id, paginacao))
							.withRel("Receitas por ano/mes ")
							.withHref("http://localhost:8080/receitas/" + receita.getDataReceita().getYear()
									+ "/" + receita.getDataReceita().getMonthValue())
							.withType("GET"));
					
					receita
					.add(linkTo(methodOn(ReceitaController.class).listarPorId(id, paginacao))
							.withRel("Resumo por ano/mes ")
							.withHref("http://localhost:8080/resumo/" + receita.getDataReceita().getYear()
									+ "/" + receita.getDataReceita().getMonthValue())
							.withType("GET"));
				}
				return new ResponseEntity<Page<ReceitaDto>>(listaDto, HttpStatus.OK);
			}
		} else {
			Page<Receita> listaReceitasDescricao = receitaRepository.findByDescricao(descricao, paginacao);
			Page<ReceitaDto> listaDtoDescricao = ReceitaDto.converterLista(listaReceitasDescricao);
			for (ReceitaDto receita : listaDtoDescricao) {
				
				receita
						.add(linkTo(methodOn(ReceitaController.class).listarReceitas(receita.getDescricao(), paginacao))
							.withRel(" Lista de receitas")
							.withHref("http://localhost:8080/receitas")
							.withType("GET"));
				
			  	receita
						.add(linkTo(methodOn(ReceitaController.class).listarReceitas(receita.getDescricao(), paginacao))
							.withRel("Listagem por Id")
							.withHref("http://localhost:8080/receitas/" + receita.getId())
							.withType("GET"));
			}
			return new ResponseEntity<Page<ReceitaDto>>(listaDtoDescricao, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable Long id,
			@PageableDefault(sort = "dataReceita", direction = Direction.ASC, page = 0, size = 100) Pageable paginacao) {

		Optional<Receita> receita = receitaRepository.findById(id);
		if (!receita.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
		} else {
			ReceitaDto receitaDto = ReceitaDto.converterReceita(receita);
			
			receitaDto
					.add(linkTo(methodOn(ReceitaController.class).listarReceitas(receitaDto.getDescricao(), paginacao))
						.withRel(" Lista de receitas")
						.withHref("http://localhost:8080/receitas")
						.withType("GET"));
			
			receitaDto
					.add(linkTo(methodOn(ReceitaController.class).listarReceitas(receitaDto.getDescricao(), paginacao))
						.withRel("Deletar receita")
						.withHref("http://localhost:8080/receitas/" + receitaDto.getId())
						.withType("DELETE"));
			
			receitaDto
					.add(linkTo(methodOn(ReceitaController.class).listarReceitas(receitaDto.getDescricao(), paginacao))
						.withRel("Atualizar receita")
						.withHref("http://localhost:8080/receitas/" + receitaDto.getId())
						.withType("PUT"));

			return new ResponseEntity<ReceitaDto>(receitaDto, HttpStatus.OK);
		}
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
	public Object listarPorAno(@PathVariable int anoReceita, @PathVariable int mesReceita,
			@PageableDefault(sort = "dataReceita", direction = Direction.ASC, page = 0, size = 100) Pageable paginacao) {

		Page<Receita> listaReceitasAnoMes = receitaRepository.findByAnoReceitaAndMesReceita(anoReceita, mesReceita,
				paginacao);

		if (listaReceitasAnoMes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta inexistente");
		} else {
			Page<ReceitaDto> listaReceitasAnoMesDto = ReceitaDto.converterLista(listaReceitasAnoMes);
			for (ReceitaDto receita : listaReceitasAnoMesDto) {
				
		receita
				.add(linkTo(methodOn(ReceitaController.class).listarReceitas(receita.getDescricao(), paginacao))
					.withRel(" Lista de receitas")
					.withHref("http://localhost:8080/receitas")
					.withType("GET"));
		
	  	receita
				.add(linkTo(methodOn(ReceitaController.class).listarReceitas(receita.getDescricao(), paginacao))
					.withRel("Listagem por Id")
					.withHref("http://localhost:8080/receitas/" + receita.getId())
					.withType("GET"));
			}
			return new ResponseEntity<Page<ReceitaDto>>(listaReceitasAnoMesDto, HttpStatus.OK);
		}
	}

}
