package br.com.sic.modelo.clima;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
public @Data class ClimaLocalizacao {
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("weather_state_name")
	private String weatherStateName;
	
	@JsonProperty("weather_state_abbr")
	private String weatherStateAbbr;
	
	@JsonProperty("wind_direction_compass")
	private String windDirectionCompass;
	
	@JsonProperty("created")
	@JsonDeserialize(using = DeserializacaoStringParaData.class)
	private Date created;
	
	@JsonProperty("applicable_date")
	private String applicableDate;
	
	@JsonProperty("min_temp")
	private Float minTemp;
	
	@JsonProperty("max_temp")
	private Float maxTemp;
	
	@JsonProperty("the_temp")
	private Float theTemp;
	
	@JsonProperty("wind_speed")
	private Float windSpeed;
	
	@JsonProperty("wind_direction")
	private Float windDirection;
	
	@JsonProperty("air_pressure")
	private Float airPressure;
	
	@JsonProperty("humidity")
	private Integer humidity;
	
	@JsonProperty("visibility")
	private Float visibility;
	
	@JsonProperty("predictability")
	private Integer predictability;
}
