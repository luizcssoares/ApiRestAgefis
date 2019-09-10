package br.apirest.agefis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.apirest.agefis.model.Movimento;

@Repository
public interface MovimentoRepository extends JpaRepository<Movimento, Long>{	
	Movimento findById(long id);
	Movimento findByNumplaca(String numplaca);
	
	@Query("SELECT m FROM Movimento m WHERE m.saida = null")
	List<Movimento> findAllOcupacao();
}
