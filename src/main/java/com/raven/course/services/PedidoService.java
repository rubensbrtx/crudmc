package com.raven.course.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raven.course.domain.Pedido;
import com.raven.course.repositories.PedidoRepository;
import com.raven.course.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;  

	public Pedido buscar(Integer id) {
		
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! id: " + id + ", Tipo: " + Pedido.class.getName()));
		
	}
}
