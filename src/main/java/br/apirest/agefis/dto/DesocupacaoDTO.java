package br.apirest.agefis.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.apirest.agefis.model.Vaga;

public class DesocupacaoDTO implements Serializable{
		
	private String numplaca;
	
	public String getNumplaca() {
		return numplaca;
	}

	public void setNumplaca(String numplaca) {
		this.numplaca = numplaca;
	}
			
	public DesocupacaoDTO() {
	  
	}		
}
