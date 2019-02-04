package com.raven.course.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raven.course.domain.Cliente;
import com.raven.course.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value = "/{id}")
	public ResponseEntity<Cliente> findCliente(@PathVariable Integer id){
		Cliente cliente = clienteService.find(id);
		return ResponseEntity.ok().body(cliente);
	}
}
