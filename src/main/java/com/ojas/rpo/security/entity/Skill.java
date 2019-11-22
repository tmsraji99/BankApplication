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
@Table(name="skill")
@javax.persistence.Entity
public class Skill implements Entity
{
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Date date;

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

    @JsonView(JsonViews.Admin.class)
    public Long getId()
    {
        return this.id;
    }

	public void setId(Long id) {
		this.id = id;
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
