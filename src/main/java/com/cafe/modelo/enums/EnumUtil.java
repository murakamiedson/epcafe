package com.cafe.modelo.enums;


import java.util.Arrays;
import java.util.List;

/**
 * @author isabella
 *
 */

public class EnumUtil {
	
	private static final List<TipoAuxiliarMaquinas> TIPOS_MAQUINAS;
	private static final List<TipoAuxiliarMaquinas> TIPOS_IMPLEMENTOS;
	private static final List<TipoAuxiliarInsumos> TIPOS_FERTILIZANTES;
	private static final List<TipoAuxiliarInsumos> TIPOS_HERBICIDAS;
	private static final List<TipoAuxiliarInsumos> TIPOS_FUNGICIDAS;
	private static final List<TipoAuxiliarInsumos> TIPOS_INSETICIDAS;
	private static final List<TipoAuxiliarInsumos> TIPOS_ADJUVANTES;
	
	
	static {
	TIPOS_MAQUINAS = Arrays.asList(
					
					TipoAuxiliarMaquinas.ABANADOR_CEREAL,
					TipoAuxiliarMaquinas.ADUBADORA_AUTOPROPELIDA,
					TipoAuxiliarMaquinas.APLICADOR_AUTOPROPELIDO,
					TipoAuxiliarMaquinas.ATOMIZADOR_COSTAL_MOTORIZADO,
					TipoAuxiliarMaquinas.CAMINHÃO,
					TipoAuxiliarMaquinas.COLHEDORA_AUTOMOTRIZ_CAFE,
					TipoAuxiliarMaquinas.COLHEDORA_CAFE,
					TipoAuxiliarMaquinas.MICROTRATOR,
					TipoAuxiliarMaquinas.MOTORROÇADEIRA,
					TipoAuxiliarMaquinas.PA_CARREGADEIRA,
					TipoAuxiliarMaquinas.PULVERIZADOR,
					TipoAuxiliarMaquinas.ROÇADEIRA_MANUAL,
					TipoAuxiliarMaquinas.SOPRADOR,
					TipoAuxiliarMaquinas.TRATOR,
					TipoAuxiliarMaquinas.TRATOR_DE_RODA_PEQUENO_PORTE,
					TipoAuxiliarMaquinas.TRITURADOR,
					TipoAuxiliarMaquinas.OUTROS
					);
	
		TIPOS_IMPLEMENTOS = Arrays.asList(
				TipoAuxiliarMaquinas.ABASTECEDOR_FERTILIZANTES,
				TipoAuxiliarMaquinas.ABASTECEDOR_PULVERIZADOR,
				TipoAuxiliarMaquinas.ADUBADEIRA_CITROS_CAFE,
				TipoAuxiliarMaquinas.ADUBADEIRA_MANUAL,
				TipoAuxiliarMaquinas.APLICADOR_ADUBO,
				TipoAuxiliarMaquinas.APLICADOR_INSETICIDA,
				TipoAuxiliarMaquinas.APLICADOR_LOCALIZADO_FERTILIZANTES,
				TipoAuxiliarMaquinas.ARADO,
				TipoAuxiliarMaquinas.ARADO_AIVECA,
				TipoAuxiliarMaquinas.BARRA_APLICADORA_HERBICIDA,
				TipoAuxiliarMaquinas.BOMBA_IRRIGAÇÃO,
				TipoAuxiliarMaquinas.CAPINADEIRA,
				TipoAuxiliarMaquinas.CARRETA_AGRICOLA,
				TipoAuxiliarMaquinas.CARRETA_BASCULANTE_CAFE,
				TipoAuxiliarMaquinas.CARRETA_BASCULANTE_METALICA,
				TipoAuxiliarMaquinas.CARRETA_BASCULANTE_METALICA_MEDIO_PORTE,
				TipoAuxiliarMaquinas.CARRETA_DISTRIBUIDORA_FERTILIZANTE_CALCARIO_ADUBO,
				TipoAuxiliarMaquinas.CARRETA_PULVERIZADORA,
				TipoAuxiliarMaquinas.COLHEDORA_CAFE,
				TipoAuxiliarMaquinas.COLHEDORA_CAFE_AUTOMOTRIZ,
				TipoAuxiliarMaquinas.CULTIVADOR,
				TipoAuxiliarMaquinas.CULTIVADOR_SUBSOLADOR,
				TipoAuxiliarMaquinas.DECOTADEIRA_RECEPADEIRA,
				TipoAuxiliarMaquinas.DISTRIBUIDOR_ADUBO_ORGANICO_CALCARIO,
				TipoAuxiliarMaquinas.DISTRIBUIDOR_ADUBO_ORGANICO_LIQUIDO,
				TipoAuxiliarMaquinas.DISTRIBUIDOR_FERTILIZANTE,
				TipoAuxiliarMaquinas.DISTRIBUIDOR_FERTILIZANTE_CALCARIO,
				TipoAuxiliarMaquinas.DISTRIBUIDOR_FERTILIZANTE_CORRETIVO,
				TipoAuxiliarMaquinas.DISTRIBUIDOR_FERTILIZANTE_DISCO,
				TipoAuxiliarMaquinas.DISTRIBUIDOR_FERTILIZANTE_SOLIDO,
				TipoAuxiliarMaquinas.DISTRIBUIDOR_FERTILIZANTES_ORGANICOS,
				TipoAuxiliarMaquinas.ENLEIRADEIRA_GRAO_CAFE,
				TipoAuxiliarMaquinas.ENXADA_ROTATIVA,
				TipoAuxiliarMaquinas.ENXADA_ROTATIVA_MEXEDOR_CAMA_AVIARIO,
				TipoAuxiliarMaquinas.GRADE_ARADORA,
				TipoAuxiliarMaquinas.GRADE_ARADORA_ARRASTO,
				TipoAuxiliarMaquinas.GRADE_ARADORA_MECANICA,
				TipoAuxiliarMaquinas.GRADE_NIVELADORA,
				TipoAuxiliarMaquinas.GUINCHO_AGRICOLA,
				TipoAuxiliarMaquinas.GUINCHO_AGRICOLA_TRASEIRO_REBOCAVEL,
				TipoAuxiliarMaquinas.GUINCHO_BAG,
				TipoAuxiliarMaquinas.GUINCHO_HIDRAULICO,
				TipoAuxiliarMaquinas.GUINCHO_TRASEIRO,
				TipoAuxiliarMaquinas.INCORPORADOR_FERTILIZANTE,
				TipoAuxiliarMaquinas.LAMINA_ENLEIRADORA,
				TipoAuxiliarMaquinas.MANEJO_SOLO_TRITURADOR,
				TipoAuxiliarMaquinas.PA_CARREGADEIRA,
				TipoAuxiliarMaquinas.PA_CARREGADEIRA_TRASEIRA,
				TipoAuxiliarMaquinas.PLANTADEIRA_ADUBADEIRA,
				TipoAuxiliarMaquinas.PLANTADEIRA_CAFE,
				TipoAuxiliarMaquinas.PLATAFORMA,
				TipoAuxiliarMaquinas.PODADEIRA,
				TipoAuxiliarMaquinas.PODADEIRA_HIDRAULICA,
				TipoAuxiliarMaquinas.PULVERIZADOR,
				TipoAuxiliarMaquinas.PULVERIZADOR_ACOPLADO,
				TipoAuxiliarMaquinas.PULVERIZADOR_ARRASTO,
				TipoAuxiliarMaquinas.PULVERIZADOR_CANHAO,
				TipoAuxiliarMaquinas.PULVERIZADOR_COSTAL,
				TipoAuxiliarMaquinas.PULVERIZADOR_HIDRAULICO,
				TipoAuxiliarMaquinas.PULVERIZADOR_MANUAL,
				TipoAuxiliarMaquinas.PULVERIZADOR_REBOCADO,
				TipoAuxiliarMaquinas.PULVERIZADOR_TRACIONADO,
				TipoAuxiliarMaquinas.PULVERIZADOR_TURBINA_FRUTICULTURA,
				TipoAuxiliarMaquinas.PULVERIZADOR_TURBO_ATOMIZADOR,
				TipoAuxiliarMaquinas.PULVERIZADORA_CANHAO,
				TipoAuxiliarMaquinas.RASTELO_CAFE,
				TipoAuxiliarMaquinas.RECOLHEDORA_CAFE,
				TipoAuxiliarMaquinas.ROÇADEIRA,
				TipoAuxiliarMaquinas.ROÇADEIRA_ARRASTO,
				TipoAuxiliarMaquinas.ROÇADEIRA_HIDRAULICA,
				TipoAuxiliarMaquinas.ROÇADEIRA_LATERAL,
				TipoAuxiliarMaquinas.SOPRADOR_TRASEIRO_CAFE,
				TipoAuxiliarMaquinas.SUBSOLADOR,
				TipoAuxiliarMaquinas.SULCADOR,
				TipoAuxiliarMaquinas.SULCADOR_ADUBADOR,
				TipoAuxiliarMaquinas.SULCADOR_ADUBADOR_ABRIDOR_SULCO,
				TipoAuxiliarMaquinas.SULCADOR_ADUBADOR_CANAVIEIRO,
				TipoAuxiliarMaquinas.SULCADOR_CANAVIEIRO,
				TipoAuxiliarMaquinas.SULCADOR_TANDEM_CAFE,
				TipoAuxiliarMaquinas.TRITURADOR_CITROS_CAFE,
				TipoAuxiliarMaquinas.OUTROS

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
				TipoAuxiliarInsumos.NPK_30_00_20,
				TipoAuxiliarInsumos.OUTROS
				);
				
		TIPOS_HERBICIDAS = Arrays.asList(
				TipoAuxiliarInsumos.GOAL_BR_EC_CONCENTRADO_EMULSIONAVEL,
				TipoAuxiliarInsumos.FLUMYZIN_500_WP,
				TipoAuxiliarInsumos.DIURON_NORTOX_800_WP_PO_MOLHAVEL,
				TipoAuxiliarInsumos.ALION_500_SC,
				TipoAuxiliarInsumos.SULFENTRAZONE_500_SC_P,
				TipoAuxiliarInsumos.SELECT_240_EC,
				TipoAuxiliarInsumos.HEAT_700_WG,
				TipoAuxiliarInsumos.ACCURATE_600_WG,
				TipoAuxiliarInsumos.CLORIMURON_MASTER_250_WG,
				TipoAuxiliarInsumos.AURORA_400_EC,
				TipoAuxiliarInsumos.GLIFOSATO_NORTOX_480_CONCENTRADO_SOLUVEL_SL,
				TipoAuxiliarInsumos.OUTROS

				);
		
		TIPOS_FUNGICIDAS = Arrays.asList(
				TipoAuxiliarInsumos.ALIETTE_WP,
				TipoAuxiliarInsumos.ALTO_100_CONCENTRADO_SOLUVEL_SL,
				TipoAuxiliarInsumos.AMISTAR_WG_ESTROBIRULINA,
				TipoAuxiliarInsumos.APROACH_POWER_CONCENTRADO_EMULSIONAVEL,
				TipoAuxiliarInsumos.APROACH_PRIMA_SC,
				TipoAuxiliarInsumos.BIO_IMUNE_SC,
				TipoAuxiliarInsumos.CANTUS_WG_VIVEIRO,
				TipoAuxiliarInsumos.COMET_EC_ESTROBIRULINA,
				TipoAuxiliarInsumos.CUPROGARD_350_PO_MOLHAVEL_WP,
				TipoAuxiliarInsumos.CUPROZEB_WP_DITIOCARBAMATO,
				TipoAuxiliarInsumos.ELITE_200_EC,
				TipoAuxiliarInsumos.FEGATEX_SL,
				TipoAuxiliarInsumos.FLEXIN_SC,
				TipoAuxiliarInsumos.FOLICUR_200_EC,
				TipoAuxiliarInsumos.GARANT_BR_WP_VIVEIRO,
				TipoAuxiliarInsumos.IMPACT_125_SC,
				TipoAuxiliarInsumos.KASUMIN,
				TipoAuxiliarInsumos.NATIVO_SC_TRIFLOXISTROBINA,
				TipoAuxiliarInsumos.OPERA_SUSPOEMULSAO_SE,
				TipoAuxiliarInsumos.ORKESTRA_SC,
				TipoAuxiliarInsumos.PREMIER_PLUS_SC,
				TipoAuxiliarInsumos.PRIORI_TOP_SC,
				TipoAuxiliarInsumos.PRIORI_XTRA_SC,
				TipoAuxiliarInsumos.RECOP_WP,
				TipoAuxiliarInsumos.ROVRAL_SC,
				TipoAuxiliarInsumos.ROVRAL_WP,
				TipoAuxiliarInsumos.SERENADE,
				TipoAuxiliarInsumos.SIMBOLL_125_SC,
				TipoAuxiliarInsumos.SPHERE_MAX_SC,
				TipoAuxiliarInsumos.TENAZ_250_SC,
				TipoAuxiliarInsumos.VERDADERO_600_WG,
				TipoAuxiliarInsumos.OUTROS


				);
		
		TIPOS_INSETICIDAS = Arrays.asList(
				TipoAuxiliarInsumos.VERISMO_SC,
				TipoAuxiliarInsumos.SPERTO_WG,
				TipoAuxiliarInsumos.VOLIAM_TARGO_SC,
				TipoAuxiliarInsumos.BENEVIA_DISPERSAO_OLEO_OD,
				TipoAuxiliarInsumos.DURIVO_SC,
				TipoAuxiliarInsumos.ENVIDOR_SC,
				TipoAuxiliarInsumos.OBERON_240_SC,
				TipoAuxiliarInsumos.ABAMECTIN_NORTOX_EC,
				TipoAuxiliarInsumos.VERTIMEC_18_EC,
				TipoAuxiliarInsumos.TURBO_EC,
				TipoAuxiliarInsumos.TALENTO_500_WP,
				TipoAuxiliarInsumos.ALTACOR_350_WG,
				TipoAuxiliarInsumos.POLYTRIN_EC,
				TipoAuxiliarInsumos.ASSIST,
				TipoAuxiliarInsumos.NIMBUS_IHAROL_ASSIST,
				TipoAuxiliarInsumos.AGRAL_NONIL_FENOXI_POLI_ETANOL,
				TipoAuxiliarInsumos.OUTROS


				);
		
		TIPOS_ADJUVANTES = Arrays.asList(
				
				TipoAuxiliarInsumos.MINERAL,
				TipoAuxiliarInsumos.VEGETAL
				);
	}
	
	public static List<TipoAuxiliarMaquinas> getTiposMaquinas() {
		return TIPOS_MAQUINAS;
	}
	
	public static List<TipoAuxiliarMaquinas> getTiposImplementos() {
		return TIPOS_IMPLEMENTOS;
	}
	
	public static List<TipoAuxiliarInsumos> getTiposFertilizantes() {
		return TIPOS_FERTILIZANTES;
	}
	
	public static List<TipoAuxiliarInsumos> getTiposHerbicidas() {
		return TIPOS_HERBICIDAS;
	}
	
	public static List<TipoAuxiliarInsumos> getTiposFungicidas() {
		return TIPOS_FUNGICIDAS;
	}
	
	public static List<TipoAuxiliarInsumos> getTiposInseticidas() {
		return TIPOS_INSETICIDAS;
	}
	
	public static List<TipoAuxiliarInsumos> getTiposAdjuvantes() {
		return TIPOS_ADJUVANTES;
	}
}
