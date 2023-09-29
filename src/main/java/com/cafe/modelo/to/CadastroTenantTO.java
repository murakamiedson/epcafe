package com.cafe.modelo.to;

import com.cafe.modelo.Propriedade;
import com.cafe.modelo.Tenant;
import com.cafe.modelo.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroTenantTO {
	
	private Tenant proprietario;
	private Propriedade propriedade;
	private Usuario usuario;
}
