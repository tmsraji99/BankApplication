package com.ojas.rpo.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ojas.rpo.security.entity.AccessToken;
import com.ojas.rpo.security.entity.User;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface UserService extends UserDetailsService
{
    User findUserByAccessToken(String accessToken);

    AccessToken createAccessToken(User user);
}
