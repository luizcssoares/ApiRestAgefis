package br.apirest.agefis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.apirest.agefis.model.ViewMovimento;

@Repository
public interface ViewMovimentoRepository  extends JpaRepository<ViewMovimento, Long>{			
	@Query(value = "SELECT * from public.\"VwMovimento\"", nativeQuery = true)
    List<ViewMovimento> findAll();
}
