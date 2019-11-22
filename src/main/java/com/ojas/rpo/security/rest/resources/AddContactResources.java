package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
import com.ojas.rpo.security.dao.DashBoard.DashBoardInterface;
import com.ojas.rpo.security.dao.addClientContact.AddContactDao;
import com.ojas.rpo.security.entity.AddContact;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;

@Component
@Path("/addClientContact")
public class AddContactResources {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AddContactDao addClientContactDao;

	@Autowired
	private DashBoardInterface submissions;

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

		List<AddContact> addContact1 = this.addClientContactDao.findAll();
		if (addContact1 == null || addContact1.isEmpty()) {

			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (addContact1.size() > 0) {
			return new Response(ExceptionMessage.StatusSuccess, addContact1);
		} else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		AddContact addContact = this.addClientContactDao.find(id);

		if (addContact == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, addContact);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/contacs/{id}")
	public @ResponseBody Response getDetails(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		List<AddContact> addContact = this.addClientContactDao.findContactByClientId(id);

		if (addContact == null || addContact.isEmpty()) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, addContact);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(AddContact addContact) {
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + addContact);
		addContact.setStatus("Active");
		addContact.setLastUpdatedDate(new Date());
		if (addContact != null) {
			if (addContact.getClient() == null) {
				Response response = new Response(ExceptionMessage.DataIsNotSaved);
				response.setRes("invalied customer id");
				return response;
			}
		}
		if (addContact.getId() == null) {
			Integer res = this.addClientContactDao.findActiveContactByEmail(addContact.getClient().getId(),
					addContact.getEmail());
			if (res > 0) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}

		} else {
			Long id = null;
			List<Object[]> list = this.addClientContactDao.findActiveContactByEmailList(addContact.getClient().getId(),
					addContact.getEmail());

			for (Object[] obj : list) {
				id = Long.parseLong(obj[0] + "");

			}

			if (id != null && id.longValue() != addContact.getId().longValue()) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}

		}
		addContact = this.addClientContactDao.save(addContact);
		if (addContact == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, addContact);
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findContactByClientId/{id}")
	public @ResponseBody Response findContactByClientId(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		List<AddContact> addContactlist = this.addClientContactDao.findContactByClientId(id);

		if (addContactlist == null || addContactlist.isEmpty()) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else

		if (addContactlist.size() > 0) {
			return new Response(ExceptionMessage.StatusSuccess, addContactlist);
		} else {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findActiveContactByClientId/{id}/{role}/{userId}")
	public @ResponseBody Response findActiveContactByClientId(@PathParam("id") Long id, @PathParam("role") String role,
			@PathParam("userId") Long userId) {
		this.logger.info("read(id)");

		List<AddContact> addContactlist = this.addClientContactDao.findActiveContactByClientId(id, role, userId);

		if (addContactlist == null || addContactlist.isEmpty()) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else

		if (addContactlist.size() > 0) {
			return new Response(ExceptionMessage.StatusSuccess, addContactlist);
		} else {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findContactByBdmId/{id}/{role}/{pageNo}/{pageSize}")
	public @ResponseBody Response findContactByBdmId(@PathParam("id") Long id, @PathParam("role") String role,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortField") String sortField, @QueryParam("sortOrder") String sortOrder,
			@QueryParam("searchField") String searchField, @QueryParam("searchText") String searchText) {
		this.logger.info("read(id)");

		return this.addClientContactDao.findContactByBdmId(id, role, sortField, sortOrder, searchField, searchText,
				pageNo, pageSize);
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Path("/getDashboard/menu/{requirementId}") public List
	 * getdashBoarddata(@PathParam("requirementId") Long requirementId) {
	 * 
	 * List list = submissions.getData1(requirementId);
	 * 
	 * return list; }
	 * 
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Path("/getDashboard/{id}") public List
	 * getdashBoarddataForRequiter(@PathParam("id") Long id) {
	 * 
	 * List list = submissions.getData(id); return list; }
	 */

	/*
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Path("/findContactByBdmId/{id}/{role}") public @ResponseBody Response
	 * findContactByBdmId(@PathParam("id") Long id, @PathParam("role") String
	 * role) { this.logger.info("read(id)");
	 * 
	 * List<AddContact> addContactlist =
	 * this.addClientContactDao.findContactByBdmId(id, role);
	 * 
	 * if (addContactlist == null || addContactlist.isEmpty()) { return new
	 * Response(ExceptionMessage.ExcepcetdDataNotAvilable); } else
	 * 
	 * if (addContactlist.size() > 0) { return new
	 * Response(ExceptionMessage.StatusSuccess, addContactlist); } else { return
	 * new Response(ExceptionMessage.ExcepcetdDataNotAvilable); } }
	 */

}
