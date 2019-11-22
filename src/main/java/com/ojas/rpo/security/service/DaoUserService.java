package com.ojas.rpo.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.accesstoken.AccessTokenDao;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.AccessToken;
import com.ojas.rpo.security.entity.User;

import java.util.Date;
import java.util.UUID;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DaoUserService implements UserService
{
    private UserDao userDao;

    private AccessTokenDao accessTokenDao;

    protected DaoUserService()
    {
        /* Reflection instantiation */
    }

    public DaoUserService(UserDao userDao, AccessTokenDao accessTokenDao)
    {
        this.userDao = userDao;
        this.accessTokenDao = accessTokenDao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return this.userDao.loadUserByUsername(username);
    }

    @Override
    @Transactional
    public User findUserByAccessToken(String accessTokenString)
    {
        AccessToken accessToken = this.accessTokenDao.findByToken(accessTokenString);

        if (null == accessToken) {
            return null;
        }

        if (accessToken.isExpired()) {
            this.accessTokenDao.delete(accessToken);
            System.out.println("token deleted=================");
            
            return null;
        }
        else
        {
        	System.out.println("update new date");
        	accessToken.setExpiry(new Date());
        	 this.accessTokenDao.save(accessToken);
        	
        }
        
        

        return accessToken.getUser();
    }

    @Override
    @Transactional
    public AccessToken createAccessToken(User user)
    {
    	if(user!=null){
    	 Date date = new Date();
    	 System.out.println("date>>>>>>>>>>>>>>>>"+date.toString());
         AccessToken accessToken = new AccessToken(user, UUID.randomUUID().toString(),date);
         return this.accessTokenDao.save(accessToken);
    	}
    	else{
    		 AccessToken accessToken = new AccessToken(user, "Bad credentials");
             return accessToken;
    	}
    }

}
