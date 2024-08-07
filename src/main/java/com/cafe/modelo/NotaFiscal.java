package com.cafe.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Log4j
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@NamedQueries({
	@NamedQuery(name="NotaFiscal.buscarNotasFiscais", 
			query="select u from NotaFiscal u where u.unidade = :codigo_unidade"),
	@NamedQuery(name="NotaFiscal.buscarNotaFiscalPorNumero",
			query="select u from NotaFiscal u where u.numero = :numero and u.unidade = :codigo_unidade"),
	@NamedQuery(name="NotaFiscal.buscarNotaFiscalPorTipoFertilizante",
			query="select u from NotaFiscal u where u.unidade = :codigo_unidade")
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
	@Column(unique = true)
	private String numero;	
	private String descricao;
	@NotNull
	private LocalDate dataEmissao;
	private String url;
	@PositiveOrZero
	private BigDecimal valorTotal = new BigDecimal(0);
	
	@ManyToOne
	@JoinColumn(nullable = false, name="codigo_unidade")
	private Unidade unidade;	
		
	@OneToMany( mappedBy = "notaFiscal", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Item> itens = new HashSet<>();
	
	@Transient
	public BigDecimal getValorItens() {
		BigDecimal total = new BigDecimal(0);
		for(Item i : this.getItens()) {
			//total = total.add(new BigDecimal(i.getQuantidade()).multiply(i.getValor()));
			total = total.add(i.getQuantidade().multiply(i.getValor()));
		}
		log.info("valorTotalItens : " + total);
		return total;
	}
	
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
