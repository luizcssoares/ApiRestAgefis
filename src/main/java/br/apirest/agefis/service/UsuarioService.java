package br.apirest.agefis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.apirest.agefis.dto.LoginDTO;
import br.apirest.agefis.model.Usuario;
import br.apirest.agefis.repository.UsuarioRepository;

@Service
public class UsuarioService {
		
	@Autowired
	private UsuarioRepository repository;
	
	public List<Usuario> findAll() {		
		return repository.findAll();
	}
	
	public Usuario findById(long id) {
		Usuario usuario = repository.findById(id); 		
		return usuario;
	}
	
	public Usuario findByNome(String nome) {
		Usuario usuario = repository.findByNome(nome); 		
		return usuario;
	}
	
	public Usuario save(Usuario usuario) {
		return repository.save(usuario);
	}
	
	public void delete(long id) {
		repository.deleteById(id);
	}

	public Usuario findByEmail(String email) {
		// TODO Auto-generated method stub
		return repository.findByEmail(email);
	}
	
	public Usuario login(LoginDTO login) {
		// TODO Auto-generated method stub		
		Usuario usuario = findByEmail(login.getEmail());
		if (usuario != null){
		    if (usuario.getSenha().equals(login.getSenha())){
		        return usuario;	
		    }		    
		}		
		return null;
	}
}
