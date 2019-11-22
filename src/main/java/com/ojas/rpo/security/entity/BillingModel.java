package com.ojas.rpo.security.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name="billingmodel")
@javax.persistence.Entity
public class BillingModel implements Entity {
	public void setId(Long id) {
		this.id = id;
	}

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String billingModel;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public String getBillingModel() {
		return billingModel;
	}

	public void setBillingModel(String billingModel) {
		this.billingModel = billingModel;
	}

	@Override
	public String toString() {
		return "BillingModel [id=" + id + ", billingModel=" + billingModel + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((billingModel == null) ? 0 : billingModel.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BillingModel other = (BillingModel) obj;
		if (billingModel == null) {
			if (other.billingModel != null)
				return false;
		} else if (!billingModel.equals(other.billingModel))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
