package com.raven.course.services;

import java.util.List;
import java.util.Optional;

import com.raven.course.domain.Cidade;
import com.raven.course.domain.Endereco;
import com.raven.course.domain.enums.TipoCliente;
import com.raven.course.dto.ClienteDTO;
import com.raven.course.dto.ClienteNewDTO;
import com.raven.course.repositories.EnderecoRepository;
import com.raven.course.services.exceptions.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.raven.course.domain.Cliente;
import com.raven.course.repositories.ClienteRepository;
import com.raven.course.services.exceptions.ObjectNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException("Cliente " + id + " nao encontrado"));
	}

	public Cliente update(Cliente obj) {
		Cliente newobj = find(obj.getId());
		updateData(newobj, obj);
		return clienteRepository.save(newobj);
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public void delete(Integer id) {
		find(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new ConstraintViolationException("Nao eh possivel excluir um cliente pois ha entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer size, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto){
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto){
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() != null){
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null){
			cli.getTelefones().add(objDto.getTelefone2());
		}

		return cli;
	}

	private void updateData(Cliente newobj, Cliente obj){
		newobj.setNome(obj.getNome());
		newobj.setEmail(obj.getEmail());
	}
}
