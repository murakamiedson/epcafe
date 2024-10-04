package com.cafe.modelo.to;

import com.cafe.modelo.enums.TipoDespesaCusteioOutras;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutrasDespesasTO {
	
	private TotalDespesaTO totaisMensais;
	private TipoDespesaCusteioOutras tipo;

}
