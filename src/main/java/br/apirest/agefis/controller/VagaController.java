package br.apirest.agefis.controller;

import java.util.List;
import java.util.Optional;

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

import br.apirest.agefis.model.Vaga;
import br.apirest.agefis.service.VagaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/* HABILITA PARA SER ACESSADO POR QUALQUER URL */
@CrossOrigin
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Agefis")
public class VagaController {
	
	@Autowired	
	VagaService vagaService;
	
	@GetMapping("/vaga")
	@ApiOperation(value="Retorna uma lista de Vagas")
	public ResponseEntity<?> listaVagas(){
		List<Vaga> lista = vagaService.findAll();
		if (lista.isEmpty()) 
		{
			String msg = "{\"message\":\"Nao existe vagas cadastradas.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Vaga>>(lista, HttpStatus.OK);		
	}
	
	@GetMapping("/vaga/{id}")
	@ApiOperation(value="Retorna uma unica Vaga")
	public ResponseEntity<?> detalheVaga(@PathVariable(value="id") long id){
		Vaga vaga = vagaService.findById(id);		
		if (vaga == null) 
		 {
		    String msg = "{\"message\":\"A vaga " + id + " nao foi encontrada.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		  }		 
		 else 
		 {
			return new ResponseEntity<Vaga>(vaga, HttpStatus.OK);        
	      }			
	}
	
	@PostMapping("/vaga")
	@ApiOperation(value="Salva dados de uma Vaga")
	public ResponseEntity<Vaga> salvarVaga(@RequestBody Vaga vaga){		
		Vaga vagaSalva = vagaService.save(vaga);
		return new ResponseEntity<Vaga>(vagaSalva, HttpStatus.OK);
	}
	
	@DeleteMapping("/vaga")
	@ApiOperation(value="Exclui uma Vaga")
	public ResponseEntity<?> removeVaga(@RequestBody Vaga vaga){
		Vaga vagaFind = vagaService.findById(vaga.getId());		
		if (vagaFind == null) 
		{
			 String msg = "{\"message\":\"A vaga " + vaga.getId() + " nao foi encontrada.\"}";
             return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}		 		
		vagaService.delete(vaga.getId());
		return new ResponseEntity<Vaga>(HttpStatus.NO_CONTENT);        	    		
	}
	
	@PutMapping("/vaga")
	@ApiOperation(value="Atualiza dados de uma Vaga")
	public ResponseEntity<?> atualizaVaga(@RequestBody Vaga vaga){
		Vaga vagaEdit = vagaService.findById(vaga.getId());		
		if (vagaEdit == null) 
		{
			 String msg = "{\"message\":\"A vaga " + vaga.getId() + " nao foi encontrada.\"}";
             return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		
		Vaga vagaSave = vagaService.save(vaga);		
		return new ResponseEntity<Vaga>(vagaSave, HttpStatus.OK);		
	}
}
