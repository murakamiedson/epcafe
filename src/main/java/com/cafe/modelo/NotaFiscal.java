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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
	@Column(unique=true)
	private String numero;	
	private String descricao;	
	private LocalDate dataEmissao;	
	
	@Lob
	private byte[] imagem;
	@PositiveOrZero
	private BigDecimal valorTotal = new BigDecimal(0);
		
	@OneToMany( mappedBy = "notaFiscal", cascade = CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	private List<Item> itens;
	
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
