package com.cafe.modelo.enums;

import java.util.EnumSet;

public class MaquinasPorDespesa {
	public EnumSet<TipoAuxiliarMaquinas> tratores = EnumSet.of(
			TipoAuxiliarMaquinas.COLHEDORA_AUTOMOTRIZ_CAFE,
			TipoAuxiliarMaquinas.COLHEDORA_CAFE, 
			TipoAuxiliarMaquinas.COLHEDORA_CAFE_AUTOMOTRIZ, 
			TipoAuxiliarMaquinas.MICROTRATOR,
			TipoAuxiliarMaquinas.TRATOR,
			TipoAuxiliarMaquinas.TRATOR_DE_RODA_PEQUENO_PORTE
	);
	
	public EnumSet<TipoAuxiliarMaquinas> naoTrator = EnumSet.of(
			TipoAuxiliarMaquinas.ATOMIZADOR_COSTAL_MOTORIZADO,
			TipoAuxiliarMaquinas.MOTORROÇADEIRA,
			TipoAuxiliarMaquinas.SOPRADOR,
			TipoAuxiliarMaquinas.ROÇADEIRA,
			TipoAuxiliarMaquinas.DERRIÇADORA,
			TipoAuxiliarMaquinas.MOTO_SERRA
	);
	
}
