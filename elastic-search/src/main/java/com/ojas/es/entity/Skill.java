package com.ojas.es.entity;







import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * JPA Annotated Pojo that represents a blog post.
 *
 * @author Philip W. Sorst <philip@sorst.net>
 */
@Table(name="skill")
@javax.persistence.Entity
@Indexed
public class Skill implements Entity
{
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Date date;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    @Column(unique=true)
    private String skillName;
    @Column
    private Boolean flag;
  
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Skill()
    {
        this.date = new Date();
    }

  
    public Long getId()
    {
        return this.id;
    }

	public void setId(Long id) {
		this.id = id;
	}
    
    public Date getDate()
    {
        return this.date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
 
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	@Override
    public String toString()
    {
        return String.format("Skill[%d, %s]", this.id,this.date, this.skillName);
    }

}
