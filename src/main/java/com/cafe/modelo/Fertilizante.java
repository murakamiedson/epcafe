package com.cafe.modelo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotBlank;

import com.cafe.modelo.enums.TipoInsumo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@NamedQueries({
		@NamedQuery(name = "Fertilizante.buscarFertilizantes", 
				query = "select u from Fertilizante u"),
		@NamedQuery(name = "Fertilizante.buscarFertilizantePorTipo",
				query="select u from Fertilizante u where u.tipo = :tipo")

})
public class Fertilizante {

	@ToString.Include
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nome;

	@Enumerated(EnumType.STRING)
	private TipoInsumo tipo;
	
}
/*
INSERT INTO fertilizante (nome, tipo) VALUES 
("Acido Borico" , "Fertilizante"),
("Borax" , "Fertilizante"),
("Calcario" , "Fertilizante"),
("Cloreto De Potassio" , "Fertilizante"),
("Fosfato Diamonico Dap" , "Fertilizante"),
("Fosfato Monoamonico Map" , "Fertilizante"),
("Fosfato Natural" , "Fertilizante"),
("Nitrato De Amonio" , "Fertilizante"),
("Nitrato De Calcio" , "Fertilizante"),
("Oxido Cuproso" , "Fertilizante"),
("Sulfato De Amonio" , "Fertilizante"),
("Sulfato De Calcio" , "Fertilizante"),
("Sulfato De Cobre" , "Fertilizante"),
("Sulfato De Zinco" , "Fertilizante"),
("Sulfato Manganoso" , "Fertilizante"),
("Super Fosfato Simples" , "Fertilizante"),
("Superfosfato Duplo" , "Fertilizante"),
("Superfosfato Triplo" , "Fertilizante"),
("Termofosfato" , "Fertilizante"),
("Ureia" , "Fertilizante"),
("Ureia Formaldeido" , "Fertilizante"),
("Npk 10 10 10" , "Fertilizante"),
("Npk 20 00 20" , "Fertilizante"),
("Npk 20 05 20" , "Fertilizante"),
("Npk 30 00 10" , "Fertilizante"),
("Npk 30 00 20" , "Fertilizante"),
("Aliette Wp" , "Fungicida"),
("Alto 100 Concentrado Soluvel Sl" , "Fungicida"),
("Amistar Wg Estrobirulina" , "Fungicida"),
("Aproach Power Concentrado Emulsionavel" , "Fungicida"),
("Aproach Prima Sc" , "Fungicida"),
("Bio Imune Sc" , "Fungicida"),
("Cantus Wg Viveiro" , "Fungicida"),
("Comet Ec Estrobirulina" , "Fungicida"),
("Cuprogard 350 Po Molhavel Wp" , "Fungicida"),
("Cuprozeb Wp Ditiocarbamato" , "Fungicida"),
("Elite 200 Ec" , "Fungicida"),
("Fegatex Sl" , "Fungicida"),
("Flexin Sc" , "Fungicida"),
("Folicur 200 Ec" , "Fungicida"),
("Garant Br Wp Viveiro" , "Fungicida"),
("Impact 125 Sc" , "Fungicida"),
("Kasumin" , "Fungicida"),
("Nativo Sc Trifloxistrobina" , "Fungicida"),
("Opera Suspoemulsao Se" , "Fungicida"),
("Orkestra Sc" , "Fungicida"),
("Premier Plus Sc" , "Fungicida"),
("Priori Top Sc" , "Fungicida"),
("Priori Xtra Sc" , "Fungicida"),
("Recop Wp" , "Fungicida"),
("Rovral Sc" , "Fungicida"),
("Rovral Wp" , "Fungicida"),
("Serenade" , "Fungicida"),
("Simboll 125 Sc" , "Fungicida"),
("Sphere Max Sc" , "Fungicida"),
("Tenaz 250 Sc" , "Fungicida"),
("Verdadero 600 Wg" , "Fungicida"),
("Verismo Sc" , "Inseticida"),
("Sperto Wg" , "Inseticida"),
("Voliam Targo Sc" , "Inseticida"),
("Benevia Dispersao Oleo Od" , "Inseticida"),
("Durivo Sc" , "Inseticida"),
("Envidor Sc" , "Inseticida"),
("Oberon 240 Sc" , "Inseticida"),
("Abamectin Nortox Ec" , "Inseticida"),
("Vertimec 18 Ec" , "Inseticida"),
("Turbo Ec" , "Inseticida"),
("Talento 500 Wp" , "Inseticida"),
("Altacor 350 Wg" , "Inseticida"),
("Polytrin Ec" , "Inseticida"),
("Assist" , "Inseticida"),
("Nimbus Iharol Assist" , "Inseticida"),
("Agral Nonil Fenoxi Poli Etanol" , "Inseticida"),
("Goal Br Ec Concentrado Emulsionavel" , "Herbicida"),
("Flumyzin 500 Wp" , "Herbicida"),
("Diuron Nortox 800 Wp Po Molhavel" , "Herbicida"),
("Alion 500 Sc" , "Herbicida"),
("Sulfentrazone 500 Sc P" , "Herbicida"),
("Select 240 Ec" , "Herbicida"),
("Heat 700 Wg" , "Herbicida"),
("Accurate 600 Wg" , "Herbicida"),
("Clorimuron Master 250 Wg" , "Herbicida"),
("Aurora 400 Ec" , "Herbicida"),
("Glifosato Nortox 480 Concentrado Soluvel Sl" , "Herbicida"),
("Vegetal" , "Adjuvante"),
("Mineral" , "Adjuvante");
*/
