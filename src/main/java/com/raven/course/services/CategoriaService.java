package com.raven.course.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import com.raven.course.domain.Categoria;
import com.raven.course.repositories.CategoriaRepository;
import com.raven.course.services.exceptions.ConstraintViolationException;
import com.raven.course.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	/***
	 * O metodo buscar retorna um objeto Optional, que pode ser null caso a categoria nao exista
	 * caso isso ocorra ele ira retornar uma excecao personalizada 
	 */
	
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria find(Integer id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
																		
		return categoria.orElseThrow(() -> new ObjectNotFoundException(  
				"Objeto nao encontrado! id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return categoriaRepository.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return categoriaRepository.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new ConstraintViolationException("Nao eh possivel excluir uma categoria que possui produtos");
		}
	}
}
