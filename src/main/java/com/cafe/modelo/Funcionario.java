package com.cafe.modelo;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
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
	@NamedQuery(name="Funcionario.buscarFuncionarios", query="select u from Funcionario u where u.tenant_id = :tenantId"),
	@NamedQuery(name="Funcionario.buscarPorUnidade", query="select u from Funcionario u where u.unidade = :unidade "
			+ "and u.tenant_id = :tenantId")
})
public class Funcionario {
	
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
	private float salario;
	
	@NotNull
	private LocalDate dataNascimento;
	
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="funcionario", fetch = FetchType.EAGER)
	//@JoinColumn(name = "codigo_formacao")
	private List<Formacao> formacoes = new ArrayList<>();
	
	
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
	
	
	@Transient
	public int getIdade() {
		LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(dataNascimento, dataAtual);
        return periodo.getYears();
	}	

}
