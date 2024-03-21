package com.cafe.modelo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author isabella
 *
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity

public class DespesaFerTalhao {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long tenantId;
	
	@PositiveOrZero
	private int quantidade = 0;
	
	@PositiveOrZero
	private BigDecimal valor = new BigDecimal(0);
	
	@ManyToOne
	@JoinColumn(name = "codigo_despesa", nullable = false)
	private DespesaFertilizante despesafertilizante;
	
	@ManyToOne
	@JoinColumn(name = "codigo_talhao", nullable = false)
	private Talhao talhao;
	
}
