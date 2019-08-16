package br.apirest.agefis.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.apirest.agefis.model.ViewMovimento;
import br.apirest.agefis.model.ViewTempoMedio;

@Repository
public interface ViewTempoMedioRepository  extends JpaRepository<ViewTempoMedio, Date>{	
	@Query(value = "SELECT * from public.\"VwTempoMedio\"", nativeQuery = true)
    List<ViewTempoMedio> findAll();
}
