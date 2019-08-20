package br.apirest.agefis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

/* ENTIDADE QUE REPRESENTA A VIEW DE MOVIMENTO(VwMovimento) COM DADOS DE 
   ENTRADA/SAIDA DAS VAGAS */
@Entity
@Table(name="ViewMovimento")
public class ViewMovimento  implements Serializable{
	
	@Id
	private long id;

	private long idvaga;
	
	private String numplaca; 
    
    @Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")    
	private Date entrada; 
    
    @Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date hora; 
    
    @Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date saida;  
    
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

	public String getNumplaca() {
		return numplaca;
	}

	public void setNumplaca(String numplaca) {
		this.numplaca = numplaca;
	}

	public Date getEntrada() {
		return entrada;
	}

	public void setEntrada(Date entrada) {
		this.entrada = entrada;
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}	
}
