package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
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
import com.ojas.rpo.security.dao.companyAddressinfo.CompanyAddressInfoDao;
import com.ojas.rpo.security.dao.companytaxinfo.CompanyTaxInfoDao;
import com.ojas.rpo.security.entity.AddQualification;
import com.ojas.rpo.security.entity.BankAndAddressDetails;
import com.ojas.rpo.security.entity.CompanyTaxInfo;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;

@Component
@Path("/invoice")
public class Invoice {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompanyTaxInfoDao companyTaxInfoDao;
    @Autowired
    private CompanyAddressInfoDao companyAddressInfoDao;
    @Autowired
    private ObjectMapper mapper;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody  Response create(CompanyTaxInfo companyTaxInfo){
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + companyTaxInfo);

		if ( this.companyTaxInfoDao.save(companyTaxInfo) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}
		else
		return new Response(ExceptionMessage.StatusSuccess,companyTaxInfo);
	}
	 
    @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("/addresspost")
	public @ResponseBody  Response create(BankAndAddressDetails bankAndAddressDetails){
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + bankAndAddressDetails);

		if ( this.companyAddressInfoDao.save(bankAndAddressDetails) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}
		else
		return new Response(ExceptionMessage.StatusSuccess,bankAndAddressDetails);
	}

    
 	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody  Response getdata() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<CompanyTaxInfo> add=this.companyTaxInfoDao.findAll();
		if (add == null) {
			//throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		else 
			
			if(add.size()>0){
				return new Response(ExceptionMessage.StatusSuccess,add);
			}
			else{
			
				return new Response(ExceptionMessage.DataIsEmpty,"200","NOEXCEPTION","NULL");
			}	
	}
  
  
    @GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("/address")
	public @ResponseBody  Response getAddress() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<BankAndAddressDetails> bankAndAddressDetails=this.companyAddressInfoDao.findAll();
		if (bankAndAddressDetails == null) {
			//throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		else 
			
			if(bankAndAddressDetails.size()>0){
				return new Response(ExceptionMessage.StatusSuccess,bankAndAddressDetails);
			}
			else{
			
				return new Response(ExceptionMessage.DataIsEmpty,"200","NOEXCEPTION","NULL");
			}	
	}
  
    
    
    private boolean isAdmin()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;

        return userDetails.getAuthorities().contains(Role.ADMIN);
    }
}
