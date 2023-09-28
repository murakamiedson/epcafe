package com.cafe.modelo.to;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.cafe.modelo.enums.TipoCombustivel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DespesaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private LocalDate mesAno;
	private Long maquinaId;
	private String maquinaNome;
	private BigDecimal valorTotal;
	private TipoCombustivel tipoCombustivel;

	/*  os atributos do construtor e da projeção devem estar na mesma ordem
	    + "d.mesAno, "
		+ "d.valorTotal, "
		+ "m.id, "
		+ "m.nome, "
		+ "m.tipoCombustivel) "
	 */
	public DespesaDTO(
			LocalDate mesAno,
			BigDecimal valorTotal,
			Long maquinaId, 
			String maquinaNome,			
			TipoCombustivel tipoCombustivel) {
		
		this.mesAno = mesAno;
		this.maquinaId = maquinaId;
		this.maquinaNome = maquinaNome;
		this.valorTotal = valorTotal;
		this.tipoCombustivel = tipoCombustivel;
	}
}
