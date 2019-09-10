package br.apirest.agefis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.apirest.agefis.dto.VagaDTO;
import br.apirest.agefis.model.Vaga;
import br.apirest.agefis.repository.VagaRepository;

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
	
	public Page<Vaga> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
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
	
	public Vaga fromDTO(VagaDTO vagaDto) {
		return new Vaga(vagaDto.getId(), vagaDto.getNome(), vagaDto.getStatus());
	}
}
