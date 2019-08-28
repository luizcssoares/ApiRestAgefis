package br.apirest.agefis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.apirest.agefis.model.Movimento;

@Repository
public interface MovimentoRepository extends JpaRepository<Movimento, Long>{	
	Movimento findById(long id);
	Movimento findByNumplaca(String numplaca);
}
