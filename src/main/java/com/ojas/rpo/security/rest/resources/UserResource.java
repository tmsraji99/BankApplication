package com.ojas.rpo.security.rest.resources;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.AccessToken;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.service.UserService;
import com.ojas.rpo.security.transfer.uesrAlpha;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Component
@Path("/user")
public class UserResource
{
    @Autowired
    private UserService userService;
   // private DaoUserService daoUserService;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authManager; 
    
    @Autowired
	private UserDao userDao;


    /**
     * Retrieves the currently logged in user.
     *
     * @return A transfer containing the username and the roles.
     */
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public uesrAlpha getUser()
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            throw new WebApplicationException(200);
        }
        User userDetails = (User) principal;
      
        return new uesrAlpha(userDetails.getUsername(), userDetails.getRole());
    }

   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getUserById/{id}")
    public uesrAlpha getUserById(@PathParam("id") Long id)
    {
       
      User user=	userDao.find(id);
      
        return new uesrAlpha(user.getUsername(), user.getRole());
    }

    /**
     * Authenticates a user and creates an access token.
     *
     * @param username The name of the user.
     * @param password The password of the user.
     * @return The generated access token.
     */
    
    
    @Path("authenticate")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AccessToken authenticate(@FormParam("username") String username, @FormParam("password") String password)
    {
    	System.out.println("-----------+++++++++++++++");
    	Object principal=null;
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        
        System.out.println("authenticationToken       :"+authenticationToken);
        System.out.println("authenticationToken isAuthenticated boolean      :"+ authenticationToken.isAuthenticated());
       try{
        Authentication authentication = this.authManager.authenticate(authenticationToken);
        System.out.println("authentication>>>>>>>>>>>>>>>>       :"+authentication.isAuthenticated());
        SecurityContextHolder.getContext().setAuthentication(authentication);
         principal = authentication.getPrincipal();
        System.out.println("principal       :"+principal);
        if (!(principal instanceof User)) {
        	System.out.println("not instance of user");
            throw new WebApplicationException(401);
        }
        return this.userService.createAccessToken((User) principal);
        
       }
       catch(Exception e)
       {
    	   System.out.println("exception                      :"+e);
    	   
    	   System.out.println("principal                      :"+(User)principal);
    	   
    	   return this.userService.createAccessToken((User) principal);
       }
       
        
    //    System.out.println("authentication isAuthenticated boolean      :"+  authentication.isAuthenticated());
      
    }

    
    private Map<String, Boolean> createRoleMap(UserDetails userDetails)
    {
        Map<String, Boolean> roles = new HashMap<String, Boolean>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.put(authority.getAuthority(), Boolean.TRUE);
        }

        return roles;
    }
}
