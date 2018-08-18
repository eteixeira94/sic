package br.com.sic.modelo.localizacao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
public @Data class LocalizacaoIP {
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("data")
	private DadosLocalizacaoIP dadosLocalizacao;
	
}
