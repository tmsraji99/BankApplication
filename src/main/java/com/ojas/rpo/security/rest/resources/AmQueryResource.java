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
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.Amquery.AmQuryDao;
import com.ojas.rpo.security.entity.Amquery;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;

@Component
@Path("/amquery")
public class AmQueryResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AmQuryDao amQuryDao;

	@Autowired
	private ObjectMapper mapper;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Amquery query) {
		System.out.println("==========inside post method====RPO");
		Amquery am = null;
		this.logger.info("create(): " + query);
		if (query.getTypeofprocess().equalsIgnoreCase("Query")) {
			query.setTypeofprocess("onHold");
			am = this.amQuryDao.save(query);

		}
		if (am != null) {
			this.amQuryDao.updateCandiate(query.getCandidateid(), query.getTypeofprocess());
			return new Response(ExceptionMessage.DataIsNotSaved);

		} else
			return new Response(ExceptionMessage.StatusSuccess, am);

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response idUpdate(@PathParam("id") Long id, Amquery amquery) {
		amquery.setCandidateid(id);
		this.logger.info("update(): " + amquery);
		Amquery am = null;

		if (amquery.getTypeofprocess().equalsIgnoreCase("onHold")) {
			amquery.setTypeofprocess("Query  Answer");
			am = this.amQuryDao.save(amquery);
		}
		if (am != null) {
			this.amQuryDao.updateCandiate(amquery.getCandidateid(), amquery.getTypeofprocess());
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, am);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response list() throws IOException {
		this.logger.info("list()");

		ObjectWriter viewWriter;

		List<Amquery> qery = this.amQuryDao.findAll();
		if (qery == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else

			return new Response(ExceptionMessage.StatusSuccess, qery);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response readId(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		Amquery amquery = this.amQuryDao.findById(id);
		if (amquery == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, amquery);

	}
}
