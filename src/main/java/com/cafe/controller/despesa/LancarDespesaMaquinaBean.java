package com.cafe.controller.despesa;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.DespesaMaquina;
import com.cafe.modelo.Maquina;
import com.cafe.modelo.enums.FatorPotencia;
import com.cafe.service.DespesaMaquinaService;
import com.cafe.service.MaquinaService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class LancarDespesaMaquinaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDate mesAno;
	private List<Maquina> maquinas;
	private DespesaMaquina despesaMaquina;
	private List<FatorPotencia> fatorPotencias;
	private List<DespesaMaquina> despesas = new ArrayList<>();
	//private List<BigDecimal> valorTotal;
	private Long tenantId;

	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private MaquinaService maquinaService;
	
	@Inject
	private DespesaMaquinaService despesaService;

	@PostConstruct
	public void inicializar() {
		//tenantId = loginBean.getUsuario().getTenant().getCodigo();
		mesAno = LocalDate.now();
		maquinas = maquinaService.buscarMaquinas(loginBean.getTenantId());
		fatorPotencias = Arrays.asList(FatorPotencia.values());
		despesas = despesaService.buscarDespesasMaquinas(loginBean.getTenantId());
		

		
		limpar();
	}
	
    public void salvar() {
    	//log.info("salvar ..." + despesaMaquina);
    	
		despesaMaquina.setMesAno(mesAno);
		despesaMaquina.setValorTotal(despesaService.calcularValorTotal(despesaMaquina));

    	try {
			this.despesaService.salvar(despesaMaquina);
			despesas.add(despesaMaquina);
			MessageUtil.sucesso("Despesa salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
		this.limpar();
    	//this.despesaService.salvar(despesaMaquina);

    	
    	
    }
    
    public void excluir() {
    	try {
			despesaService.excluir(despesaMaquina);			
			this.despesas.remove(despesaMaquina);
			MessageUtil.sucesso("Despesa " + despesaMaquina.getId() + " excluída com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
    }
    
    
    public void buscarPrecoCombustivel() {
    	log.info("buscar preço ..." + despesaMaquina.getMaquina().getId());
    	
    	//buscar no cadastro
    	//precoCombustivel.setValor(new BigDecimal(5.54));
    	//BigDecimal ultimoValor = despesaService
    	//		.buscarUltimoPrecoCombustivel(loginBean.getUsuario().getTenant().getCodigo()).getValor();
    	
    }

	public void limpar() {
		despesaMaquina = new DespesaMaquina();

		despesaMaquina.setMesAno(mesAno);
		despesaMaquina.setUnidade(loginBean.getUsuario().getUnidade());
		despesaMaquina.setTenant_id(loginBean.getUsuario().getTenant().getCodigo());
	}
	
	

	
	
}