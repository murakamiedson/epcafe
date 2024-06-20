package com.cafe.modelo;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
	
	@ManyToOne
	@JoinColumn(nullable = false, name="codigo_unidade")
	private Unidade unidade;
	
	@PositiveOrZero
	private BigDecimal quantidade = new BigDecimal(0);
	
	@PositiveOrZero
	private BigDecimal valor = new BigDecimal(0);
	
	@ManyToOne
	@JoinColumn(name = "codigo_despesa", nullable = false)
	private DespesaFertilizante despesaFertilizante;
	
	@ManyToOne
	@JoinColumn(name = "codigo_talhao", nullable = false)
	private Talhao talhao;
	
	
	/*
	 * Datas de Criação e Modificação
	 */
	@CreationTimestamp	
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataCriacao;
	
	@UpdateTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataModificacao;
}
