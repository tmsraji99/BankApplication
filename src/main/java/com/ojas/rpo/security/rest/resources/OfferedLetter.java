package com.ojas.rpo.security.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.Offered.OfferedDao;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.InterviewFeedback;
import com.ojas.rpo.security.entity.Offered;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Resume;

@Component
@Path("/offeredleeter")
public class OfferedLetter {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OfferedDao offeredDao;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private Resume resume;
	
	 @POST
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public @ResponseBody  Response create(Offered of){
			System.out.println("==========inside post method====RPO");
			this.logger.info("create(): " + of);
			
			
				of.setTypeofProcess("waiting for joing");
				of= this.offeredDao.save(of);
			
			
			if(of!=null)
			{
				 this.offeredDao.updateCandiate(of.getCandidateId(), of.getTypeofProcess());
				 return new Response(ExceptionMessage.StatusSuccess,of);
			}
			else
			{
				return new Response(ExceptionMessage.DataIsNotSaved);
			}
		}
}
