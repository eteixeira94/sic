package br.com.sic.servico;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sic.modelo.Cliente;
import br.com.sic.modelo.ClimaHistorico;
import br.com.sic.repositorio.ClienteRepositorio;

/**
 * 
 * @author Ednardo Teixeira
 */
@Service
public class ClienteServico {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	@Autowired
	private LocalizacaoServico localizacaoServico;

	@Transactional(value = TxType.SUPPORTS)
	public void salvar(@Valid Cliente cliente, HttpServletRequest requisicao) {
		List<ClimaHistorico> climaHistoricos = this.localizacaoServico.buildClimaHistoricos(cliente, requisicao);
		cliente.setClimaHistoricos(climaHistoricos);
		this.clienteRepositorio.save(cliente);
	}

	@Transactional(value = TxType.SUPPORTS)
	public void atualizar(@Valid Cliente cliente) {
		this.clienteRepositorio.save(cliente);
	}

	public Cliente consultarPor(Long id) {
		return this.clienteRepositorio.findById(id).get();
	}

	@Transactional(value = TxType.SUPPORTS)
	public void deletarPor(Long id) {
		this.clienteRepositorio.deleteById(id);
	}

	public List<Cliente> consultarTodos() {
		return this.clienteRepositorio.findAll();
	}
}
