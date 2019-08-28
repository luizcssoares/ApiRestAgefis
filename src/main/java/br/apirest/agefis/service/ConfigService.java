package br.apirest.agefis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.apirest.agefis.model.Config;
import br.apirest.agefis.repository.ConfigRepository;

@Service
public class ConfigService {
	
	@Autowired	
	ConfigRepository repository;
	
	public Optional<Config> findById(long id){
     	return repository.findById(id);     	
	}
	
	public Config findFirstByOrderByIdDesc(){
     	return repository.findFirstByOrderByIdDesc();     	
	}

	public List<Config> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	public Config save(Config config) {
		// TODO Auto-generated method stub
		return repository.save(config);
	}
}
