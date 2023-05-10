package com.cafe.modelo.enums;


import java.util.Arrays;
import java.util.List;

/**
 * @author isabella
 *
 */

public class EnumUtil {
	
	private static final List<TipoAuxiliarInsumos> TIPOS_MAQUINAS;
	private static final List<TipoAuxiliarInsumos> TIPOS_IMPLEMENTOS;
	private static final List<TipoAuxiliarInsumos> TIPOS_FERTILIZANTES;
	private static final List<TipoAuxiliarInsumos> TIPOS_DEFENSIVOS;
	
	
	static {
	TIPOS_MAQUINAS = Arrays.asList(
					
					TipoAuxiliarInsumos.CULTIVADOR_MOTORIZADO_GRAOS_CEREAIS,
					TipoAuxiliarInsumos.DESMUCILADOR,
					TipoAuxiliarInsumos.DEBULHADEIRA,
					TipoAuxiliarInsumos.DERRICADEIRA,
					TipoAuxiliarInsumos.DESINTEGRADOR,
					TipoAuxiliarInsumos.DESPOLPADOR,
					TipoAuxiliarInsumos.ENSILADEIRA,
					TipoAuxiliarInsumos.GRADE,
					TipoAuxiliarInsumos.PODADOR,
					TipoAuxiliarInsumos.RECOLHEDOR,
					TipoAuxiliarInsumos.ROCADEIRA,
					TipoAuxiliarInsumos.ROCADEIRA_TRATORIZADA,
					TipoAuxiliarInsumos.SECADOR_ESTATICO,
					TipoAuxiliarInsumos.SECADOR_HORIZONTAL
					);
	
		TIPOS_IMPLEMENTOS = Arrays.asList(
				TipoAuxiliarInsumos.ABANADOR_CEREAL,
				TipoAuxiliarInsumos.ADUBADORA_AUTOPROPELIDA,
				TipoAuxiliarInsumos.APLICADOR_AUTOPROPELIDO,
				TipoAuxiliarInsumos.ARRUADORA,
				TipoAuxiliarInsumos.ATOMIZADOR_COSTAL_MOTORIZADO,
				TipoAuxiliarInsumos.BATEDEIRA_CEREAIS,
				TipoAuxiliarInsumos.BENEFICIADORA,
				TipoAuxiliarInsumos.CAMINHAO,
				TipoAuxiliarInsumos.CANHAO_PULVERIZACAO,
				TipoAuxiliarInsumos.CAPINADEIRA,
				TipoAuxiliarInsumos.CARREGADORA_AGRICOLA_CANA,
				TipoAuxiliarInsumos.CARRETA,
				TipoAuxiliarInsumos.COLHEDORA,
				TipoAuxiliarInsumos.COLHEDORA_AUTOPROPELIDA_MINICEIFA,
				TipoAuxiliarInsumos.SECADOR_LEITO_FIXO,
				TipoAuxiliarInsumos.SEPARADOR_HIDRAULICO_CAFE,
				TipoAuxiliarInsumos.SOPRADOR,
				TipoAuxiliarInsumos.SUBSOLADOR,
				TipoAuxiliarInsumos.TRATOR,
				TipoAuxiliarInsumos.TRINCHA,
				TipoAuxiliarInsumos.TRITURADORES
				);
		
		TIPOS_FERTILIZANTES = Arrays.asList(
				
				TipoAuxiliarInsumos.ACIDO_BORICO,
				TipoAuxiliarInsumos.BORAX,
				TipoAuxiliarInsumos.CALCARIO,
				TipoAuxiliarInsumos.CLORETO_DE_POTASSIO,
				TipoAuxiliarInsumos.FOSFATO_DIAMONICO_DAP,
				TipoAuxiliarInsumos.FOSFATO_MONOAMONICO_MAP,
				TipoAuxiliarInsumos.FOSFATO_NATURAL,
				TipoAuxiliarInsumos.NITRATO_DE_AMONIO,
				TipoAuxiliarInsumos.NITRATO_DE_CALCIO,
				TipoAuxiliarInsumos.OXIDO_CUPROSO,
				TipoAuxiliarInsumos.SULFATO_DE_AMONIO,
				TipoAuxiliarInsumos.SULFATO_DE_CALCIO,
				TipoAuxiliarInsumos.SULFATO_DE_COBRE,
				TipoAuxiliarInsumos.SULFATO_DE_ZINCO,
				TipoAuxiliarInsumos.SULFATO_MANGANOSO,
				TipoAuxiliarInsumos.SUPER_FOSFATO_SIMPLES,
				TipoAuxiliarInsumos.SUPERFOSFATO_DUPLO,
				TipoAuxiliarInsumos.SUPERFOSFATO_TRIPLO,
				TipoAuxiliarInsumos.TERMOFOSFATO,
				TipoAuxiliarInsumos.UREIA,
				TipoAuxiliarInsumos.UREIA_FORMALDEIDO,
				TipoAuxiliarInsumos.NPK_10_10_10,
				TipoAuxiliarInsumos.NPK_20_00_20,
				TipoAuxiliarInsumos.NPK_20_05_20,
				TipoAuxiliarInsumos.NPK_30_00_10,
				TipoAuxiliarInsumos.NPK_30_00_20
				);
				
		TIPOS_DEFENSIVOS = Arrays.asList(
				TipoAuxiliarInsumos.DEFENSIVO
				);
	}
	
	public static List<TipoAuxiliarInsumos> getTiposMaquinas() {
		return TIPOS_MAQUINAS;
	}
	
	public static List<TipoAuxiliarInsumos> getTiposImplementos() {
		return TIPOS_IMPLEMENTOS;
	}
	
	public static List<TipoAuxiliarInsumos> getTiposFertilizantes() {
		return TIPOS_FERTILIZANTES;
	}
	
	public static List<TipoAuxiliarInsumos> getTiposDefensivos() {
		return TIPOS_DEFENSIVOS;
	}
}
