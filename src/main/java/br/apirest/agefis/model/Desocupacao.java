package br.apirest.agefis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/* ENTIDADE QUE REPRESENTA DADOS DA SAIDA DE VEICULOS DAS VAGAS */
@Entity
@Table(name="desocupacao")
public class Desocupacao implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private long id;
	
	private long idvaga;
		
	private long idconfig;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date saida;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
    private Date hora;
	
	private BigDecimal valor;
			
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdvaga() {
		return idvaga;
	}

	public void setIdvaga(long idvaga) {
		this.idvaga = idvaga;
	}
	
	public long getIdconfig() {
		return idconfig;
	}

	public void setIdconfig(long idconfig) {
		this.idconfig = idconfig;
	}			
	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Date getSaida() {
		return saida;
	}
	
	public void setSaida(Date saida) {
		this.saida = saida;
	}
}
