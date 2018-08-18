package br.com.sic.rest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sic.modelo.Cliente;
import br.com.sic.servico.ClienteServico;

@RestController
@RequestMapping(value = "/api/cliente", consumes = "application/json;charset=utf-8")
public class ClienteRest {
	
	@Autowired
	private ClienteServico clienteServico;
	
	@PostMapping("/salvar")
	public ResponseEntity<?> salvar(@RequestBody @Valid Cliente cliente, HttpServletRequest requisicao) {
		
		if (cliente.getId() != null) {
			throw new RuntimeException("Operação de atualização não permitida na operação de salvar.");
		}
		
		this.clienteServico.salvar(cliente, requisicao);
		return new ResponseEntity<>("Cliente criado com sucesso.", HttpStatus.OK);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizar(@RequestBody @Valid Cliente cliente) {
		
		if (cliente.getId() == null) {
			throw new RuntimeException("Operação de criação não permitida na operação de atualização.");
		}
		
		this.clienteServico.atualizar(cliente);
		return new ResponseEntity<>("Cliente atualizado com sucesso.", HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> consultarPor(@PathVariable("id") Long id) {
		return new ResponseEntity<>(this.clienteServico.consultarPor(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletarPor(@PathVariable("id") Long id) {
		this.clienteServico.deletarPor(id);
		return new ResponseEntity<>("Cliente deletado com sucesso", HttpStatus.OK);
	}
	
	@GetMapping("/consultar-todos")
	public ResponseEntity<?> consultarTodos() {
		return new ResponseEntity<>(this.clienteServico.consultarTodos(), HttpStatus.OK);
	}
}
