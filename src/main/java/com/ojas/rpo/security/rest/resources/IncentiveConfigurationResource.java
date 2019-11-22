package com.ojas.rpo.security.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.incentiveconfigs.IncentiveConfigDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.IncentiveConfigurations;
import com.ojas.rpo.security.entity.Response;

@Component
@Path("/incentiveConfig")
public class IncentiveConfigurationResource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IncentiveConfigDao incentiveConfigDao;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public @ResponseBody Response save(IncentiveConfigurations incentive) {

		if (incentive != null) {
			incentive = this.incentiveConfigDao.save(incentive);
			return new Response(ExceptionMessage.OK, incentive);
		} else {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAll")
	public @ResponseBody Response getIncentive() {
		this.logger.info("list()");
		List<IncentiveConfigurations> add = this.incentiveConfigDao.findAll();
		if (add == null || add.isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			return new Response(ExceptionMessage.OK, add);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get/{id}")
	public @ResponseBody Response getIncentive(@PathParam("id") Long id) {

		IncentiveConfigurations obj = this.incentiveConfigDao.find(id);
		if (obj == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {
			return new Response(ExceptionMessage.OK, obj);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update/{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, IncentiveConfigurations incentive) {

		IncentiveConfigurations obj = this.incentiveConfigDao.find(id);
		if (obj == null) {
			return new Response(ExceptionMessage.Not_Found, "Incentive not found to Update");
		} else {
			if (incentive.getAmount() != null) {
				obj.setAmount(incentive.getAmount());
			}
			if (incentive.getIs_countable() != null) {
				obj.setIs_countable(incentive.getIs_countable());
			}
			if (incentive.getMax_ctc() != null) {
				obj.setMax_ctc(incentive.getMax_ctc());
			}
			if (incentive.getMin_ctc() != null) {
				obj.setMin_ctc(incentive.getMin_ctc());
			}
			if (incentive.getRole() != null) {
				obj.setRole(incentive.getRole());
			}
			if (incentive.getRule() != null) {
				obj.setRule(incentive.getRule());
			}
			if (incentive.getTarget() != null) {
				obj.setTarget(incentive.getTarget());
			}
			if (incentive.getValueType() != null) {
				obj.setValueType(incentive.getValueType());
			}

			IncentiveConfigurations updated = incentiveConfigDao.save(obj);
			if (null != updated) {
				return new Response(ExceptionMessage.OK, incentive);
			} else {
				return new Response(ExceptionMessage.DataIsNotSaved, "Update operation failed");
			}
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public @ResponseBody Response delete(@PathParam("id") Long id) {

		IncentiveConfigurations obj = this.incentiveConfigDao.find(id);
		if (obj == null) {
			return new Response(ExceptionMessage.Not_Found, "No Incentive found to delete.");
		} else {
			this.incentiveConfigDao.delete(obj);
			return new Response(ExceptionMessage.OK, "Incentive Deleted");
		}
	}
}
