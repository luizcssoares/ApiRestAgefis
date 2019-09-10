package br.apirest.agefis.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.apirest.agefis.model.Vaga;

public class OcupacaoDTO implements Serializable{
		
	private String numplaca;
	private long   idvaga;
	private String tipo;
	
	public String getNumplaca() {
		return numplaca;
	}

	public void setNumplaca(String numplaca) {
		this.numplaca = numplaca;
	}

	public long getIdvaga() {
		return idvaga;
	}

	public void setIdvaga(long idvaga) {
		this.idvaga = idvaga;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
			
	public OcupacaoDTO() {
	  
	}		
}
