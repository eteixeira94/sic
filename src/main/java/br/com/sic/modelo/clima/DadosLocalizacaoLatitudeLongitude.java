package br.com.sic.modelo.clima;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
public @Data class DadosLocalizacaoLatitudeLongitude implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JsonProperty("distance")
	private Double distance;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("location_type")
	private String locationType;
	
	@JsonProperty("woeid")
	private Integer woeid;
	
	@JsonProperty("latt_long")
	private String lattLong;
}	
