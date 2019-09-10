package br.apirest.agefis.controller;

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

import br.apirest.agefis.dto.LoginDTO;
import br.apirest.agefis.model.Usuario;
import br.apirest.agefis.model.Vaga;
import br.apirest.agefis.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/* HABILITA PARA SER ACESSADO POR QUALQUER URL */
@CrossOrigin
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Agefis")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/usuario/{id}")
	@ApiOperation(value="Retorna uma unica Vaga")
	public ResponseEntity<?> detalheUsuario(@PathVariable(value="id") long id){
		Usuario usuario = usuarioService.findById(id);		
		if (usuario == null) 
		 {
		    String msg = "{\"message\":\"O usuario id " + id + " nao foi encontrada.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		  }		 
		 else 
		 {
		    return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);        
	      }			
	}
	
	@GetMapping("/usuario/{email}")
	@ApiOperation(value="Retorna um unico usuario")
	public ResponseEntity<?> detalheUsuario(@PathVariable(value="email") String email){
		Usuario usuario = usuarioService.findByEmail(email);		
		if (usuario == null) 
		 {
		    String msg = "{\"message\":\"O usuario de email " + email + " nao foi encontrada.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		  }		 
		 else 
		 {
		    return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);        
	      }			
	}
		
	@GetMapping("/usuario")
	@ApiOperation(value="Retorna lista de usurios")
	public ResponseEntity<?> listaUsuario(){
		List<Usuario> lista = usuarioService.findAll();
		if (lista.isEmpty()) 
		{
			String msg = "{\"message\":\"Nao existe Usuarios cadastrados.\"}";
            return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);		
	}
	
	@DeleteMapping("/usuario")
	@ApiOperation(value="Exclui um Usuario")
	public ResponseEntity<?> removeUsuario(@RequestBody Usuario usuario){
		Usuario usuarioFind = usuarioService.findById(usuario.getId());		
		if (usuarioFind == null) 
		{
			 String msg = "{\"message\":\"A vaga " + usuario.getId() + " nao foi encontrada.\"}";
             return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}		 		
		usuarioService.delete(usuario.getId());
		return new ResponseEntity<Vaga>(HttpStatus.NO_CONTENT);        	    		
	}
		
	@PutMapping("/usuario")
	@ApiOperation(value="Atualiza os dados de um usuario")
	public ResponseEntity<?> atulizaUsuario(@RequestBody Usuario usuario){
		Usuario usuarioSave = usuarioService.save(usuario);
		return new ResponseEntity<Usuario>(usuarioSave, HttpStatus.OK);		
		//return configRepository.save(config);
	}
	
	@PostMapping("/login")
	@ApiOperation(value="Insere os dados de um usuario")
	public ResponseEntity<?> loginUsuario(@RequestBody LoginDTO loginDTO){									
		Usuario usuarioLogin = usuarioService.login(loginDTO);
		if (usuarioLogin == null) 
		{
			 String msg = "{\"message\":\"O Login e (ou) Senha estao invalidos.\"}";
             return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
		}		 	
		return new ResponseEntity<Usuario>(usuarioLogin, HttpStatus.OK);		 	
	}
	
	@PostMapping("/usuario")
	@ApiOperation(value="Insere os dados de um usuario")
	public ResponseEntity<?> insereUsuario(@RequestBody Usuario usuario){									
		Usuario usuarioSave = usuarioService.save(usuario);
		return new ResponseEntity<Usuario>(usuarioSave, HttpStatus.OK);		 	
	}
}
