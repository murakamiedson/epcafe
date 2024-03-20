package com.cafe.modelo;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
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
	private int quantidade;
	
	@PositiveOrZero
	private BigDecimal valor;
	
	@ManyToOne
	private Talhao talhao;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="codigo_despesa_fertilizante")
	private DespesaFertilizante despesaFertilizante;	
}
