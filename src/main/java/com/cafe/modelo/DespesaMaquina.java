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
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cafe.modelo.enums.FatorPotencia;

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
	@NamedQuery(name="DespesaMaquina.buscarDespesasMaquinas", 
			query="select u from DespesaMaquina u where u.unidade = :codigo_unidade"),
	
})
public class DespesaMaquina {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private Long tenant_id;	
	
	@NotNull
	private LocalDate data;

	@PositiveOrZero
	private BigDecimal tempoTrabalhado;
	
	private BigDecimal precoUnitarioCombustivel;
	
	private BigDecimal valorTotal;
	
	private BigDecimal distanciaTrabalhada;
	
	//true para horas e false para minutos
	private boolean unidadeHoras;
	
	@Enumerated(EnumType.STRING)
	private FatorPotencia fatorPotencia = FatorPotencia.INDEFINIDO;
	
	@ManyToOne
	@JoinColumn(nullable = false, name="codigo_unidade")
	private Unidade unidade;
	
	@ManyToOne
	@JoinColumn(nullable = false, name="codigo_maquina")
	private Maquina maquina;
	
	
	
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
