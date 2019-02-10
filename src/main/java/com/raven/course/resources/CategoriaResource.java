package com.raven.course.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.raven.course.domain.Categoria;
import com.raven.course.dto.CategoriaResponseDTO;
import com.raven.course.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	/***
	 * RequestBody transforma o json enviado no objeto java esperado pelo metodo,
	 * PageRequest retorna de forma paginada os atributos solicitados pelo cliente
	 */

	@Autowired
	private CategoriaService categoriaService;				

	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria categoria = categoriaService.find(id);	
		return ResponseEntity.ok().body(categoria);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj = categoriaService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = categoriaService.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		categoriaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping()
	public ResponseEntity<List<CategoriaResponseDTO>> findAll() {
		List<Categoria> categorias = categoriaService.findAll();	
		
		List<CategoriaResponseDTO> categoriaDto = categorias.stream()
				.map(obj -> new CategoriaResponseDTO(obj))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(categoriaDto);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoriaResponseDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "24") Integer size, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		
		Page<Categoria> categorias = categoriaService.findPage(page, size, orderBy, direction);
		Page<CategoriaResponseDTO> categoriasDto = categorias.map(obj -> new CategoriaResponseDTO(obj));
		
		return ResponseEntity.ok().body(categoriasDto);
	}
}
