package br.com.sic.modelo.localizacao;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
public @Data class DadosLocalizacaoIP implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("ipv4")
	private String ipv4;
	
	@JsonProperty("continent_name")
	private String continentName;
	
	@JsonProperty("country_name")
	private String countryName;
	
	@JsonProperty("subdivision_1_name")
	private String subdivision1Name;
	
	@JsonProperty("subdivision_2_name")
	private Object subdivision2Name;
	
	@JsonProperty("city_name")
	private String cityName;
	
	@JsonProperty("latitude")
	private double latitude;
	
	@JsonProperty("longitude")
	private double longitude;

}
