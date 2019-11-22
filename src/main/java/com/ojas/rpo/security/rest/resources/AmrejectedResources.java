package com.ojas.rpo.security.rest.resources;

import java.text.SimpleDateFormat;

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

import com.ojas.rpo.security.dao.Amrejected.AmrejectedDao;
import com.ojas.rpo.security.entity.Amrejected;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.GMailServer;
import com.ojas.rpo.security.util.ReadPropertiesFile;


@Component
@Path("/amreject")
public class AmrejectedResources {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AmrejectedDao amrejectedDao;

	

	public AmrejectedDao getAmrejectedDao() {
		return amrejectedDao;
	}

	public void setAmrejectedDao(AmrejectedDao amrejectedDao) {
		this.amrejectedDao = amrejectedDao;
	}

	@Autowired
	private ObjectMapper mapper;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Amrejected rejected) {
		System.out.println("==========inside post method====RPO");

		this.logger.info("create(): " + rejected);
		

		Amrejected am= this.amrejectedDao.save(rejected);
		if(am!=null)
		{
			EmailEntity emailEntity = ReadPropertiesFile.readConfig();//to read config file
			
			emailEntity.setMessagesubject("AM Rejceted feedback...");//add subject
			emailEntity.setTo(rejected.getUserEmail());//add to address
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			
			emailEntity.setMessageBody("<i>Dear, </i><B>" + rejected.getCandidateiname().toUpperCase() + "</B>" + "<br>" //add message body
					+ "Greetings from Ojas!!!" + "<br><br>" 
					+ " candidate Rejected reasion:"+ rejected.getStatusNodes()+"<br><br>"
					
					);

			GMailServer sender = new GMailServer(emailEntity);// create gmail server
			sender.sendMail(emailEntity);// send mail
			System.out.println("recutier Email Sent Succesfully...");

				
			 this.amrejectedDao.updateCandiate(rejected.getCandidateid(), rejected.getTypeofprocess());
			 return new Response(ExceptionMessage.DataIsNotSaved);
		}
	
		else
			return new Response(ExceptionMessage.StatusSuccess, am);
		
		
	}

}
