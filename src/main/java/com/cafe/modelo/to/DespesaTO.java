package com.cafe.modelo.to;

import java.math.BigDecimal;

import com.cafe.modelo.Maquina;
import com.cafe.modelo.enums.TipoCombustivel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DespesaTO {
	
	private BigDecimal valorTotalJan;
	private BigDecimal valorTotalFev;
	private BigDecimal valorTotalMar;
	private BigDecimal valorTotalAbr;
	private BigDecimal valorTotalMai;
	private BigDecimal valorTotalJun;
	private BigDecimal valorTotalJul;
	private BigDecimal valorTotalAgo;
	private BigDecimal valorTotalSet;
	private BigDecimal valorTotalOut;
	private BigDecimal valorTotalNov;
	private BigDecimal valorTotalDez;
	
	private TipoCombustivel tipoCombustivel;
	private Long maquina;
	
	
	
	
	
}
