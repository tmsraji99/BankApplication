package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.billingModel.BillingModelDao;
import com.ojas.rpo.security.entity.BillingModel;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Path("/BillingModel")
public class BillingModelResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BillingModelDao billingModelDao;
	/*
	 * @Autowired private LocationDao locationDao;
	 */

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
		List<BillingModel> add = this.billingModelDao.findAll();
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
		BillingModel billingModel = this.billingModelDao.find(id);
		if (billingModel == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, billingModel);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(BillingModel billingModel) {
		System.out.println("==========inside post method====RPO");

		if (billingModel != null && !billingModel.getBillingModel().isEmpty()) {

			billingModel = this.billingModelDao.save(billingModel);

			return new Response(ExceptionMessage.StatusSuccess, billingModel);

		} else {

			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		}

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, BillingModel billingModel) {
		System.out.println("==========inside post method====RPO");
		billingModel.setId(id);
		this.logger.info("update(): " + billingModel);

		if (this.billingModelDao.save(billingModel) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, billingModel);
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public  @ResponseBody Response delete(@PathParam("id") Long id, BillingModel billingModel) {
		billingModel.setId(id);
		if (this.billingModelDao.delete(id) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, billingModel);
		
		
	}

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
