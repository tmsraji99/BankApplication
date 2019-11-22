package com.ojas.rpo.security.entity;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.util.Date;

/**
 * JPA Annotated Pojo that represents a blog post.
 *
 * @author Philip W. Sorst <philip@sorst.net>
 */
@Table(name="services")
@javax.persistence.Entity
public class Services implements Entity {
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private Date date;

	@Column(unique=true)
	private String serviceName;
	
	@Column
	private String status;

	public Services() {
		this.date = new Date();
	}

	@JsonView(JsonViews.Admin.class)
	public Long getId() {
		return this.id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Services [id=" + id + ", date=" + date + ", serviceName=" + serviceName + "]";
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonView(JsonViews.User.class)
	public Date getDate() {
		return this.date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((serviceName == null) ? 0 : serviceName.hashCode());
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
		Services other = (Services) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (serviceName == null) {
			if (other.serviceName != null)
				return false;
		} else if (!serviceName.equals(other.serviceName))
			return false;
		return true;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}
