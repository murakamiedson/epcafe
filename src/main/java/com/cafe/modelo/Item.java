package com.cafe.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.PositiveOrZero;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
public class Item implements Serializable{

	private static final long serialVersionUID = 1L;

	@ToString.Include
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@PositiveOrZero
	private BigDecimal quantidade;
	
	@PositiveOrZero
	private BigDecimal valor = new BigDecimal(0);
	
	@ToString.Include
	@EqualsAndHashCode.Include
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "codigo_fertilizante")
	private Fertilizante fertilizante;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name= "codigo_nota")
	private NotaFiscal notaFiscal;
	
}
