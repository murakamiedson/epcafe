package com.cafe.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.persistence.CascadeType;
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

import com.cafe.modelo.enums.Intensidade;

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
	private BigDecimal horasTrabalhadas = new BigDecimal(0);
	
	@Enumerated(EnumType.STRING)
	private Intensidade intensidade;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Unidade unidade;
	
	@OneToOne(cascade = CascadeType.ALL)	
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
