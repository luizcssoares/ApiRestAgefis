package br.apirest.agefis.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.apirest.agefis.enums.TipoOcupacao;
import br.apirest.agefis.model.Config;
import br.apirest.agefis.model.Movimento;
import br.apirest.agefis.model.Vaga;
import br.apirest.agefis.service.ConfigService;
import br.apirest.agefis.service.MovimentoService;
import br.apirest.agefis.service.VagaService;
import br.apirest.agefis.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/* HABILITA PARA SER ACESSADO POR QUALQUER URL */
@CrossOrigin
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Agefis")
public class MovimentoController {
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	VagaService vagaService;
	
	@Autowired
	MovimentoService movimentoService;
			
	@GetMapping("/movimento")
	@ApiOperation(value="Retorna uma lista de Movimento das vagas")
	public ResponseEntity<?> listaMovimento(){
		List<Movimento> lista = movimentoService.findAll();
		if (lista.isEmpty()) {
			String msg = "{\"message\":\"A lista de Ocupacao das vagas esta vazia.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Movimento>>(lista, HttpStatus.OK);			
	}
	
	@GetMapping("/movimento/{id}")
	@ApiOperation(value="Retorna uma unica Ocupacao de vaga")
	public ResponseEntity<?> detalheMovimento(@PathVariable(value="id") long id){
		Movimento movimento = movimentoService.findById(id);		
		if (movimento == null) 
		 {
		    String msg = "{\"message\":\"A Ocupacao de vaga " + id + " nao foi encontrada.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		  }		 
		 else 
		 {
			return new ResponseEntity<Movimento>(movimento, HttpStatus.OK);        
	      }				
	}
		
	@DeleteMapping("/movimento")
	@ApiOperation(value="Excluir um Movimento de vaga")
	public ResponseEntity<?> removeMovimento(@RequestBody Movimento movimento){		
		Movimento ocupacaoFind = movimentoService.findById(movimento.getId());		
		if (ocupacaoFind == null) 
		{
			 String msg = "{\"message\":\"A ocupacao de vaga " + ocupacaoFind.getId() + " nao foi encontrada.\"}";
             return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}		 		
		movimentoService.delete(ocupacaoFind.getId());
		return new ResponseEntity<Movimento>(HttpStatus.NO_CONTENT);				
	}
	
	@PutMapping("/movimento")
	@ApiOperation(value="Atualiza dados de uma Ocupacao de vaga")
	public ResponseEntity<?> atualizaMovimento(@RequestBody Movimento movimento){
		
		Movimento ocupacaoEdit = movimentoService.findById(movimento.getId());		
		if (ocupacaoEdit == null) 
		{
			 String msg = "{\"message\":\"A ocupacao de vaga " + movimento.getId() + " nao foi encontrada.\"}";
             return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		
		Movimento ocupacaoSave = movimentoService.save(movimento);		
		return new ResponseEntity<Movimento>(ocupacaoSave, HttpStatus.OK);						
	}		
}
