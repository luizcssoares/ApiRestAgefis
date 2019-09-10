package br.apirest.agefis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.apirest.agefis.model.Movimento;
import br.apirest.agefis.repository.MovimentoRepository;

@Service
public class MovimentoService {
	
	@Autowired
	MovimentoRepository repository;
	
	public List<Movimento> findAllOcupacao() {				
		return repository.findAllOcupacao();
	}
	
	public Movimento findById(long id) {				
		return repository.findById(id);
	}
				
	public List<Movimento> findAll() {
		return repository.findAll();
	}
	
	public Movimento findByNumplaca(String numplaca) {				
		return repository.findByNumplaca(numplaca);
	}
		
	public Movimento save(Movimento movimento) {
		return repository.save(movimento);
	}
	
	public void delete(long id) {
		repository.deleteById(id);
	}

	
	
	
}
