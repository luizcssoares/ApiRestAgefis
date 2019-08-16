package br.apirest.agefis.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.apirest.agefis.model.Config;

@Repository
public interface ConfigRepository  extends JpaRepository<Config, Long>{
	Config findById(long id);
	Config findFirstByOrderByIdDesc();
}
