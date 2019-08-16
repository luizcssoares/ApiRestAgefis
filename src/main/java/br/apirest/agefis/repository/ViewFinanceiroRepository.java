package br.apirest.agefis.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.apirest.agefis.model.ViewFinanceiro;
import br.apirest.agefis.model.ViewMovimento;

@Repository
public interface ViewFinanceiroRepository extends JpaRepository<ViewFinanceiro, Date>{	
	@Query(value = "SELECT * from public.\"VwFinanceiro\"", nativeQuery = true)
    List<ViewFinanceiro> findAll();
}
