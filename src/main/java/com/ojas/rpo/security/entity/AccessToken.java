package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Table(name="accesstoken")
@javax.persistence.Entity
public class AccessToken implements Entity
{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String token;

    @ManyToOne
    private User user;

    @Column
    private Date expiry;

    protected AccessToken()
    {
        /* Reflection instantiation */
    	this.expiry = new Date();
    }

    public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public AccessToken(User user, Date expiry) {
		super();
		this.user = user;
		this.expiry = expiry;
	}

	public AccessToken(Date expiry) {
		super();
		this.expiry = expiry;
	}

	public AccessToken(User user, String token)
    {
        this.user = user;
        this.token = token;
    }

    public AccessToken(User user, String token, Date expiry)
    {
    	this.user = user;
        this.token = token;
        this.expiry = expiry;
    }

    @Override
    public Long getId()
    {
        return this.id;
    }

    public String getToken()
    {
        return this.token;
    }

    public User getUser()
    {
        return this.user;
    }

    public Date getExpiry()
    {
        return this.expiry;
    }

    public boolean isExpired()
    {
    	System.out.println("expiry date>>>>>>>>>>>>>>>>>>>>>>>>>>>"+this.expiry);
        if (null == this.expiry) {
            return true;
        }
        
        
        else
        {
        	System.out.println("differ>>>>>>>>>>"+(System.currentTimeMillis()-this.expiry.getTime()));
        	
        	/*(120000>(System.currentTimeMillis()-this.expiry.getTime()))*/
        	
        	if(12000000>(System.currentTimeMillis()-this.expiry.getTime()))
        	{
        		System.out.println("NOT EXPIRE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        		return false;
        	}
        	else
        	{
        		return true;
        	}
        }

       // return this.expiry.getTime() > System.currentTimeMillis();
        
        
        
    }
}
