package com.cafe.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cafe.modelo.enums.TipoAuxiliarInsumos;
import com.cafe.modelo.enums.TipoCombustivel;
import com.cafe.modelo.enums.TipoInsumo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@NamedQueries({
	@NamedQuery(name="Maquina.buscarMaquinas", query="select u from Maquina u where u.tenant_id = :tenantId"),
})
public class Maquina {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long tenant_id;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	private String modelo;
	
	@PositiveOrZero
	private BigDecimal valor; // valor do bem
	
	@PositiveOrZero
	private BigDecimal potencia = new BigDecimal(0.0); // CV	
		
	@PositiveOrZero
	private Integer vidaUtil;
	
	@NotNull
	private LocalDate dataCompra;
	
	@Enumerated(EnumType.STRING)
	private TipoInsumo tipoInsumo;
	
	@Enumerated(EnumType.STRING)
	private TipoCombustivel tipoCombustivel;
	
	@Enumerated(EnumType.STRING)
	private TipoAuxiliarInsumos tipo;

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
