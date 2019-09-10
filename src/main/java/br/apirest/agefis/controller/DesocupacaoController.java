package br.apirest.agefis.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.apirest.agefis.dto.DesocupacaoDTO;
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
public class DesocupacaoController {
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	VagaService vagaService;
	
	@Autowired
	MovimentoService movimentoService;
					
	//@PostMapping("/movimento/desocupacao")
	@PutMapping("/desocupacao")
	@ApiOperation(value="Salva dados de uma Desocupacao")	
	public ResponseEntity<?> salvarDesocupacao(@RequestBody DesocupacaoDTO desocupacaoDTO) throws ParseException{
		
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
		    Movimento desocupacao = movimentoService.findByNumplaca(desocupacaoDTO.getNumplaca());
		    if (desocupacao == null) 
			{
			    String msg = "{\"message\":\"A desocupacao de vaga para placa " + desocupacaoDTO.getNumplaca() + " nao foi encontrada.\"}";
	            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
			}
		    		    
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
