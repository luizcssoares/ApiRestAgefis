package br.apirest.agefis.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.apirest.agefis.model.Config;
import br.apirest.agefis.model.Vaga;
import br.apirest.agefis.repository.ConfigRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@Api(value="API REST Agefis")
public class ConfigController {
	
	@Autowired
	ConfigRepository configRepository;
	
	@GetMapping("/config")
	@ApiOperation(value="Retorna lista de valores da moeda")
	public ResponseEntity<?> listaConfig(){
		List<Config> lista = configRepository.findAll();
		if (lista.isEmpty()) 
		{
			String msg = "{\"message\":\"Nao existe lista de valores da moeda cadastrada.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Config>>(lista, HttpStatus.OK);		
	}
	
	@GetMapping("/config/moeda")
	@ApiOperation(value="Retorna o valor cobrado pela Hora do estacionamento")	
	public ResponseEntity<?> moedaConfig(){
		
		// Seleciona o ultimo registro da tabela de Config para pegar o valor da Moeda
		Config config = configRepository.findFirstByOrderByIdDesc();		
		if (config == null) 
		 {
		    String msg = "{\"message\":\"O valor da Hora do aluguel nao foi configurado.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		  }		 
		 else 
		 {
			return new ResponseEntity<Config>(config, HttpStatus.OK);        
	      }		
	}
		
	@PutMapping("/config")
	@ApiOperation(value="Atualiza o valor cobrado pela Hora do estacionamento")
	public ResponseEntity<?> atulizaConfig(@RequestBody Config config){
		Config configSave = configRepository.save(config);
		return new ResponseEntity<Config>(configSave, HttpStatus.OK);		
		//return configRepository.save(config);
	}
	
	@PostMapping("/config")
	@ApiOperation(value="Insere o valor cobrado pela Hora do estacionamento")
	public ResponseEntity<?> insereConfig(@RequestBody Config config){									
		Config configSave = configRepository.save(config);
		return new ResponseEntity<Config>(configSave, HttpStatus.OK);		 	
	}
}
