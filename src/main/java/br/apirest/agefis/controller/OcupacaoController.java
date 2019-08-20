package br.apirest.agefis.controller;

import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RestController;

import br.apirest.agefis.model.Ocupacao;
import br.apirest.agefis.model.Vaga;
import br.apirest.agefis.repository.ConfigRepository;
import br.apirest.agefis.repository.OcupacaoRepository;
import br.apirest.agefis.repository.VagaRepository;
import br.apirest.agefis.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/* HABILITA PARA SER ACESSADO POR QUALQUER URL */
@CrossOrigin
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Agefis")
public class OcupacaoController {
	
	@Autowired
	OcupacaoRepository ocupacaoRepository;
	
	@Autowired
	ConfigRepository configRepository;
	
	@Autowired
	VagaRepository vagaRepository;
			
	@GetMapping("/ocupacao")
	@ApiOperation(value="Retorna uma lista de Ocupacao das vagas")
	public ResponseEntity<?> listaOcupacao(){
		List<Ocupacao> lista = ocupacaoRepository.findAll();
		if (lista.isEmpty()) {
			String msg = "{\"message\":\"A lista de Ocupacao das vagas esta vazia.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Ocupacao>>(lista, HttpStatus.OK);			
	}
	
	@GetMapping("/ocupacao/{id}")
	@ApiOperation(value="Retorna uma unica Ocupacao de vaga")
	public ResponseEntity<?> detalheOcupacao(@PathVariable(value="id") long id){
		Ocupacao ocupacao = ocupacaoRepository.findById(id);		
		if (ocupacao == null) 
		 {
		    String msg = "{\"message\":\"A Ocupacao de vaga " + id + " nao foi encontrada.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		  }		 
		 else 
		 {
			return new ResponseEntity<Ocupacao>(ocupacao, HttpStatus.OK);        
	      }				
	}
	
	@PostMapping("/ocupacao")
	@ApiOperation(value="Salva dados de uma Ocupacao de vaga")		
	public ResponseEntity<?> salvarOcupacao(@RequestBody Ocupacao ocupacao) throws ParseException{
		
		Vaga vaga = new Vaga();
		vaga = vagaRepository.findById(ocupacao.getIdvaga());
		if (vaga == null)                      // Checa se vaga existe
		{
			String msg = "{\"message\":\"A vaga " + ocupacao.getIdvaga() + " nao existe.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		 }
		else
		{
			if (vaga.getStatus().equals("D"))  // Checa se vaga Desocupada
			{								
				Util util = new Util();		
				Date dataEntrada = util.DataCorrente();
				Date horaEntrada = util.HoraCorrente();
						
				ocupacao.setEntrada(dataEntrada);
				ocupacao.setHora(horaEntrada);
				
				// Atualiza vaga para status (O - Ocupada)			
				vaga = vagaRepository.findById(ocupacao.getIdvaga());
				vaga.setStatus("O");
				vagaRepository.save(vaga);
				
				Ocupacao ocupacaoSave = ocupacaoRepository.save(ocupacao); 
				return new ResponseEntity<Ocupacao>(ocupacaoSave, HttpStatus.OK);											 		
			}
			else {
				String msg = "{\"message\":\"A vaga " + ocupacao.getIdvaga() + " encontra-se Ocupada.\"}";
	            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
			}
		}
	}
	
	@DeleteMapping("/ocupacao")
	@ApiOperation(value="Excluir uma Ocupacao de vaga")
	public ResponseEntity<?> removeOcupacao(@RequestBody Ocupacao ocupacao){		
		Ocupacao ocupacaoFind = ocupacaoRepository.findById(ocupacao.getId());		
		if (ocupacaoFind == null) 
		{
			 String msg = "{\"message\":\"A ocupacao de vaga " + ocupacao.getId() + " nao foi encontrada.\"}";
             return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}		 		
		ocupacaoRepository.delete(ocupacao);
		return new ResponseEntity<Vaga>(HttpStatus.NO_CONTENT);				
	}
	
	@PutMapping("/ocupacao")
	@ApiOperation(value="Atualiza dados de uma Ocupacao de vaga")
	public ResponseEntity<?> atualizaOcupacao(@RequestBody Ocupacao ocupacao){
		
		Ocupacao ocupacaoEdit = ocupacaoRepository.findById(ocupacao.getId());		
		if (ocupacaoEdit == null) 
		{
			 String msg = "{\"message\":\"A ocupacao de vaga " + ocupacao.getId() + " nao foi encontrada.\"}";
             return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		
		Ocupacao ocupacaoSave = ocupacaoRepository.save(ocupacao);		
		return new ResponseEntity<Ocupacao>(ocupacaoSave, HttpStatus.OK);						
	}
}
