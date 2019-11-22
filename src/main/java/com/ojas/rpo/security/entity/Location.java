package com.ojas.rpo.security.entity;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * JPA Annotated Pojo that represents a blog post.
 *
 * @author Philip W. Sorst <philip@sorst.net>
 */
@Table(name="location")
@javax.persistence.Entity
public class Location implements Entity
{
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Date date;

    @Column
    private String locationName;

    public Location()
    {
        this.date = new Date();
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
    public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	@Override
    public String toString()
    {
        return String.format("Location[%d, %s]", this.id,this.date, this.locationName);
    }

	public void setId(Long id) {
		this.id = id;
	}
}
