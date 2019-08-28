package br.apirest.agefis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.apirest.agefis.model.Vaga;
import br.apirest.agefis.repository.VagaRepository;
import br.apirest.agefis.service.exception.ObjectNotFoundException;

@Service
public class VagaService {
		
	@Autowired
	private VagaRepository repository;
	
	public List<Vaga> findAll() {		
		return repository.findAll();
	}
	
	public Vaga findById(long id) {
		Vaga vaga = repository.findById(id); 
		//if (vaga == null) {
		//	throw new ObjectNotFoundException("Categoria nao entrada ! " + id + ", Tipo:" + Vaga.class.getName()); 
		//}
		return vaga;
	}
	
	public Vaga save(Vaga vaga) {
		return repository.save(vaga);
	}
	
	public void delete(long id) {
		repository.deleteById(id);
	}

	public String statusVaga(long id) {
		Vaga vaga = repository.findById(id);
		return vaga.getStatus(); 
	}
	
	public void atualizaStatusVaga(long id, String status) {
		Vaga vaga = repository.findById(id);
		vaga.setStatus(status);		
		repository.save(vaga);		
	}
}
