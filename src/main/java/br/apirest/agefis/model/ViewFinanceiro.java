package br.apirest.agefis.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/* ENTIDADE QUE REPRESENTA A VIEW (VwFINANCEIRO) COM DADOS FINANCEIROS DE USO DAS VAGAS */
@Entity
@Table(name="ViewFinanceiro")
public class ViewFinanceiro {

	@Id			
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entrada;
	
	private BigDecimal valor;
		
	public Date getEntrada() {
		return entrada;
	}

	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
