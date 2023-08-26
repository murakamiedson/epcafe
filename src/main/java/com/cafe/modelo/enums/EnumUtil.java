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
	private static final List<TipoAuxiliarInsumos> TIPOS_HERBICIDAS;
	private static final List<TipoAuxiliarInsumos> TIPOS_FUNGICIDAS;
	private static final List<TipoAuxiliarInsumos> TIPOS_INSETICIDAS;
	private static final List<TipoAuxiliarInsumos> TIPOS_ADJUVANTES;
	
	
	static {
	TIPOS_MAQUINAS = Arrays.asList(
					
					TipoAuxiliarInsumos.ABANADOR_CEREAL,
					TipoAuxiliarInsumos.ADUBADORA_AUTOPROPELIDA,
					TipoAuxiliarInsumos.APLICADOR_AUTOPROPELIDO,
					TipoAuxiliarInsumos.ATOMIZADOR_COSTAL_MOTORIZADO,
					TipoAuxiliarInsumos.CAMINHÃO,
					TipoAuxiliarInsumos.COLHEDORA_AUTOMOTRIZ_CAFE,
					TipoAuxiliarInsumos.COLHEDORA_CAFE,
					TipoAuxiliarInsumos.MICROTRATOR,
					TipoAuxiliarInsumos.MOTORROÇADEIRA,
					TipoAuxiliarInsumos.PA_CARREGADEIRA,
					TipoAuxiliarInsumos.PULVERIZADOR,
					TipoAuxiliarInsumos.ROÇADEIRA_MANUAL,
					TipoAuxiliarInsumos.SOPRADOR,
					TipoAuxiliarInsumos.TRATOR,
					TipoAuxiliarInsumos.TRATOR_DE_RODA_PEQUENO_PORTE,
					TipoAuxiliarInsumos.TRITURADOR,
					TipoAuxiliarInsumos.OUTROS
					);
	
		TIPOS_IMPLEMENTOS = Arrays.asList(
				TipoAuxiliarInsumos.ABANADOR_CAFE_LAVOURA,
				TipoAuxiliarInsumos.ABASTECEDOR_FERTILIZANTES,
				TipoAuxiliarInsumos.ABASTECEDOR_PULVERIZADOR,
				TipoAuxiliarInsumos.ADUBADEIRA_CITROS_CAFE,
				TipoAuxiliarInsumos.ADUBADEIRA_MANUAL,
				TipoAuxiliarInsumos.ADUBADOR_MECANICO,
				TipoAuxiliarInsumos.APLICADOR_ADUBO,
				TipoAuxiliarInsumos.APLICADOR_INSETICIDA,
				TipoAuxiliarInsumos.APLICADOR_LOCALIZADO_FERTILIZANTES,
				TipoAuxiliarInsumos.ARAÇAO,
				TipoAuxiliarInsumos.ARADO,
				TipoAuxiliarInsumos.ARADO_AIVECA,
				TipoAuxiliarInsumos.ARRUADOR_SOPRADOR_CAFE,
				TipoAuxiliarInsumos.BARRA_APLICADORA_HERBICIDA,
				TipoAuxiliarInsumos.BENEFICIADORA,
				TipoAuxiliarInsumos.BOMBA_DE_IRRIGAÇÃO,
				TipoAuxiliarInsumos.BOMBA_IRRIGAÇÃO,
				TipoAuxiliarInsumos.CAPINADEIRA,
				TipoAuxiliarInsumos.CARRETA_AGRICOLA,
				TipoAuxiliarInsumos.CARRETA_BASCULANTE,
				TipoAuxiliarInsumos.CARRETA_BASCULANTE_CAFE,
				TipoAuxiliarInsumos.CARRETA_BASCULANTE_METALICA,
				TipoAuxiliarInsumos.CARRETA_BASCULANTE_METALICA_MEDIO_PORTE,
				TipoAuxiliarInsumos.CARRETA_DISTRIBUIDORA_FERTILIZANTE_CALCARIO_ADUBO,
				TipoAuxiliarInsumos.CARRETA_DISTRIBUIDORA_FERTILIZANTES_CALCARIO,
				TipoAuxiliarInsumos.CARRETA_PULVERIZADORA,
				TipoAuxiliarInsumos.COLHEDORA_CAFE,
				TipoAuxiliarInsumos.COLHEDORA_CAFE_AUTOMOTRIZ,
				TipoAuxiliarInsumos.COLHEDORA_CAFE_ENGATE_TRATOR,
				TipoAuxiliarInsumos.CULTIVADOR,
				TipoAuxiliarInsumos.CULTIVADOR_SUBSOLADOR,
				TipoAuxiliarInsumos.DECOTADEIRA_RECEPADEIRA,
				TipoAuxiliarInsumos.DESCASCADOR_CAFE_ROÇA,
				TipoAuxiliarInsumos.DESMUCILADOR_CAFE,
				TipoAuxiliarInsumos.DISTRIBUIDOR_ADUBO_ORGANICO_CALCARIO,
				TipoAuxiliarInsumos.DISTRIBUIDOR_ADUBO_ORGANICO_LIQUIDO,
				TipoAuxiliarInsumos.DISTRIBUIDOR_FERTILIZANTE,
				TipoAuxiliarInsumos.DISTRIBUIDOR_FERTILIZANTE_CALCARIO,
				TipoAuxiliarInsumos.DISTRIBUIDOR_FERTILIZANTE_CORRETIVO,
				TipoAuxiliarInsumos.DISTRIBUIDOR_FERTILIZANTE_DISCO,
				TipoAuxiliarInsumos.DISTRIBUIDOR_FERTILIZANTE_SOLIDO,
				TipoAuxiliarInsumos.DISTRIBUIDOR_FERTILIZANTES_ORGANICOS,
				TipoAuxiliarInsumos.ENLEIRADEIRA_GRAO_CAFE,
				TipoAuxiliarInsumos.ENXADA_ROTATIVA,
				TipoAuxiliarInsumos.ENXADA_ROTATIVA_MEXEDOR_CAMA_AVIARIO,
				TipoAuxiliarInsumos.ESQUELETADEIRA_CAFE_ARRASTO_PELO_TRATOR,
				TipoAuxiliarInsumos.GRADE_ARADORA,
				TipoAuxiliarInsumos.GRADE_ARADORA_ARRASTO,
				TipoAuxiliarInsumos.GRADE_ARADORA_MECANICA,
				TipoAuxiliarInsumos.GRADE_NIVELADORA,
				TipoAuxiliarInsumos.GUINCHO_AGRICOLA,
				TipoAuxiliarInsumos.GUINCHO_AGRICOLA_TRASEIRO_REBOCAVEL,
				TipoAuxiliarInsumos.GUINCHO_BAG,
				TipoAuxiliarInsumos.GUINCHO_HIDRAULICO,
				TipoAuxiliarInsumos.GUINCHO_TRASEIRO,
				TipoAuxiliarInsumos.INCORPORADOR_FERTILIZANTE,
				TipoAuxiliarInsumos.LAMINA_ENLEIRADORA,
				TipoAuxiliarInsumos.LAVADOR_CAFE,
				TipoAuxiliarInsumos.MANEJO_SOLO_TRITURADOR,
				TipoAuxiliarInsumos.MAQUINA_PAPA_GALHO,
				TipoAuxiliarInsumos.PA_CARREGADEIRA,
				TipoAuxiliarInsumos.PA_CARREGADEIRA_TRASEIRA,
				TipoAuxiliarInsumos.PENEIRAO_POS_COLHEITA,
				TipoAuxiliarInsumos.PLANTADEIRA_ADUBADEIRA,
				TipoAuxiliarInsumos.PLANTADEIRA_CAFE,
				TipoAuxiliarInsumos.PLATAFORMA,
				TipoAuxiliarInsumos.PODADEIRA,
				TipoAuxiliarInsumos.PODADEIRA_HIDRAULICA,
				TipoAuxiliarInsumos.PULVERIZADOR,
				TipoAuxiliarInsumos.PULVERIZADOR_ACOPLADO,
				TipoAuxiliarInsumos.PULVERIZADOR_ARRASTO,
				TipoAuxiliarInsumos.PULVERIZADOR_CANHAO,
				TipoAuxiliarInsumos.PULVERIZADOR_COSTAL,
				TipoAuxiliarInsumos.PULVERIZADOR_COSTAL_AGRICOLA,
				TipoAuxiliarInsumos.PULVERIZADOR_HIDRAULICO,
				TipoAuxiliarInsumos.PULVERIZADOR_MANUAL,
				TipoAuxiliarInsumos.PULVERIZADOR_REBOCADO,
				TipoAuxiliarInsumos.PULVERIZADOR_TRACIONADO,
				TipoAuxiliarInsumos.PULVERIZADOR_TURBINA_FRUTICULTURA,
				TipoAuxiliarInsumos.PULVERIZADOR_TURBO_ATOMIZADOR,
				TipoAuxiliarInsumos.PULVERIZADORA_CANHAO,
				TipoAuxiliarInsumos.RASTELO_CAFE,
				TipoAuxiliarInsumos.RECOLHEDOR_CAFE_CHAO,
				TipoAuxiliarInsumos.RECOLHEDORA_CAFE,
				TipoAuxiliarInsumos.ROÇADEIRA,
				TipoAuxiliarInsumos.ROÇADEIRA_ARRASTO,
				TipoAuxiliarInsumos.ROÇADEIRA_HIDRAULICA,
				TipoAuxiliarInsumos.ROÇADEIRA_LATERAL,
				TipoAuxiliarInsumos.SOPRADOR_TRASEIRO_CAFE,
				TipoAuxiliarInsumos.SUBSOLADOR,
				TipoAuxiliarInsumos.SULCADOR,
				TipoAuxiliarInsumos.SULCADOR_ADUBADOR,
				TipoAuxiliarInsumos.SULCADOR_ADUBADOR_ABRIDOR_SULCO,
				TipoAuxiliarInsumos.SULCADOR_ADUBADOR_CANAVIEIRO,
				TipoAuxiliarInsumos.SULCADOR_AGRIC_FAMILIAR,
				TipoAuxiliarInsumos.SULCADOR_CANAVIEIRO,
				TipoAuxiliarInsumos.SULCADOR_TANDEM_CAFE,
				TipoAuxiliarInsumos.TRATOR_NORMAL_OU_CAFEEIRO,
				TipoAuxiliarInsumos.TRITURADOR_CITROS_CAFE,
				TipoAuxiliarInsumos.OUTROS

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
				
				TipoAuxiliarInsumos.ADJUVANTES,
				TipoAuxiliarInsumos.OUTROS
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
