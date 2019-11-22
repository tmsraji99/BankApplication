package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.location.ServicesDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.Services;
import com.ojas.rpo.security.entity.Skill;

@Component
@Path("/services")
public class ServicesResource
{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ServicesDao servicesDao;
 /*   @Autowired
    private LocationDao locationDao;*/
    

    @Autowired
    private ObjectMapper mapper;
    
    @GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody  Response getdata() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<Services> add=this.servicesDao.findAll();
		if (add == null) {
			//throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		else 
			
			if(add.size()>0){
				return new Response(ExceptionMessage.StatusSuccess,add);
			}
			else{
			
				return new Response(ExceptionMessage.DataIsEmpty,"200","NOEXCEPTION","NULL");
			}	
	}
    
    
    @GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public  @ResponseBody  Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		Services services = this.servicesDao.find(id);
		if (services == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		}
		else
		return new Response(ExceptionMessage.StatusSuccess,services);
	}



	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody  Response create(Services services)
    {
		System.out.println("==========inside post method====RPO");
		
		this.logger.info("create(): " + services);
		
	
		
		if ( this.servicesDao.save(services) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}
		else
		return new Response(ExceptionMessage.StatusSuccess,services);
	}

 
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
	public @ResponseBody  Response update(@PathParam("id") Long id, Services services){
		System.out.println("==========inside post method====RPO");
		services.setId(id);
		this.logger.info("update(): " + services);
		

		if ( this.servicesDao.save(services) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}
		else
		return new Response(ExceptionMessage.StatusSuccess,services);
	}
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public void delete(@PathParam("id") Long id, Services services)
    {
	 services.setId(id);
    	//this.logger.info("read(id)");

        this.servicesDao.delete(id);
    }

   

    private boolean isAdmin()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;

        return userDetails.getAuthorities().contains(Role.ADMIN);
    }
}
