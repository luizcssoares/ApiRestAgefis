package br.apirest.agefis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.apirest.agefis.model.Desocupacao;
import br.apirest.agefis.model.Ocupacao;

@Repository
public interface DesocupacaoRepository extends JpaRepository<Desocupacao, Long>{
	Desocupacao findByIdvaga(long idvaga);
	Desocupacao findById(long id);
}
