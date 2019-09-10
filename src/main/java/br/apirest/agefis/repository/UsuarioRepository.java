package br.apirest.agefis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.apirest.agefis.dto.LoginDTO;
import br.apirest.agefis.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Usuario findByNome(String nome);
	Usuario save(Usuario usuario);
	Usuario findById(long id);
	Usuario findByEmail(String email);
	//Usuario login(LoginDTO login);
}
