package com.cafe.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
@NamedQueries({
	@NamedQuery(name="DespesaFertilizante.buscarDespesasFertilizantes", 
			query="select u from DespesaFertilizante u where u.tenant_id = :tenantId")
})
public class DespesaFertilizante {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private Long tenant_id;
	
	@ManyToOne
	private Fertilizante fertilizante;
	
	@NotNull
	private LocalDate mesAno;
	
	@NotNull
	private String notaFiscal;
	
	@Positive
	private BigDecimal quantidade;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="despesaFertilizante", fetch = FetchType.EAGER)
	private List<QuantidadeTalhao> qdesTalhoes;
	
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
