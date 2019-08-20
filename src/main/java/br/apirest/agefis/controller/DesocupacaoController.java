package br.apirest.agefis.controller;

import java.math.BigDecimal;
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

import br.apirest.agefis.model.Config;
import br.apirest.agefis.model.Desocupacao;
import br.apirest.agefis.model.Movimento;
import br.apirest.agefis.model.Ocupacao;
import br.apirest.agefis.model.Vaga;
import br.apirest.agefis.repository.ConfigRepository;
import br.apirest.agefis.repository.DesocupacaoRepository;
import br.apirest.agefis.repository.MovimentoRepository;
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
public class DesocupacaoController {
	@Autowired
	DesocupacaoRepository desocupacaoRepository;
	
	@Autowired
	OcupacaoRepository ocupacaoRepository;
	
	@Autowired
	VagaRepository vagaRepository;
	
	@Autowired
	ConfigRepository configRepository;
	
	@Autowired
	MovimentoRepository movimentoRepository;
			
	@GetMapping("/desocupacao")
	@ApiOperation(value="Retorna uma lista de Desocupacao das vagas")
	public ResponseEntity<?> listaDesocupacao(){
		
		List<Desocupacao> lista = desocupacaoRepository.findAll();
		if (lista.isEmpty()) {
			String msg = "{\"message\":\"A lista de Desocupacao de vagas esta vazia.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Desocupacao>>(lista, HttpStatus.OK);	
	}
	
	@GetMapping("/desocupacao/{id}")
	@ApiOperation(value="Retorna uma unica Vaga Desocupada")
	public ResponseEntity<?> detalheDesocupacao(@PathVariable(value="id") long id){
		
		Desocupacao desocupacao = desocupacaoRepository.findById(id);		
		if (desocupacao == null) 
		 {
		    String msg = "{\"message\":\"A Desocupacao de vaga " + id + " nao foi encontrada.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		  }		 
		 else 
		 {
			return new ResponseEntity<Desocupacao>(desocupacao, HttpStatus.OK);        
	      }
	}
	
	@PostMapping("/desocupacao")
	@ApiOperation(value="Salva dados de uma Vaga Desocupada")	
	public ResponseEntity<?> salvarDesocupacao(@RequestBody Desocupacao desocupacao) throws ParseException{	
		
		// Valor cobrado pela Hora do estacionamento 
		Config config = new Config();
		config = configRepository.findFirstByOrderByIdDesc();
		if (config == null)
		{
			String msg = "{\"message\":\"O valor da Hora do Aluguel ainda nao foi configurado.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		 }
		else 
		{	
			// Valor da Hora do Aluguel
		    BigDecimal valorHora = config.getValor();
						
			Vaga vaga = new Vaga();
			vaga = vagaRepository.findById(desocupacao.getIdvaga());
			if (vaga.getStatus().equals("O"))  // Checa se vaga Ocupada
			{															
				// Dados da Ocupacao do Veiculo na Vaga				
				Ocupacao ocupacao = new Ocupacao();
				ocupacao = ocupacaoRepository.findByIdvaga(desocupacao.getIdvaga());
				Date dataEntrada = ocupacao.getEntrada();
				Date horaEntrada = ocupacao.getHora();
											
				// Data e Hora da Saida
				Util util = new Util();
				Date dataSaida = util.DataCorrente();		
				Date horaSaida = util.HoraCorrente();			
						
				// Calculo do Numero de horas para efetuar o Pagamento						
				double numHoras = (horaSaida.getTime() - horaEntrada.getTime()) / 3600000;				
				BigDecimal valorPago = valorHora.multiply(new BigDecimal(numHoras));		
				//System.out.println(valorPago);
										
				// Preenche os campo ValorPago, DataSaida, Horasaida e Valor Moeda (config.getId()) da Desocupacao 
				desocupacao.setIdconfig(config.getId());
				desocupacao.setHora(horaSaida); 
				desocupacao.setSaida(dataSaida);		
				desocupacao.setValor(valorPago);
											
				// Atualiza Vaga para status (D - desocupada)			
				vaga = vagaRepository.findById(desocupacao.getIdvaga());
				vaga.setStatus("D");
				vagaRepository.save(vaga);
				
				Desocupacao desocupacaoSave = desocupacaoRepository.save(desocupacao);
				
		        // Atualiza tabela de Movimento das vagas
				Movimento movimento = new Movimento();
				movimento.setIdocupacao(ocupacao.getId());
				movimento.setIddesocupacao(desocupacaoSave.getId());
				movimentoRepository.save(movimento);
				
				return new ResponseEntity<Desocupacao>(desocupacaoSave, HttpStatus.OK);	
			 }
			else 
			{
				String msg = "{\"message\":\"A vaga " + desocupacao.getIdvaga() + " ja encontra-se Desocupada.\"}";
	            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
			 }
		}
	}
	
	@DeleteMapping("/desocupacao")
	@ApiOperation(value="Excluir uma Desocupacao efetivada")
	public ResponseEntity<?> removeDesocupacao(@RequestBody Desocupacao desocupacao){
		
		Desocupacao desocupacaoFind = desocupacaoRepository.findById(desocupacao.getId());		
		if (desocupacaoFind == null) 
		{
			 String msg = "{\"message\":\"A Desocupacao de vaga " + desocupacao.getId() + " nao foi encontrada.\"}";
             return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}		 		
		desocupacaoRepository.delete(desocupacao);
		return new ResponseEntity<Desocupacao>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/desocupacao")
	@ApiOperation(value="Atualiza dados de uma Vaga Desocupada")
	public ResponseEntity<?> atualizaDesocupacao(@RequestBody Desocupacao desocupacao){
		
		Desocupacao desocupacaoEdit = desocupacaoRepository.findById(desocupacao.getId());		
		if (desocupacaoEdit == null) 
		{
			 String msg = "{\"message\":\"A Desocupacao de vaga " + desocupacao.getId() + " nao foi encontrada.\"}";
             return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		
		Desocupacao desocupacaoSave = desocupacaoRepository.save(desocupacao);		
		return new ResponseEntity<Desocupacao>(desocupacaoSave, HttpStatus.OK);
	}

}
