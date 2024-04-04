package com.cafe.modelo;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cafe.modelo.enums.Grupo;
import com.cafe.modelo.enums.Role;
import com.cafe.modelo.enums.Status;

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
	// único que é busca geral - usado no login para identificar o tenant
	@NamedQuery(name="Usuario.buscarPorEmail", query="select u from Usuario u "
			+ "where u.email = :email"),
	
	@NamedQuery(name="Usuario.buscarTodos", query="select u from Usuario u where u.tenant.codigo = :tenantId"),
	@NamedQuery(name="Usuario.buscarTodosPorUnidade", query="select u from Usuario u "
			+ "where u.propriedade = :unidade "
			+ "and u.tenant.codigo = :tenantId"),	
	@NamedQuery(name="Usuario.buscarTodosFiltro", query="select u from Usuario u "
			+ "where u.propriedade = :unidade "
			+ "and u.tenant.codigo = :tenantId "
			+ "and u.nome LIKE :nome "),
	@NamedQuery(name="Usuario.buscarTodosFiltro2", query="select u from Usuario u "
			+ "where u.nome LIKE :nome "
			+ "and u.tenant.codigo = :tenantId"),		
	@NamedQuery(name="Usuario.testarCredencial", query="select u from Usuario u "
			+ "where u.email = :email "
			+ "and u.tenant.codigo = :tenantId"),
	
	/* apenas usuarios Ativos */
	
	@NamedQuery(name="Usuario.buscarTecnicos", query="select u from Usuario u "
			+ "where u.propriedade = :unidade "
			+ "and u.tenant.codigo = :tenantId "
			+ "and u.status = :status " 
			+ "order by u.nome"),
	@NamedQuery(name="Usuario.buscarTecnicosRole", query="select u from Usuario u "
			+ "where u.role = :role "
			+ "and u.propriedade = :unidade "
			+ "and u.tenant.codigo = :tenantId "
			+ "and u.status = :status "
			+ "order by u.nome"),
	@NamedQuery(name="Usuario.buscarUsuarios", query="select u from Usuario u "
			+ "where u.propriedade = :unidade "
			+ "and u.tenant.codigo = :tenantId "
			+ "and u.status = :status "			
			+ "order by u.nome"),  
	
	/* count ativos */
	
	@NamedQuery(name="Usuario.buscarTotalTecnicos", query="select count(u) from Usuario u "
			+ "where u.tenant.codigo = :tenantId "
			+ "and u.status = :status"),
	@NamedQuery(name="Usuario.buscarTotalTecnicosUnid", query="select count(u) from Usuario u "
			+ "where u.propriedade = :unidade "
			+ "and u.tenant.codigo = :tenantId " 
			+ "and u.status = :status")
})
public class Usuario implements Serializable {

	private static final long serialVersionUID = 82375949344894033L;
	
	@EqualsAndHashCode.Include
	@ToString.Include
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message="O nome é obrigatório")
	private String nome;
	
	private String registroProfissional;
	
	@Email
	@Column(unique=true)
	private String email;
	
	private String senha;
	
	private Integer theme;

	@Enumerated(EnumType.STRING)
	private Grupo grupo;
	
	@Enumerated(EnumType.STRING)
	private Role role;	
	
	@Enumerated(EnumType.STRING)
	private Status status;	

	@ManyToOne
	@JoinColumn(name="codigo_unidade")
	private Propriedade propriedade;
	
	@ManyToOne
	@JoinColumn(name="tenant_id")
	private Tenant tenant;
	
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
