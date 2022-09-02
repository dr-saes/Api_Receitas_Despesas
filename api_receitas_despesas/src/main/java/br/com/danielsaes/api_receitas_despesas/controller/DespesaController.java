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

import br.com.danielsaes.api_receitas_despesas.controller.dto.DespesaDto;
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
	public ResponseEntity<?> cadastrarDespesa(@RequestBody @Valid DespesaForm form, UriComponentsBuilder uriBuilder)
			throws Exception {

		Despesa despesa = new Despesa();
		despesa = form.toDespesa();

		try {
			if (!despesaRepository
					.findByAnoMesEDescricao(despesa.getMesDespesa(), despesa.getAnoDespesa(), despesa.getDescricao())
					.isEmpty()) {
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
	public ResponseEntity<?> listarDespesas(@RequestParam(required = false) String descricao,
			@PageableDefault(sort = "dataDespesa", direction = Direction.ASC, page = 0, size = 100) Pageable paginacao) {

		if (descricao == null) {
			Page<Despesa> listaDespesas = despesaRepository.findAll(paginacao);
			if (listaDespesas.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista inexistente");
			} else {
				Page<DespesaDto> listaDto = DespesaDto.converterLista(listaDespesas);
				for (DespesaDto despesa : listaDto) {
					long id = despesa.getId();
					
					despesa
						.add(linkTo(methodOn(DespesaController.class).listarPorId(id, paginacao))
								.withRel("Listagem por Id")
								.withHref("http://localhost:8080/despesas/" + despesa.getId())
								.withType("GET"));
					despesa
						.add(linkTo(methodOn(DespesaController.class).listarPorId(id, paginacao))
								.withRel("Lista com a descricao: " + "'" + despesa.getDescricao() + "'")
								.withHref("http://localhost:8080/despesas?descricao=" + despesa.getDescricao())
								.withType("GET"));
					
					despesa
					.add(linkTo(methodOn(DespesaController.class).listarPorId(id, paginacao))
							.withRel("Despesas por ano/mes ")
							.withHref("http://localhost:8080/despesas/" + despesa.getDataDespesa().getYear()
									+ "/" + despesa.getDataDespesa().getMonthValue())
							.withType("GET"));
					
					despesa
					.add(linkTo(methodOn(DespesaController.class).listarPorId(id, paginacao))
							.withRel("Resumo por ano/mes ")
							.withHref("http://localhost:8080/resumo/" + despesa.getDataDespesa().getYear()
									+ "/" + despesa.getDataDespesa().getMonthValue())
							.withType("GET"));
					
				}
				return new ResponseEntity<Page<DespesaDto>>(listaDto, HttpStatus.OK);
			}
		} else {
			Page<Despesa> listaDespesaDescricao = despesaRepository.findByDescricao(descricao, paginacao);
			Page<DespesaDto> listaDtoDescricao = DespesaDto.converterLista(listaDespesaDescricao);
			for (DespesaDto despesa : listaDtoDescricao) {
		
		despesa
				.add(linkTo(methodOn(DespesaController.class).listarDespesas(despesa.getDescricao(), paginacao))
					.withRel(" Lista de receitas")
					.withHref("http://localhost:8080/despesas")
					.withType("GET"));
		
		despesa
				.add(linkTo(methodOn(DespesaController.class).listarDespesas(despesa.getDescricao(), paginacao))
					.withRel("Listagem por Id")
					.withHref("http://localhost:8080/despesas/" + despesa.getId())
					.withType("GET"));;
			}
			return new ResponseEntity<Page<DespesaDto>>(listaDtoDescricao, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable Long id,
			@PageableDefault(sort = "despesa", direction = Direction.ASC, page = 0, size = 100) Pageable paginacao) {

		Optional<Despesa> despesa = despesaRepository.findById(id);
		if (!despesa.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
		} else {
			DespesaDto despesaDto = DespesaDto.converterDespesa(despesa);
			despesaDto
					.add(linkTo(methodOn(DespesaController.class).listarDespesas(despesaDto.getDescricao(), paginacao))
							.withRel("Lista de despesas")
							.withHref("http://localhost:8080/despesas")
							.withType("GET"));
			despesaDto
					.add(linkTo(methodOn(DespesaController.class).listarDespesas(despesaDto.getDescricao(), paginacao))
							.withRel("Deletar despesa")
							.withHref("http://localhost:8080/despesas/" + despesaDto.getId())
							.withType("DELETE"));
			
			despesaDto
					.add(linkTo(methodOn(DespesaController.class).listarDespesas(despesaDto.getDescricao(), paginacao))
							.withRel("Atualizar despesa")
							.withHref("http://localhost:8080/despesa/" + despesaDto.getId())
							.withType("PUT"));
				
			
			
			
			return new ResponseEntity<DespesaDto>(despesaDto, HttpStatus.OK);
		}
	}

	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeDespesas", allEntries = true)
	public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoDespesaForm form) {
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
	public ResponseEntity<Object> deletar(@PathVariable Long id) {
		Optional<Despesa> despesa = despesaRepository.findById(id);
		if (despesa.isPresent()) {
			despesaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
	}

	@GetMapping("/{anoDespesa}/{mesDespesa}")
	public Object listarDespesasPorAnoEMes(@PathVariable int anoDespesa, @PathVariable int mesDespesa,
			@PageableDefault(sort = "dataDespesa", direction = Direction.ASC, page = 0, size = 100) Pageable paginacao) {

		Page<Despesa> listaDespesasAnoMes = despesaRepository.findByAnoDespesaAndMesDespesa(anoDespesa, mesDespesa,
				paginacao);

		if (listaDespesasAnoMes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta inexistente");
		} else {
			Page<DespesaDto> listaDespesasAnoMesDto = DespesaDto.converterLista(listaDespesasAnoMes);
			for (DespesaDto despesa : listaDespesasAnoMesDto) {
				
				despesa
						.add(linkTo(methodOn(DespesaController.class).listarDespesas(despesa.getDescricao(), paginacao))
							.withRel(" Lista de despesas")
							.withHref("http://localhost:8080/despesas")
							.withType("GET"));
		
				despesa
						.add(linkTo(methodOn(DespesaController.class).listarDespesas(despesa.getDescricao(), paginacao))
							.withRel("Listagem por Id")
							.withHref("http://localhost:8080/despesas/" + despesa.getId())
							.withType("GET"));
			}
			return new ResponseEntity<Page<DespesaDto>>(listaDespesasAnoMesDto, HttpStatus.OK);
		}
	}
}
