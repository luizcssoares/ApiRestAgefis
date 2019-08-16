package br.apirest.agefis.controller;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.apirest.agefis.model.ViewFinanceiro;
import br.apirest.agefis.model.ViewMovimento;
import br.apirest.agefis.model.ViewTempoMedio;
import br.apirest.agefis.repository.ViewFinanceiroRepository;
import br.apirest.agefis.repository.ViewMovimentoRepository;
import br.apirest.agefis.repository.ViewTempoMedioRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api/relatorio")
@Api(value="API REST Agefis")
public class RelatorioController {
	
	@Autowired
	ViewMovimentoRepository viewmovimentoRepository;
	
	@Autowired
	ViewFinanceiroRepository viewfinanceiroRepository;
	
	@Autowired
	ViewTempoMedioRepository viewTempoMedioRepository;
	
	@Autowired
	EntityManager entityManager;
	
	@GetMapping("/movimento")
	@ApiOperation(value="Retorna uma lista com Movimentacao das Vagas")
	public ResponseEntity<?> listaViewMovimentos(){
		List<ViewMovimento> lista = viewmovimentoRepository.findAll();		
		//List<ViewMovimento> lista = entityManager.createQuery("select vm from ViewMovimento vm", ViewMovimento.class).getResultList();				
		System.out.println("tamanho :"+lista.size());
		if (lista.isEmpty()) 
		{
			String msg = "{\"message\":\"A lista de movimento de vagas esta vazia.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ViewMovimento>>(lista, HttpStatus.OK);		
	}
	
	@GetMapping("/financeiro")
	@ApiOperation(value="Retorna uma lista com Movimentacao das Vagas")
	public ResponseEntity<?> listaViewFinanceiro(){
		List<ViewFinanceiro> lista = viewfinanceiroRepository.findAll();
		if (lista.isEmpty()) 
		{
			String msg = "{\"message\":\"A lista com dados financeiros esta vazia.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ViewFinanceiro>>(lista, HttpStatus.OK);		
	}

	@GetMapping("/tempomedio")
	@ApiOperation(value="Retorna uma lista com tempo medio de ocupacao das Vagas")
	public ResponseEntity<?> listaViewTempoMedio(){
		List<ViewTempoMedio> lista = viewTempoMedioRepository.findAll();
		if (lista.isEmpty()) 
		{
			String msg = "{\"message\":\"A lista com tempos medios de ocupacao das vagas esta vazia.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ViewTempoMedio>>(lista, HttpStatus.OK);		
	}

	

}
