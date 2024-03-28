package com.cafe.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@NamedQueries({
	@NamedQuery(name="NotaFiscal.buscarNotasFiscais", 
			query="select u from NotaFiscal u where u.tenant_id = :tenantId"),
	@NamedQuery(name="NotaFiscal.buscarNotaFiscalPorNumero",
			query="select u from NotaFiscal u where u.numero = :numero and u.tenant_id = :tenantId"),
	@NamedQuery(name="NotaFiscal.buscarNotaFiscalPorTipoFertilizante",
			query="select u from NotaFiscal u where u.tenant_id = :tenantId")
})
public class NotaFiscal implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ToString.Include
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private Long tenant_id;	
	@NotNull
	private String numero;	
	private String descricao;	
	private LocalDate dataEmissao;	
	private String fileName;
	@PositiveOrZero
	private BigDecimal valorTotal = new BigDecimal(0);
	
	//@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	//@Fetch(value = FetchMode.JOIN)
    //@JoinTable(name="Item", joinColumns={@JoinColumn(name="codigo_nota")}, 
    //						inverseJoinColumns={@JoinColumn(name="codigo_fertilizante")})
	
	@OneToMany( mappedBy = "notaFiscal", cascade = CascadeType.ALL)
	private List<Item> itens;
	
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
