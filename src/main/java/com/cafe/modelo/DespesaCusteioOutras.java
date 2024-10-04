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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cafe.modelo.enums.TipoDespesaCusteioOutras;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@NamedQueries({
	@NamedQuery(name = "DespesaCusteioOutras.buscarDespesasCusteioOutras", 
		query = "select d from DespesaCusteioOutras d where d.unidade = :codigo_unidade "
				+ "and  d.eCusteio = :tipo_despesa")
})
public class DespesaCusteioOutras {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long tenantId;
	@NotNull
	private LocalDate data;

	private BigDecimal valorTotal;
	
	private String descricao;
	
	//0 nao é custeio
	//1 é custeio
	private Boolean eCusteio;
	
	@Enumerated(EnumType.STRING)
	private TipoDespesaCusteioOutras tipo;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "codigo_unidade")
	private Unidade unidade;
	
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
