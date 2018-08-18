package br.com.sic.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name = "TBL_CLIENTE")
public @Data class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_CLIENTE", nullable = false, length = 15)
	private Long id;

	@NotNull(message = "msg.campo.obrigatorio")
	@Size(max = 255, message = "msg.campo.maior.que")
	@Column(name = "NOME_CLIENTE", nullable = false, length = 255)
	private String nome;

	@NotNull(message = "msg.campo.obrigatorio")
	@Column(name = "IDADE_CLIENTE", nullable = false, length = 3)
	private Integer idade;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente")
	private List<ClimaHistorico> climaHistoricos;
}
