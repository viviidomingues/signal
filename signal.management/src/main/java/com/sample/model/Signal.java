package com.sample.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sample.dao.BooleanTrueFalseConverter;

@Entity
@Table(name = "signais")
@SuppressWarnings("serial")
public class Signal implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Transient
	private SignalGroup signalGroup;

	@Column(name = "descricao")
	private String address;

	@Column(name = "informacoes")
	private String details;
	
	@Column(name="tipo")
	private String tipo;
	
	@Column(name="taxa")
	private int taxa;
	
	@Column(name="plcid")
	private int plcid;
	
	@Column(name="selecao")
	private String selecao;
	
	@Column(name="valor_ativacao")
	private int valor_ativacao;
	
	@Column(name="must_delete")
	private String must_delete;

	@Column(name = "ativo", columnDefinition = "char")
	@Convert(converter = BooleanTrueFalseConverter.class)
	private Boolean active;

	public SignalGroup getSignalGroup() {
		return this.signalGroup;
	}

	public void setSignalGroup(SignalGroup signalGroup) {
		this.signalGroup = signalGroup;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((signalGroup == null) ? 0 : signalGroup.hashCode());
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
		Signal other = (Signal) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (signalGroup == null) {
			if (other.signalGroup != null)
				return false;
		} else if (!signalGroup.equals(other.signalGroup))
			return false;
		return true;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getTaxa() {
		return taxa;
	}

	public void setTaxa(int taxa) {
		this.taxa = taxa;
	}

	public int getPlcid() {
		return plcid;
	}

	public void setPlcid(int plcid) {
		this.plcid = plcid;
	}

	public String getSelecao() {
		return selecao;
	}

	public void setSelecao(String selecao) {
		this.selecao = selecao;
	}

	public int getValor_ativacao() {
		return valor_ativacao;
	}

	public void setValor_ativacao(int valor_ativacao) {
		this.valor_ativacao = valor_ativacao;
	}

	public String getMust_delete() {
		return must_delete;
	}

	public void setMust_delete(String must_delete) {
		this.must_delete = must_delete;
	}

}
