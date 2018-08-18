package br.com.sic.servico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.sic.modelo.Cliente;
import br.com.sic.modelo.ClimaHistorico;
import br.com.sic.modelo.clima.ClimaLocalizacao;
import br.com.sic.modelo.clima.DadosLocalizacaoLatitudeLongitude;
import br.com.sic.modelo.localizacao.DadosLocalizacaoIP;
import br.com.sic.modelo.localizacao.LocalizacaoIP;

/**
 * 
 * @author Ednardo Teixeira
 */
@Service
public class LocalizacaoServico {
	
	@Value("${url.integracao.localizacao}")
	private String apiLocalizacao;

	@Value("${url.integracao.localizacao.clima}")
	private String apiLocalizacaoClima;
	
	@Value("${url.integracao.clima.historico}")
	private String apiClimaHistorico;

	public List<ClimaHistorico> buildClimaHistoricos(Cliente cliente, HttpServletRequest requisicao) {
		String ipOrigem = this.getIpRequisicaoRemota(requisicao);
		LocalizacaoIP geolocalizacao = this.consultarLatLogPorIp(ipOrigem);
		Date dataRequisicao = new Date();
		List<ClimaHistorico> climaHistoricos = null;
		
		// API REST retorna status igual a success
		if (geolocalizacao != null && "success".equals(geolocalizacao.getStatus())) {
			DadosLocalizacaoLatitudeLongitude dadosLocalizacaoLatitudeLongitude = this.consultarLocalizacaoClima(geolocalizacao);
			if (dadosLocalizacaoLatitudeLongitude != null) {
				ClimaLocalizacao[] climaLocalizacoes = this.consultarHistoricosDoClimaDaLocalizacaoPela(dadosLocalizacaoLatitudeLongitude.getWoeid(), dataRequisicao);
				
				if (climaLocalizacoes != null && climaLocalizacoes.length > 0) {
					climaHistoricos = new ArrayList<>();
					
					for (ClimaLocalizacao climaLocalizacao : climaLocalizacoes) {
						climaHistoricos.add(ClimaHistorico
								.builder()
								.dataRequisicao(dataRequisicao)
								.horario(climaLocalizacao.getCreated())
								.ipOrigem(ipOrigem)
								.latitude(geolocalizacao.getDadosLocalizacao().getLatitude())
								.longitude(geolocalizacao.getDadosLocalizacao().getLongitude())
								.temperaturaMaxima(climaLocalizacao.getMaxTemp())
								.temperaturaMinima(climaLocalizacao.getMinTemp())
								.cliente(cliente)
								.build());
					}
				}
			}
		}
		
		return climaHistoricos;
	}

	private <T> T chamarAPIRest(Class<T> clazz, String getApiRest, Object... variaveis) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(getApiRest, clazz, variaveis);
	}
	
	/**
	 * @param woeid - https://pt.wikipedia.org/wiki/WOEID
	 * @param dataRequisicao
	 * @return
	 */
	private ClimaLocalizacao[] consultarHistoricosDoClimaDaLocalizacaoPela(Integer woeid, Date dataRequisicao) {
		return this.chamarAPIRest(ClimaLocalizacao[].class, this.apiClimaHistorico, woeid, new SimpleDateFormat("yyyy/M/dd").format(dataRequisicao));
	}

	/**
	 * Quando houver mais de uma cidade para o IP da requisição, então pegar a menor distância
	 * RN
	 * Quando executar a busca de clima por geolocalização, caso não exista a cidade especifica de 
	 * origem, utilize o resultado mais próximo.
	 */
	private DadosLocalizacaoLatitudeLongitude consultarLocalizacaoClima(LocalizacaoIP geolocalizacao) {
		DadosLocalizacaoIP dadosLocalizacao = geolocalizacao.getDadosLocalizacao();
		DadosLocalizacaoLatitudeLongitude[] dadosLocalizacaoLatitudeLongitudes = this.chamarAPIRest(DadosLocalizacaoLatitudeLongitude[].class, this.apiLocalizacaoClima, dadosLocalizacao.getLatitude(), dadosLocalizacao.getLongitude());
		
		DadosLocalizacaoLatitudeLongitude menorDistancia = null;
		if (dadosLocalizacaoLatitudeLongitudes != null) {
			if (dadosLocalizacaoLatitudeLongitudes.length == 1) {
				return dadosLocalizacaoLatitudeLongitudes[0];
			}
			
			for (DadosLocalizacaoLatitudeLongitude dadosLocalizacaoLatitudeLongitude : dadosLocalizacaoLatitudeLongitudes) {
				if (menorDistancia == null) {
					menorDistancia = dadosLocalizacaoLatitudeLongitude;
					continue;
				}
				
				if (dadosLocalizacaoLatitudeLongitude.getDistance().doubleValue() < menorDistancia.getDistance().doubleValue()) {
					menorDistancia = dadosLocalizacaoLatitudeLongitude;
				}
			}
		}
		
		return menorDistancia;
	}
	
	private LocalizacaoIP consultarLatLogPorIp(String ipOrigem) {
		return this.chamarAPIRest(LocalizacaoIP.class, this.apiLocalizacao, ipOrigem);
	}

	/**
	 * https://en.wikipedia.org/wiki/X-Forwarded-For
	 * @param requisicao
	 * @return
	 */
	private String getIpRequisicaoRemota(HttpServletRequest requisicao) {
		String ip = requisicao.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = requisicao.getRemoteAddr();
		}
		
		return ip;
	}
}
