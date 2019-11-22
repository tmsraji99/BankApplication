package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
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
import com.ojas.rpo.security.dao.addressdetails.AddressDetailsDao;
import com.ojas.rpo.security.entity.AddressDetails;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;

@Component
@Path("/addressdetails")
public class AddressDetailsResources {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AddressDetailsDao addressDetailsDao;

	@Autowired
	private ObjectMapper mapper;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getdata() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<AddressDetails> add = this.addressDetailsDao.findAll();
		if (add == null || add.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (add.size() > 0) {
			return new Response(ExceptionMessage.StatusSuccess, add);
		} else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		AddressDetails addressDetails = this.addressDetailsDao.find(id);
		if (addressDetails == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, addressDetails);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/readbasedoncpid/{cpid}")
	public @ResponseBody Response readbasedcpid(@PathParam("cpid") Long cpid) {
		this.logger.info("read(id)");
		List<AddressDetails> addressDetails = this.addressDetailsDao.findBycpId(cpid);
		if (addressDetails == null || addressDetails.isEmpty()) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, addressDetails);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(AddressDetails addressdetails)  {
		Response response=null;
		if(addressdetails.getCid()==null) {
			response = new Response(ExceptionMessage.DataIsEmpty);
			response.setRes("Invalid customer id");
		}
		addressdetails = this.addressDetailsDao.save(addressdetails);
		 response = new Response(ExceptionMessage.StatusSuccess, addressdetails);
		response.setRes("Address Details Added Successfully");

		if (addressdetails == null) {

			response = new Response(ExceptionMessage.DataIsEmpty);
			response.setRes("Invalid Address Details");
		
	}
		return response;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id,  AddressDetails addressdetails) {
		System.out.println("==========inside post method====RPO");
		addressdetails.setId(id);
		Response response = null;
		if(addressdetails.getCid()==null) {
			response = new Response(ExceptionMessage.DataIsEmpty);
			response.setRes("Invalid customer id");
		}
		if (this.addressDetailsDao.save(addressdetails) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {
			response = new Response(ExceptionMessage.StatusSuccess, addressdetails);
			response.setRes("AddressDetails Details Updated Successfully ");
		}

		return response;
	}
	

	/*
	 * @POST
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Path("{id}") public @ResponseBody Response create(@PathParam("id") Long id,
	 * AddressDetails addressdetails) {
	 * System.out.println("==========inside post method====RPO");
	 * addressdetails.setId(id); this.logger.info("update(): " + addressdetails);
	 * 
	 * if (this.addressDetailsDao.save(addressdetails) == null) { return new
	 * Response(ExceptionMessage.DataIsNotSaved); } else return new
	 * Response(ExceptionMessage.StatusSuccess, addressdetails); }
	 */

	/*
	 * @POST
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON) public @ResponseBody Response
	 * create(AddressDetails addressdetails) {
	 * System.out.println("==========inside post method====RPO");
	 * this.logger.info("create(): " + addressdetails);
	 * 
	 * if (this.addressDetailsDao.save(addressdetails) == null) { return new
	 * Response(ExceptionMessage.DataIsNotSaved); } else return new
	 * Response(ExceptionMessage.StatusSuccess, addressdetails); }
	 */
	
	


	private boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		return userDetails.getAuthorities().contains(Role.ADMIN);
	}
}