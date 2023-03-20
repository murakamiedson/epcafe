package com.cafe.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author murakamiadmin
 *
 */
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@NamedQueries({
	@NamedQuery(name="Pais.buscarTodosPaises", query="select p from Pais p ORDER BY p.pais"),
	@NamedQuery(name="Pais.buscarPorContinente", query="select p from Pais p where p.continente = :continente ORDER BY p.pais")
	
})
public class Pais implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@ToString.Include
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;		//ISO-3166 numeric
	
	private String fips; 		//Federal Information Processing Standard (geocodigo)
	private String pais;		//Country
	private String capital;		//Capital
	private Long area;			//Area em km2
	private Long populacao;		//Population
	private String continente;	//continent	
}
