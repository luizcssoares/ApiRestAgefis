package br.apirest.agefis.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.apirest.agefis.model.Vaga;

public class VagaDTO implements Serializable{
		
	private long id;		
	
	@NotEmpty(message="Prenchimento obrigatorio")
	@Length(min=5, max=60, message="Campo deve ter entre 5 e 60 caracteres")
	private String nome;	
	
	private String status;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public VagaDTO() {
		
	}
	
	public VagaDTO(Vaga obj) {
	  id = obj.getId();
	  nome = obj.getNome();
	  status = obj.getStatus();
	}		
}
