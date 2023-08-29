package com.cafe.modelo;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author isabella
 *
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity

public class QuantidadeTalhao {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal quantidade;
	
	@ManyToOne
	private Talhao talhao;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="codigo_despesa_fertilizante")
	private DespesaFertilizante despesaFertilizante;	
}
