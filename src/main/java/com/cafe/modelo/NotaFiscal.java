package com.cafe.modelo;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@NamedQueries({
	@NamedQuery(name="NotaFiscal.buscarNotasFiscais", 
			query="select u from NotaFiscal u where u.tenant_id = :tenantId"),
	@NamedQuery(name="NotaFiscal.buscarNotaFiscalPorNumero",
			query="select u from NotaFiscal u where u.numero = :numero and u.tenant_id = :tenantId")
})
public class NotaFiscal {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private Long tenant_id;
	
	@NotNull
	private String numero;
	
	private String descricao;
	
	@Positive
	private BigDecimal valorTotal;
	
	@Lob
	private byte[] imagem;
	
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
