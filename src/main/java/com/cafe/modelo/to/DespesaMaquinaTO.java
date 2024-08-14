package com.cafe.modelo.to;

import java.time.LocalDate;

import com.cafe.modelo.enums.TipoCombustivel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DespesaMaquinaTO {
	
	private TotalDespesaTO totaisMensais;
	
	private LocalDate mesAno;
	private TipoCombustivel tipoCombustivel;
	private Long maquinaId;
	private String maquinaNome;

}
