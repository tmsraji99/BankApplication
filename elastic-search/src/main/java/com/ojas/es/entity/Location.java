package com.ojas.es.entity;







import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import java.util.Date;

/**
 * JPA Annotated Pojo that represents a blog post.
 *
 * @author Philip W. Sorst <philip@sorst.net>
 */
@Table(name="location")
@javax.persistence.Entity
@Indexed
public class Location implements Entity
{
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Date date;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    @Column
    private String locationName;

    public Location()
    {
        this.date = new Date();
    }

   
    public Long getId()
    {
        return this.id;
    }

    public Date getDate()
    {
        return this.date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

   
  
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
