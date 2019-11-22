package com.ojas.rpo.security.rest.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.bank.BankDao;
import com.ojas.rpo.security.entity.BankAndAddressDetails;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.GST;
import com.ojas.rpo.security.entity.Response;

@Component
@Path("/bankDetails")
public class BankResource {

	@Autowired
	private BankDao bankdao;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addBank")
	public  Response create(BankAndAddressDetails BankAndAddressDetails,@Context HttpServletRequest request) {
		BankAndAddressDetails bank = this.bankdao.save(BankAndAddressDetails);
		if(bank == null) {
			return new Response(ExceptionMessage.DataIsNotSaved,"Bank Details Not Saved");
		}else {
			return new Response(ExceptionMessage.OK,"bank");
		}
	}
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateBank/{id}")
	public Response update(@PathParam ("id")Long id,BankAndAddressDetails bank ) {
		 bank.setId(id);
		if(this.bankdao.save(bank) == null) {
			return new Response(ExceptionMessage.Not_Found,"Bank Details not updated");
		}else {
			 return new Response(ExceptionMessage.OK,"bank");
		}
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getbyid/{id}")
		 public  Response findbyid(@PathParam("id") Long id)
		    {
			 BankAndAddressDetails bank = this.bankdao.find(id);
		        if (bank == null) {
		           return new Response(ExceptionMessage.Bad_Request,"Bank id not found");
		        }else {
		        	
		        	return new Response(ExceptionMessage.OK,bank);
		        }
		        
		    }

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		@Path("/getAll")
		public Response getAllBankDetails() {
			
			List<BankAndAddressDetails> bank = this.bankdao.findAll();
			
			if (bank.isEmpty()) {
				return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
			} else
				return new Response(ExceptionMessage.StatusSuccess, bank);
		}
		
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public Response delete(@PathParam("id")Long id) {
		BankAndAddressDetails bank = this.bankdao.find(id);
		this.bankdao.delete(bank);
		if(bank == null) {
			return new Response(ExceptionMessage.Not_Found,"Bank Details Not Deleted");
		}else {
			return new Response(ExceptionMessage.OK,"Bank Details Deleted");
		}
		
	}
	
}
