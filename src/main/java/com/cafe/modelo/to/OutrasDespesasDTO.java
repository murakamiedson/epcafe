package com.cafe.modelo.to;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.cafe.modelo.enums.TipoDespesaCusteioOutras;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutrasDespesasDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private LocalDate data;
	private BigDecimal valorDespesa;
	private TipoDespesaCusteioOutras tipo;
	
	public OutrasDespesasDTO(
			LocalDate data,
			BigDecimal valorDespesa,
			TipoDespesaCusteioOutras tipo
			) {
		this.data = data;
		this.valorDespesa = valorDespesa;
		this.tipo = tipo;
	}
}
