package com.cafe.modelo;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@NamedQueries({
	@NamedQuery(name="Instalacao.buscarInstalacoes", query="select u from Instalacao u where u.tenant_id = :tenantId"),
	@NamedQuery(name="Instalacao.buscarPorUnidade", query="select u from Instalacao u where u.unidade = :unidade "
			+ "and u.tenant_id = :tenantId")
})
public class Instalacao {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private Long tenant_id;
		
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@PositiveOrZero
	private float valor;
	@PositiveOrZero
	private int vidaUtil;

	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false, name="codigo_unidade")
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
