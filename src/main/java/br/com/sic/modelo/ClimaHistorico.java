package br.com.sic.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_CLIMA_HISTORICO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class ClimaHistorico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name =  "ID_CLIMA_HIST", nullable = false, length = 15)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="ID_CLIENTE", nullable = false, referencedColumnName = "ID_CLIENTE", updatable = false)
	@JsonIgnore
	private Cliente cliente;
	
	@Column(name =  "IP_CLIMA", nullable = false, length = 50, updatable = false)
	private String ipOrigem;

	@Column(name =  "LONGITUDE", nullable = false, length = 26, precision = 13, updatable = false)
	private double longitude;

	@Column(name =  "LATITUDE", nullable = false, length = 26, precision = 13, updatable = false)
	private double latitude;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name =  "DATA_REQUISICAO", nullable = false, length = 7, updatable = false)
	private Date dataRequisicao;

	@Column(name =  "TEMP_MINIMA", nullable = false, length = 19, precision = 4, updatable = false)
	private Float temperaturaMinima;

	@Column(name =  "TEMP_MAXIMA", nullable = false, length = 19, precision = 4, updatable = false)
	private Float temperaturaMaxima;

	@Column(name =  "HORARIO", nullable = false, length = 7, updatable = false)
	private Date horario;
	
}
