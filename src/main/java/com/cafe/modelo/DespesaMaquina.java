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
import javax.persistence.OneToOne;
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
			query="select u from DespesaMaquina u where u.tenant_id = :tenantId"),
	@NamedQuery(name="DespesaMaquina.buscarMaquinasDistintas", 
			query="SELECT DISTINCT d.maquina FROM DespesaMaquina d where d.tenant_id = :tenantId" ),
	//@NamedQuery(name="DespesaMaquina.buscarValorTotalMensal", query="select ")
	
})
public class DespesaMaquina {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private Long tenant_id;	
	
	@NotNull
	private LocalDate mesAno;

	@PositiveOrZero
	private BigDecimal horasTrabalhadas;
	
	@Enumerated(EnumType.STRING)
	private FatorPotencia fatorPotencia;
	
	@OneToOne
	private Unidade unidade;
	
	@OneToOne	
	private Maquina maquina;
	
	private BigDecimal precoUnitarioCombustivel;
	
	private BigDecimal valorTotal;
	
	
	
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
