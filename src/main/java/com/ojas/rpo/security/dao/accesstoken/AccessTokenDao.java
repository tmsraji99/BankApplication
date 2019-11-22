package com.ojas.rpo.security.dao.accesstoken;
import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.AccessToken;
/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface AccessTokenDao extends Dao<AccessToken, Long>
{
    AccessToken findByToken(String accessTokenString);
}
