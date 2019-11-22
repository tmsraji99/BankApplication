package com.ojas.rpo.security.rest.resources;

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
import com.ojas.rpo.security.dao.blogpost.BlogPostDao;
import com.ojas.rpo.security.dao.hiringMode.HiringModeDao;
import com.ojas.rpo.security.entity.AddQualification;
import com.ojas.rpo.security.entity.BlogPost;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.HiringMode;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.List;

@Component
@Path("/typeofhiring")
public class HiringModeResource
{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HiringModeDao hiringModeDao;

    @Autowired
    private ObjectMapper mapper;

    @GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody  Response list() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<HiringMode> add=this.hiringModeDao.findAll();
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
		  HiringMode hiringMode = this.hiringModeDao.find(id);
		if (hiringMode == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		}
		else
		return new Response(ExceptionMessage.StatusSuccess,hiringMode);
	}
   
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody  Response create(HiringMode hiringMode){
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + hiringMode);

		if ( this.hiringModeDao.save(hiringMode) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}
		else
		return new Response(ExceptionMessage.StatusSuccess,hiringMode);
	}
	  
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
	public @ResponseBody  Response update(@PathParam("id") Long id,  HiringMode hiringMode){
		System.out.println("==========inside post method====RPO");
		hiringMode.setId(id);
		this.logger.info("update(): " + hiringMode);
		

		if ( this.hiringModeDao.save(hiringMode) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}
		else
		return new Response(ExceptionMessage.StatusSuccess,hiringMode);
	}

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public HiringMode delete(@PathParam("id") Long id)
    {
        this.logger.info("delete(id)");
        HiringMode hiringMode= hiringModeDao.find(id);
        hiringMode.setStatus("inactive");
		return this.hiringModeDao.save(hiringMode);
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
