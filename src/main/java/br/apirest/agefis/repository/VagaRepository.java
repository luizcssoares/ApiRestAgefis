package br.apirest.agefis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.apirest.agefis.model.Vaga;
import io.swagger.annotations.ApiOperation;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long>{
	Vaga findByNome(String nome);
	Vaga save(Vaga vaga);
	Vaga findById(long id);
}
