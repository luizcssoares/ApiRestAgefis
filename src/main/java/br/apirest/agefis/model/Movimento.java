package br.apirest.agefis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.apirest.agefis.enums.TipoOcupacao;

@Entity
@Table(name="movimento")
public class Movimento implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="idvaga")
	private Vaga vaga;
	
	private String numplaca;
					
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entrada;

	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
    private Date horaentrada;
	
	private TipoOcupacao tipo;
		
	@ManyToOne
	@JoinColumn(name="idconfig")
	private Config config;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date saida;
	
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
    private Date horasaida;
	
	private BigDecimal valorpago;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Date getSaida() {
		return saida;
	}

	public void setSaida(Date saida) {
		this.saida = saida;
	}

	public Date getHorasaida() {
		return horasaida;
	}

	public void setHorasaida(Date horasaida) {
		this.horasaida = horasaida;
	}

	public BigDecimal getValorpago() {
		return valorpago;
	}

	public void setValorpago(BigDecimal valorpago) {
		this.valorpago = valorpago;
	}
	
	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Date getHoraentrada() {
		return horaentrada;
	}

	public void setHoraentrada(Date horaentrada) {
		this.horaentrada = horaentrada;
	}

	public TipoOcupacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoOcupacao tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movimento other = (Movimento) obj;
		if (id != other.id)
			return false;
		return true;
	}
    
}
