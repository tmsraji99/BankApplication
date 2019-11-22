package com.ojas.rpo.security.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.GST.GSTDao;
import com.ojas.rpo.security.dao.location.SkillDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.GST;
import com.ojas.rpo.security.entity.InterviewDetails;
import com.ojas.rpo.security.entity.Location;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Resume;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.Skill;

@Component
@Path("/GST")
public class GSTResource {

	@Autowired
	private GSTDao gstDao;

	@Autowired
	private ObjectMapper mapper;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addGst")
	public @ResponseBody Response creategst(GST gst) {

		gst = this.gstDao.save(gst);
		if (gst == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {
			return new Response(ExceptionMessage.OK, gst);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update/{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, GST gstnew) {
		GST gstold = this.gstDao.find(id);
		if (gstold == null) {
			
			return new Response(ExceptionMessage.Not_Found, "GST not found");
			
		} 
		else {
              if(gstnew.getGst() != null ){
            	  gstold.setGst(gstnew.getGst());
            	  GST gstUpdated = gstDao.save(gstold);
      			if (gstUpdated == null) {
      				return new Response(ExceptionMessage.DataIsNotSaved, "GST not updated");
      			} else {
      				return new Response(ExceptionMessage.OK, gstUpdated);
      			}
			}else {
				
				return  new Response(ExceptionMessage.Bad_Request,"gst not updated");
			}
			
		}
	}
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    @Path("/getbyid/{id}")
	    public  Response findbyid(@PathParam("id") Long id)
	    {
	        GST gst = this.gstDao.find(id);
	        if (gst == null) {
	           return new Response(ExceptionMessage.Bad_Request,"gst id not found");
	        }else {
	        	
	        	return new Response(ExceptionMessage.OK,gst);
	        }
	        
	        
	    }
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	 @Path("/getAllGst")
	public @ResponseBody Response getAllGstDetails() {

		List<GST> gst = this.gstDao.findAll();

		if (gst.isEmpty()) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, gst);
	}
	

@DELETE
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/delete/{id}")
public Response delete(@PathParam("id") Long id)
{
   GST gst = this.gstDao.find(id);
   this.gstDao.delete(gst);
   
   if(gst == null) {
	  return new Response(ExceptionMessage.Not_Found,"GST not deleted");
   }else {
	   
	   return new Response(ExceptionMessage.OK,"GST deleted");
   }

}

}



