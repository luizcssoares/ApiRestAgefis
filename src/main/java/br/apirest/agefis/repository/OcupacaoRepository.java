package br.apirest.agefis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.apirest.agefis.model.Config;
import br.apirest.agefis.model.Ocupacao;

@Repository
public interface OcupacaoRepository extends JpaRepository<Ocupacao, Long>{
	Ocupacao findByIdvaga(long idvaga);
	Ocupacao findById(long id);
}
