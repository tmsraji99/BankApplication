package com.ojas.recruitex.rest.resources;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.ojas.recruitex.entity.JobOpening;
import com.ojas.recruitex.response.Response;

@Configuration
@ComponentScan(basePackages = "com.ojas.recruitex")
@Controller
@Path("/job")
public class JobOpeningResources {

	@POST
	@Path("/savejob")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response<JobOpening> saveJob(@Valid JobOpening jobOpening, BindingResult result) {

		Response<JobOpening> response = new Response<JobOpening>();

		response.setT(jobOpening);
		return response;

	}

	private void validateJobOpening(JobOpening jobOpening, BindingResult bindingResult) {

		if (jobOpening.getCity().equals(null))
			bindingResult.addError(new ObjectError("Job Opening", "no city Information"));
	}

}
