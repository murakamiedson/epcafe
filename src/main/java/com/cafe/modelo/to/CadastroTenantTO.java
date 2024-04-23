package com.cafe.modelo.to;

import com.cafe.modelo.Unidade;
import com.cafe.modelo.Tenant;
import com.cafe.modelo.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroTenantTO {
	
	private Tenant proprietario;
	private Unidade unidade;
	private Usuario usuario;
}
