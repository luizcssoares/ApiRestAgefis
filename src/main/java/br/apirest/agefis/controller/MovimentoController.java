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
	@ApiOperation(value="Retorna uma lista de Ocupacao das vagas")
	public ResponseEntity<?> listaOcupacao(){
		List<Movimento> lista = movimentoService.findAll();
		if (lista.isEmpty()) {
			String msg = "{\"message\":\"A lista de Ocupacao das vagas esta vazia.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Movimento>>(lista, HttpStatus.OK);			
	}
	
	@GetMapping("/movimento/{id}")
	@ApiOperation(value="Retorna uma unica Ocupacao de vaga")
	public ResponseEntity<?> detalheOcupacao(@PathVariable(value="id") long id){
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
	
	@PostMapping("/movimento/ocupacao")
	@ApiOperation(value="Salva dados de uma Ocupacao de vaga")		
	//public ResponseEntity<?> salvarOcupacao(@RequestBody Ocupacao ocupacao) throws ParseException{
	public ResponseEntity<?> salvarOcupacao(@RequestParam String numplaca, 
			                                @RequestParam long idvaga) throws ParseException{		
		
		System.out.println("entrei no salvarOcupacao");
		
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
				movimento.setTipo(TipoOcupacao.HORISTA);											
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
	
	@DeleteMapping("/movimento")
	@ApiOperation(value="Excluir uma Ocupacao de vaga")
	public ResponseEntity<?> removeOcupacao(@RequestBody Movimento movimento){		
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
	public ResponseEntity<?> atualizaOcupacao(@RequestBody Movimento movimento){
		
		Movimento ocupacaoEdit = movimentoService.findById(movimento.getId());		
		if (ocupacaoEdit == null) 
		{
			 String msg = "{\"message\":\"A ocupacao de vaga " + movimento.getId() + " nao foi encontrada.\"}";
             return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		
		Movimento ocupacaoSave = movimentoService.save(movimento);		
		return new ResponseEntity<Movimento>(ocupacaoSave, HttpStatus.OK);						
	}
		
	@PostMapping("/movimento/desocupacao")
	@ApiOperation(value="Salva dados de uma Vaga Desocupada")	
	public ResponseEntity<?> salvarDesocupacao(@RequestParam String numplaca) throws ParseException{
		
		// Valor cobrado pela Hora do estacionamento 		 
		Config config = configService.findFirstByOrderByIdDesc();
		if (config == null)
		{
			String msg = "{\"message\":\"O valor da Hora do Aluguel ainda nao foi configurado.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		 }
		else 
		{	
			// Valor da Hora do Aluguel
		    BigDecimal valorHora = config.getValor();
		    
		    // Localiza Ocupacao pela numplaca
		    Movimento desocupacao = movimentoService.findByNumplaca(numplaca);		    
		    long idvaga = desocupacao.getVaga().getId();
		    long idmovimento = desocupacao.getId();
						
  		    // Checa primeiro para ver se vaga esta Ocupada
		    Vaga vaga = vagaService.findById(idvaga);		
			if (vaga.getStatus().equals("O"))  
			{																										
				// Procura na tabela Ocupacao a Data/hora de entrada				
				Date dataEntrada = desocupacao.getEntrada();
				Date horaEntrada = desocupacao.getHoraentrada();
											
				// Data e Hora da Saida
				Util util = new Util();
				Date dataSaida = util.DataCorrente();		
				Date horaSaida = util.HoraCorrente();			
						
				// Calculo do Numero de horas para efetuar o Pagamento						
				double numHoras = (horaSaida.getTime() - horaEntrada.getTime()) / 3600000;				
				BigDecimal valorPago = valorHora.multiply(new BigDecimal(numHoras));		
				//System.out.println(valorPago);
										
				// Preenche os campo ValorPago, DataSaida, Horasaida e Valor Moeda (config.getId()) da Desocupacao				
				desocupacao.setConfig(config);
				desocupacao.setHorasaida(horaSaida); 
				desocupacao.setSaida(dataSaida);		
				desocupacao.setValorpago(valorPago);
				Movimento desocupacaoSave = movimentoService.save(desocupacao);		
											
				// Atualiza Vaga para status (D - desocupada)
				vaga.setStatus("D");
				Vaga vagaUpdate = vagaService.save(vaga);
								
				return new ResponseEntity<Movimento>(desocupacaoSave, HttpStatus.OK);	
			 }
			else 
			{
				String msg = "{\"message\":\"A vaga " + vaga.getId() + " ja encontra-se Desocupada.\"}";
	            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
			 }
		}
	}		
}
