package com.cafe.modelo;

import java.time.OffsetDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

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
	@NamedQuery(name="DespesaMaquina.buscarDespesasMaquinas", 
			query="select u from DespesaMaquina u where u.tenant_id = :tenantId"),
})
public class DespesaMaquina {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private Long tenant_id;	
	private int ano;

	private float horas1 = 0;
	private float horas2 = 0;
	private float horas3 = 0;
	private float horas4 = 0;
	private float horas5 = 0;
	private float horas6 = 0;
	private float horas7 = 0;
	private float horas8 = 0;
	private float horas9 = 0;
	private float horas10 = 0;
	private float horas11 = 0;
	private float horas12 = 0;
	
	
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
