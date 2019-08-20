package br.apirest.agefis.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/* ENTIDADE QUE REPRESENTA OS DADOS DAS VAGAS */
@Entity
@Table(name="vaga")
public class Vaga  implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private long id;
	
	private String nome;
	
	private String status; // D - Desocupada / O - Ocupada 
	
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
}
