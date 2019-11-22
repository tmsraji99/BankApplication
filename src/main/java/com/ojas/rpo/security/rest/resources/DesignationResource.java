package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
import com.ojas.rpo.security.dao.designation.DesignationDao;
import com.ojas.rpo.security.entity.AddQualification;
import com.ojas.rpo.security.entity.BlogPost;
import com.ojas.rpo.security.entity.Designation;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.util.DateParsing;

@Component
@Path("/designation")
public class DesignationResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DesignationDao designationDao;

    @Autowired
    private ObjectMapper mapper;
    

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody  Response create(Designation designation){
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + designation);
		
		String s=designation.getDesignation();
		String fs=DateParsing.textConvertor(s);
		designation.setDesignation(fs);

		if ( this.designationDao.save(designation) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}
		else
		return new Response(ExceptionMessage.StatusSuccess,designation);
	}

    
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
		List<Designation> add=this.designationDao.findAll();
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
		 Designation designation = this.designationDao.find(id);
		if (designation == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		}
		else
		return new Response(ExceptionMessage.StatusSuccess,designation);
	}
	
	 @POST
	    @Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Path("{id}")
		public @ResponseBody  Response designation(@PathParam("id") Long id,Designation designation){
			System.out.println("==========inside post method====RPO");
			designation.setId(id);
			this.logger.info("update(): " + designation);
			

			if ( this.designationDao.save(designation) == null) {
				return new Response(ExceptionMessage.DataIsNotSaved);
			}
			else
			return new Response(ExceptionMessage.StatusSuccess,designation);
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
