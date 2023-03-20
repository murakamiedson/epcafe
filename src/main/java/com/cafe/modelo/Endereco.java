package com.cafe.modelo;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * @author murakamiadmin
 *
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@NamedQueries({
	@NamedQuery(name="Endereco.buscarTodos", query="select e from Endereco e")	
})
public class Endereco implements Cloneable, Serializable {

	private static final long serialVersionUID = 2060033487029113003L;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message="O endereco é obrigatório.")
	private String endereco;
	
	@NotNull(message="O número é obrigatório.")
	private Long numero;
	private String complemento;

	private String bairro;
	
	private String cep;
	
	private String municipio;
	
	@NotBlank(message="A UF é obrigatória.")
	private String uf;
	private String referencia;
	private String telefoneContato;
	
	@Override
	public Endereco clone() throws CloneNotSupportedException {
		return (Endereco) super.clone();
	}
	
	public String toString() {
		return endereco + ", " + numero + ". " + bairro + " - " + municipio + "/" + uf + ". CEP: " + cep;
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
