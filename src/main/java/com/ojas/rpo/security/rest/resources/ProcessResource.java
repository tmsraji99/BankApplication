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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.typeofprocess.ProcessDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Processtype;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.util.MailGenaration;
import com.ojas.rpo.security.util.ProcesstypeUtil;

@Component
@Path("/ptype")
public class ProcessResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProcessDao processdao;

	@Autowired
	private ObjectMapper mapper;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(ProcesstypeUtil processUtil) {
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + processUtil);
		Processtype processtype = new Processtype();
		BeanUtils.copyProperties(processUtil, processtype);
		Processtype am = null;
		this.logger.info("create(): " + processtype);
		if (processtype.getTypeofprocess().equalsIgnoreCase("Customer Shortlisted")) {
			processtype.setTypeofprocess("Process for Interview");
			am = this.processdao.save(processtype);
		}
		if (am != null) {
			this.processdao.updateCandiate(processtype.getCandidateid(), processtype.getTypeofprocess(),
					 processtype.getRequirementId(),processtype.getUserId());
			return new Response(ExceptionMessage.DataIsNotSaved);
		}

		else
			return new Response(ExceptionMessage.StatusSuccess, processtype);
	}

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
		List<Processtype> add = this.processdao.findAll();
		if (add == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (add.size() > 0) {
			return new Response(ExceptionMessage.StatusSuccess, add);
		} else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response process(@PathParam("id") Long id, Processtype process) {
		System.out.println("==========inside post method====RPO");
		process.setId(id);
		this.logger.info("update(): " + process);

		if (this.processdao.save(process) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, process);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		Processtype process = this.processdao.find(id);
		if (process == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, process);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/basedid/{id}")
	public @ResponseBody Response readId(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		Processtype amquery = this.processdao.findById(id);
		if (amquery == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, amquery);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("mail/{id}")
	public @ResponseBody Response updatestatus(@PathParam("id") Long id) {

		Processtype processtype = this.processdao.findById(id);

		processtype.setTypeofprocess("Waiting for Interview Feedback");
		if (processtype != null) {
			this.processdao.updateCandiate(id, processtype.getTypeofprocess(),processtype.getUserId(),processtype.getRequirementId());
			new MailGenaration().sendSpecificMail(processtype);
			return new Response(ExceptionMessage.StatusSuccess, processtype);
		}

		else
			return new Response(ExceptionMessage.DataIsNotSaved);

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
