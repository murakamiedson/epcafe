package com.cafe.modelo;

import java.time.OffsetDateTime;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cafe.modelo.enums.NivelEscolaridade;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@NamedQueries({
	@NamedQuery(name="Formacao.buscarFormacoes", query="select u from Formacao u where u.tenant_id = :tenantId"),
	@NamedQuery(name="Formacao.buscarPorFuncionario", query="select u from Formacao u where u.funcionario = :funcionario "
			+ "and u.tenant_id = :tenantId")
})
public class Formacao {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Long tenant_id;
	
	@NotBlank
	private String descricao;
	
	/*
	@NotEmpty
	@Lob
	private Blob comprovacao;*/
	
	@Enumerated(EnumType.STRING)
	private NivelEscolaridade nivelEscolaridade;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="codigo_funcionario")
	private Funcionario funcionario;
	
	
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
