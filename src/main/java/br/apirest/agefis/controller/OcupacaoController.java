package br.apirest.agefis.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.apirest.agefis.dto.OcupacaoDTO;
import br.apirest.agefis.enums.TipoOcupacao;
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
public class OcupacaoController {
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	VagaService vagaService;
	
	@Autowired
	MovimentoService movimentoService;
				
	
	@GetMapping("/ocupacao")
	@ApiOperation(value="Retorna uma lista de Ocupacao das vagas")
	public ResponseEntity<?> listaOcupacao(){
		List<Movimento> lista = movimentoService.findAllOcupacao();
		if (lista.isEmpty()) {
			String msg = "{\"message\":\"A lista de Ocupacao das vagas esta vazia.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Movimento>>(lista, HttpStatus.OK);			
	}
	
	@PostMapping("/ocupacao")
	@ApiOperation(value="Salva dados de uma Ocupacao de vaga")		
	public ResponseEntity<?> salvarOcupacao(@RequestBody OcupacaoDTO ocupacao) throws ParseException{
	//public ResponseEntity<?> salvarOcupacao(@RequestParam String numplaca, 
	//		                                @RequestParam long idvaga) throws ParseException{		
		
		System.out.println("entrei no salvarOcupacao");
		
		String numplaca = ocupacao.getNumplaca(); 
        long   idvaga = ocupacao.getIdvaga(); 			
        String tipo = ocupacao.getTipo();
		
		Vaga vaga = vagaService.findById(idvaga);
		if (vaga == null)                      // Checa se vaga existe
		{
			String msg = "{\"message\":\"A vaga " + idvaga + " nao existe.\"}";	
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		 }
		else
		{
			// Checa se vaga Desocupada (D - Desocupada)
			if (vagaService.statusVaga(vaga.getId()).equals("D"))  
			{								
				Util util = new Util();		
				Date dataEntrada = util.DataCorrente();
				Date horaEntrada = util.HoraCorrente();
				
				// Lanca registro na tabela Movimento
				Movimento movimento = new Movimento();
				movimento.setVaga(vaga); 											
				movimento.setNumplaca(numplaca);
				movimento.setEntrada(dataEntrada);
				movimento.setHoraentrada(horaEntrada);
				//movimento.setTipo(TipoOcupacao.HORISTA);
				if (tipo.equals("1")) {
				    movimento.setTipo(TipoOcupacao.HORISTA);
				}  
				else {
					movimento.setTipo(TipoOcupacao.MENSALISTA);
				}
				Movimento movimentoSave = movimentoService.save(movimento);
																			
				// Atualiza vaga para status (O - Ocupada) e Lista de Ocupação.
				List<Movimento> listaMov = new ArrayList<>();
				listaMov.add(movimentoSave);
				
				Vaga vagaFind = vagaService.findById(idvaga);
				vagaFind.setMovimentos(listaMov);				
				vagaFind.setStatus("O");		
				
				Vaga vagaUpdate = vagaService.save(vagaFind);		
														
				return new ResponseEntity<Movimento>(movimentoSave, HttpStatus.OK);											 		
			}
			else {
				String msg = "{\"message\":\"A vaga " + idvaga + " encontra-se Ocupada.\"}";
	            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
			}
		}
	}
						
}
