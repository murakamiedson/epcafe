package com.cafe.modelo.to;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.cafe.modelo.enums.TipoCombustivel;
import com.cafe.service.DespesaMaquinaService;

public class DespesaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private LocalDate mesAno;
	private Long maquinaId;
	private String maquinaNome;
	private BigDecimal valorTotal;
	private TipoCombustivel tipoCombustivel;
		
	private DespesaMaquinaService despesaService;
	
	private List<DespesaDTO> listaJunho = despesaService.buscarDespesasTO(LocalDate.of(2023, 6, 1), LocalDate.of(2023, 6, 30));
	
	public DespesaDTO() {}

	public DespesaDTO(LocalDate mesAno, Long maquinaId, String maquinaNome, BigDecimal valorTotal,
			TipoCombustivel tipoCombustivel) {
		
		this.mesAno = mesAno;
		this.maquinaId = maquinaId;
		this.maquinaNome = maquinaNome;
		this.valorTotal = valorTotal;
		this.tipoCombustivel = tipoCombustivel;
	}
	
	
	public void leLista() {
		
		this.maquinaId = listaJunho.get(0).maquinaId;
		BigDecimal total = new BigDecimal(0);
		
		BigDecimal totalMaquina = new BigDecimal(0);
		
		for(DespesaDTO dto : listaJunho) {
			
			if(maquinaId == dto.maquinaId) {
				totalMaquina = totalMaquina.add(dto.valorTotal);
			}
			else {
				DespesaTO to = new DespesaTO();
				to.setMaquina(dto.maquinaId);
				total = total.add(totalMaquina);
				to.setValorTotalJun(total);
				
				
				
			}
			totalMaquina = new BigDecimal(0);
		}
	}
	
	
	
	
}
