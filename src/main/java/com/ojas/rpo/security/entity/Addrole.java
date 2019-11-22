package com.ojas.rpo.security.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="addrole")
@javax.persistence.Entity
	public class Addrole implements Entity
	{
	    @Id
	    @GeneratedValue
	    private Long id;

		@Column
	    private Date date;

	    @Column
	    private String roleName;

	    public Addrole()
	    {
	        this.date = new Date();
	    }
	    public void setId(Long id) {
			this.id = id;
		}
	    @JsonView(JsonViews.Admin.class)
	    public Long getId()
	    {
	        return this.id;
	    }

	    @JsonView(JsonViews.User.class)
	    public Date getDate()
	    {
	        return this.date;
	    }

	    public void setDate(Date date)
	    {
	        this.date = date;
	    }

	   
	    @JsonView(JsonViews.User.class)
	    public String getroleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		@Override
	    public String toString()
	    {
	        return String.format("Location[%d, %s]", this.id,this.date, this.roleName);
	    }
	}


