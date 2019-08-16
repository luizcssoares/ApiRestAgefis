package br.apirest.agefis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.apirest.agefis.model.Movimento;
import br.apirest.agefis.model.Vaga;
import br.apirest.agefis.repository.MovimentoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@Api(value="API REST Agefis")
public class MovimentoController {
	
	@Autowired
	MovimentoRepository movimentoRepository;
	
	@GetMapping("/movimento")
	@ApiOperation(value="Retorna uma lista com Movimentacao das Vagas")
	public ResponseEntity<?> listaMovimentos(){
		List<Movimento> lista = movimentoRepository.findAll();
				
		if (lista.isEmpty()) 
		{
			String msg = "{\"message\":\"A lista de Movimento de vagas esta vazia.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Movimento>>(lista, HttpStatus.OK);		
	}

}
