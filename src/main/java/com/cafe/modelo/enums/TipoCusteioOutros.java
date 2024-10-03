package com.cafe.modelo.enums;

public enum TipoCusteioOutros {
	// 1 = Outras despesas de custeio
	OPERACAO_ANIMAL(1),
	OPERACAO_AVIAO(1),
	ALUGUEL_MAQUINAS(1),
	ALUGUEL_ANIMAIS(1),
	MAO_DE_OBRA(1),
	ADMINISTRADOR(1),
	SEMENTES_E_MUDAS(1),
	EMBALAGENS_UTENSILIOS(1),
	ANALISE_DE_SOLO(1),
	DEMAIS_DESPESAS(1),
	SERVICOS_DIVERSOS(1),
	// Outras despesas
	TRANSPORTE_EXTERNO(0),
	DESPESAS_ADMINISTRATIVAS(0),
	DESPESAS_DE_ARMAZENAGEM(0),
	BENEFICIAMENTO(0),
	SEGURO_PRODUCAO(0),
	SEGURO_CREDITO(0),
	ASSISTENCIA_TECNICA(0),
	CLASSIFICAÇÃO(0),
	CESSR(0),
	OUTROS(0);
	
	private final int valor;
	 
    private TipoCusteioOutros(int valor) {
        this.valor = valor;
    }

    public int getValor() {
    	//BigDecimal valorFinal = 
        return this.valor;
    }
}
