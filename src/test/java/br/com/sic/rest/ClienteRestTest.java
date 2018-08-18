package br.com.sic.rest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sic.SicRestApplication;
import br.com.sic.modelo.Cliente;
import br.com.sic.servico.ClienteServico;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SicRestApplication.class)
public class ClienteRestTest {
	
    private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private ClienteServico clienteServico;
	
	@PostConstruct
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void deveCadastrarClienteComDadosDoClimaIntegradosComSucesso() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		Cliente cliente = new Cliente();
		cliente.setNome("Ednardo Teixeira");
		cliente.setIdade(24);
		
		mockMvc.perform(post("/api/cliente/salvar")
				  .header("X-FORWARDED-FOR", "179.236.13.105")
				  .content(mapper.writeValueAsString(cliente))
			      .contentType(MediaType.APPLICATION_JSON_UTF8))
			      .andExpect(status().isOk())
			      .andExpect(content().string("Cliente criado com sucesso."));
		
		mockMvc.perform(get("/api/cliente/" + clienteServico.consultarTodos().get(0).getId().longValue())
				  .accept(MediaType.APPLICATION_JSON_UTF8)
			      .contentType(MediaType.APPLICATION_JSON_UTF8))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.nome").value(cliente.getNome()))
			      .andExpect(jsonPath("$.climaHistoricos").isArray());
	}
	
	
	@Test
	public void deveAtualizarClienteComSucesso() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		Cliente cliente = new Cliente();
		cliente.setNome("Ednardo Teixeira");
		cliente.setIdade(24);
		
		mockMvc.perform(post("/api/cliente/salvar")
				  .header("X-FORWARDED-FOR", "179.236.13.105")
				  .content(mapper.writeValueAsString(cliente))
			      .contentType(MediaType.APPLICATION_JSON_UTF8))
			      .andExpect(status().isOk())
			      .andExpect(content().string("Cliente criado com sucesso."));
		
		long idCliente = clienteServico.consultarTodos().get(0).getId().longValue();
		Cliente clienteDB = mapper.readValue(mockMvc.perform(get("/api/cliente/"+ idCliente)
				  .accept(MediaType.APPLICATION_JSON_UTF8)
			      .contentType(MediaType.APPLICATION_JSON_UTF8))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.nome").value(cliente.getNome()))
			      .andExpect(jsonPath("$.climaHistoricos").isArray()).andReturn().getResponse().getContentAsString(), Cliente.class);
		
		clienteDB.setIdade(25);
		mockMvc.perform(put("/api/cliente/atualizar")
				  .content(mapper.writeValueAsString(clienteDB))
			      .contentType(MediaType.APPLICATION_JSON_UTF8))
			      .andExpect(status().isOk())
			      .andExpect(content().string("Cliente atualizado com sucesso."));
		
		mockMvc.perform(get("/api/cliente/" + idCliente)
				  .accept(MediaType.APPLICATION_JSON_UTF8)
			      .contentType(MediaType.APPLICATION_JSON_UTF8))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.idade").value(25));
	}
	
	@Test
	public void deveDeletarClienteComSucesso() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		Cliente cliente = new Cliente();
		cliente.setNome("Ednardo Teixeira");
		cliente.setIdade(24);
		
		mockMvc.perform(post("/api/cliente/salvar")
				  .header("X-FORWARDED-FOR", "179.236.13.105")
				  .content(mapper.writeValueAsString(cliente))
			      .contentType(MediaType.APPLICATION_JSON_UTF8))
			      .andExpect(status().isOk())
			      .andExpect(content().string("Cliente criado com sucesso."));
		
		mockMvc.perform(delete("/api/cliente/deletar/" + clienteServico.consultarTodos().get(0).getId().longValue())
			      .contentType(MediaType.APPLICATION_JSON_UTF8))
			      .andExpect(status().isOk())
			      .andExpect(content().string("Cliente deletado com sucesso"));
	}
    
    private static int totalRequisicoes;
    public static synchronized void incrementarRequisicao() {
    	++totalRequisicoes;
    }
	
	@Test
	/**
	 * Teste para simular 1000 requisições concorrentes e que será resolvida em 10s.
	 * Maquina com 16 GB e Core i7
	 * @throws Exception
	 */
	public void deveSuportarNRequisicoesComSucesso() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		
		Cliente cliente = new Cliente();
		cliente.setNome("Ednardo Teixeira");
		cliente.setIdade(24);

		
		mockMvc.perform(post("/api/cliente/salvar")
				  .header("X-FORWARDED-FOR", "179.236.13.105")
				  .content(mapper.writeValueAsString(cliente))
			      .contentType(MediaType.APPLICATION_JSON_UTF8))
			      .andExpect(status().isOk())
			      .andExpect(content().string("Cliente criado com sucesso."));
		
		long idCliente = clienteServico.consultarTodos().get(0).getId().longValue();
		
		totalRequisicoes = 0;
		for (int i = 0; i < 100; i++) {
			new Thread(new RequisicaoTop10(idCliente)).start();
		}
		
		Thread.sleep(10 * 1000);
		
		assertEquals(1000, ClienteRestTest.totalRequisicoes);
	}
	
	class RequisicaoTop10 implements Runnable {
		
		private long idCliente;
		
		public RequisicaoTop10(long idCliente) {
			this.idCliente = idCliente;
		}

		@Override
		public void run() {
			try {
				for (int j = 0; j < 10; j++) {
					mockMvc.perform(get("/api/cliente/" + this.idCliente)
							.accept(MediaType.APPLICATION_JSON_UTF8)
							.contentType(MediaType.APPLICATION_JSON_UTF8))
							.andExpect(status().isOk());
					
					ClienteRestTest.incrementarRequisicao();
				}
			} catch (Exception e) {}
		}
	}
}
