package com.cafe.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import com.cafe.modelo.enums.Medida;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author isabella
 *
 */

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@NamedQueries({
		@NamedQuery(name = "DespesaFertilizante.buscarDespesasFertilizantes", 
				query = "select d from DespesaFertilizante d where d.unidade = :codigo_unidade") 
})
public class DespesaFertilizante {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long tenantId;
	@NotNull
	private LocalDate data;

	private BigDecimal valorTotal;
	
	@Enumerated(EnumType.STRING)
	private Medida medida;

	@ManyToOne
	@JoinColumn(nullable = false, name = "codigo_unidade")
	private Unidade unidade;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Fertilizante fertilizante;

	@ManyToOne
	@JoinColumn(nullable = false)
	private NotaFiscal notaFiscal;

	@OneToMany(mappedBy = "despesaFertilizante", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DespesaFerTalhao> despesasTalhoes;

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
