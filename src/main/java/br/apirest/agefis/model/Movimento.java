package br.apirest.agefis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="movimento")
public class Movimento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private long id;
	
	private long idocupacao;
	private long iddesocupacao;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdocupacao() {
		return idocupacao;
	}
	public void setIdocupacao(long idocupacao) {
		this.idocupacao = idocupacao;
	}
	public long getIddesocupacao() {
		return iddesocupacao;
	}
	public void setIddesocupacao(long iddesocupacao) {
		this.iddesocupacao = iddesocupacao;
	}
}
