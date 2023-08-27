package com.cafe.modelo;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cafe.modelo.enums.Medida;
import com.cafe.modelo.enums.TipoAuxiliarInsumos;
import com.cafe.modelo.enums.TipoInsumo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@NamedQueries({
	@NamedQuery(name="Fertilizante.buscarFertilizantes", query="select u from Fertilizante u where u.tenant_id = :tenantId"),
	
})
public class Fertilizante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long tenant_id;
	
		
	@Enumerated(EnumType.STRING)
	private Medida medida;
	
	@Enumerated(EnumType.STRING)
	private TipoInsumo tipoInsumo;
	
	@Enumerated(EnumType.STRING)
	private TipoAuxiliarInsumos tipo;
	
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
