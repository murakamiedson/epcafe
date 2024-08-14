package com.cafe.modelo.to;

import java.math.BigDecimal;

public interface DespesaAnualTO {
	
	BigDecimal getValorTotalJan();
    BigDecimal getValorTotalFev();
    BigDecimal getValorTotalMar();
    BigDecimal getValorTotalAbr();
    BigDecimal getValorTotalMai();
    BigDecimal getValorTotalJun();
    BigDecimal getValorTotalJul();
    BigDecimal getValorTotalAgo();
    BigDecimal getValorTotalSet();
    BigDecimal getValorTotalOut();
    BigDecimal getValorTotalNov();
    BigDecimal getValorTotalDez();
    BigDecimal getValorTotalAnual();
    
    void setValorTotalAnual(BigDecimal valorTotalAnual);
	void setValorTotalJan(BigDecimal valorDespesa);
	void setValorTotalFev(BigDecimal valorDespesa);
	void setValorTotalMar(BigDecimal valorDespesa);
	void setValorTotalAbr(BigDecimal valorDespesa);
	void setValorTotalMai(BigDecimal valorDespesa);
	void setValorTotalJun(BigDecimal valorDespesa);
	void setValorTotalJul(BigDecimal valorDespesa);
	void setValorTotalAgo(BigDecimal valorDespesa);
	void setValorTotalSet(BigDecimal valorDespesa);
	void setValorTotalOut(BigDecimal valorDespesa);
	void setValorTotalNov(BigDecimal valorDespesa);
	void setValorTotalDez(BigDecimal valorDespesa);
}
