package com.cafe.modelo;

import java.time.OffsetDateTime;
import java.util.List;

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
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cafe.modelo.enums.Medida;
import com.cafe.modelo.enums.TipoAuxiliarInsumos;
import com.cafe.modelo.enums.TipoInsumo;

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
		@NamedQuery(name = "Fertilizante.buscarFertilizantes", 
				query = "select u from Fertilizante u where u.tenant_id = :tenantId"),
		@NamedQuery(name = "Fertilizante.buscarFertilizantePorTipoInsumo", 
				query = "select u from Fertilizante u where u.tipoInsumo = :tipoInsumo and u.tenant_id = :tenantId")

})
public class Fertilizante {

	@ToString.Include
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
	
	@OneToMany(mappedBy = "fertilizante", cascade = CascadeType.ALL)
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
