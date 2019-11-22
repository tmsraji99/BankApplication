package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
import com.ojas.rpo.security.dao.status.StatusDao;
import com.ojas.rpo.security.entity.AddQualification;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.Status;

@Component
@Path("/addstatus")
public class StatusResource {
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());

	    @Autowired
	    private StatusDao statusDao;

	    @Autowired
	    private ObjectMapper mapper;

	  
     	@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public @ResponseBody  Response list() throws IOException {
			this.logger.info("list()");
			ObjectWriter viewWriter;
			if (this.isAdmin()) {
				viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
			} else {
				viewWriter = this.mapper.writerWithView(JsonViews.User.class);
			}
			List<Status> add=this.statusDao.findAll();
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
    	 @Path("/findstatusforc2h")
		public @ResponseBody  Response listfindstatusforc2h() throws IOException {
			this.logger.info("list()");
			ObjectWriter viewWriter;
			
			List<Status> add= new ArrayList<Status>();
			Status status= new Status();
			status.setStatus("Document Collection");
			Status status1= new Status();
			status1.setStatus("Offer Release");
			add.add(status);
			add.add(status1);
			return new Response(ExceptionMessage.StatusSuccess,add);
		}
    	
    	
    	@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
    	 @Path("/findstatus")
		public @ResponseBody  Response listfindstatus() throws IOException {
			this.logger.info("list()");
			
			List<Status> add= new ArrayList<Status>();
			Status status= new Status();
			status.setStatus("Process for Interview");
			status.setDisplayName("Schedule for Interview");
			add.add(status);
			Status status1= new Status();
			status1.setStatus("Hold");
			status1.setDisplayName("Hold");
			add.add(status1);
			
			
			return new Response(ExceptionMessage.StatusSuccess,add);
		}
     	
     	
        @SuppressWarnings("unused")
     	@GET
     	@Produces(MediaType.APPLICATION_JSON)
        @Path("/find/{roleStatus}/{procestype}")
	    public @ResponseBody  Response   list(@PathParam("roleStatus") String roleStatus,@PathParam("procestype") String procestype) throws IOException    {
	        this.logger.info("list()");
	        //System.out.println("vroleName==================="+roleName);
	        ObjectWriter viewWriter;
	        if (this.isAdmin()) {
	            viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
	        } else
	        {
	            viewWriter = this.mapper.writerWithView(JsonViews.User.class);
	        }
	        List<Status> statusLst=this.statusDao.findAll();
	        List<Status> newStatusList = null;
     		
     	        
     	        if (roleStatus.equals("BDM")) {// is AM
     			newStatusList = new ArrayList<Status>();
     			for (Status s : statusLst) {
     				String satu = s.getRoleName();
     				System.out.println(satu);
     				if (satu.equals("BDM")) {
     					newStatusList.add(s);
     				}
     			} // for closing
     		}else if(roleStatus.equals("recruiter")){// is recruiter
     	    	   newStatusList=new ArrayList<Status>();
     		        for(Status s:statusLst){
     		        	String satu=s.getRoleName();
     		        	System.out.println(satu);
     		        	if(satu.equals("recruiter")){
     		        		newStatusList.add(s);
     		        	}
     		        }//for closing
     	       }
     	        
     	        
     	       System.out.println( "lst----------------"+newStatusList);
     	       
     /*	   if(procestype.equals("Submit to Lead")){
   	    	   newStatusList=new ArrayList<Status>();
   	       for(Status s:statusLst){
   	        	String satu=s.getStatus();
   	        	System.out.println(satu);
   	        	if(satu.equals("Submit to Lead")){
   	        		newStatusList.add(s);
   	        	}
   	        }//f
   	       }
   	         
     	  else*/
     		  if(procestype.equals("Pending Review")){
   		    	   newStatusList=new ArrayList<Status>();
   		       for(Status s:statusLst){
   		        	String satu=s.getStatus();
   		        	System.out.println(satu);
   		        	if(satu.equals("Query")||satu.equals("Profile has been Accepted by Lead")||satu.equals("Rejected"))
   		        	{
   		        		newStatusList.add(s);
   		        	}
   		            }//f
   		       }
   	        else  if (procestype.equals("Profile has been Accepted by Lead")){
   		    	   newStatusList=new ArrayList<Status>();
   		       for(Status s:statusLst){
   		        	String satu=s.getStatus();
   		        	System.out.println(satu);
   		        	if(satu.equals("Submitted to Customer")){
   		        		newStatusList.add(s);
   		        	}
   		            }//f
   		       }
   	    
   	        else  if (procestype.equals("Submitted to Customer")){
   		    	   newStatusList=new ArrayList<Status>();
   		       for(Status s:statusLst){
   		        	String satu=s.getStatus();
   		        	System.out.println(satu);
   		        	if(satu.equals("Customer Shortlisted")  || satu.equals("Customer Rejected")){
   		        		newStatusList.add(s);
   		        	}
   		            }//f
   		       }
   	         
   	       
   	        else  if (procestype.equals("Process for Interview")){
   		    	   newStatusList=new ArrayList<Status>();
   		       for(Status s:statusLst){
   		    	   System.out.println("Process Interview");
   		        	String satu=s.getStatus();
   		        	System.out.println(satu);
   		        	if(satu.equals("interview schedule")){
   		        		newStatusList.add(s);
   		        	}
   		            }//f
   		       }
   	        else  if (procestype.equals("Waiting for Interview Feedback")){
   		    	   newStatusList=new ArrayList<Status>();
   		    	   
   		       for(Status s:statusLst){
   		        	String satu=s.getStatus();
   		        	System.out.println(satu);
   		        	if(satu.equals("Customer feedback")){
   		        		newStatusList.add(s);
   		        		System.out.println("Matched");
   		        	}
   		            }//f
   		       }
   	     else  if (procestype.equals("Candidate OfferAccepted")){
		    	   newStatusList=new ArrayList<Status>();
		    	   
		       for(Status s:statusLst){
		        	String satu=s.getStatus();
		        	System.out.println(satu);
		        	if(satu.equals("Offer Release")){
		        		newStatusList.add(s);
		        		System.out.println("Matched");
		        	}
		            }//f
		       }
   	        else  if (procestype.equals("Feedback for Customer")){
   		    	   newStatusList=new ArrayList<Status>();
   		       for(Status s:statusLst){
   		        	String satu=s.getStatus();
   		        	System.out.println(satu);
   		        	if(satu.equals("Interview FeedBack")){
   		        		newStatusList.add(s);
   		        	}
   		            }//f
   		       }
   	         
   	        else  if (procestype.equals("Query Answer")){
   		    	   newStatusList=new ArrayList<Status>();
   		       for(Status s:statusLst){
   		        	String satu=s.getStatus();
   		        	System.out.println(satu);
   		        	if( satu.equals("Profile has been Accepted by Lead")||satu.equals("Rejected")){
   		        		newStatusList.add(s);
   		        	}
   		            }//f
   		       }
   	     else  if (procestype.equals("waiting for Offer release")){
		    	   newStatusList=new ArrayList<Status>();
		       for(Status s:statusLst){
		        	String satu=s.getStatus();
		        	System.out.println(satu);
		        	/*if( satu.equals("Offered")){
		        		newStatusList.add(s);
		        	}*/
		        	if( satu.equals("UploadDocuments")){
		        		newStatusList.add(s);
		        	}
		            }//f
		       }
   	         
     		  
   	
     		  
   	  else  if (procestype.equals("Offer Pending")){
   	   newStatusList=new ArrayList<Status>();
      for(Status s:statusLst){
       	String satu=s.getStatus();
       	System.out.println(satu);
       	/*if( satu.equals("Offered")){
       		newStatusList.add(s);
       	}*/
       	if( satu.equals("Offer Release")){
       		newStatusList.add(s);
       	}
           }//f
      }
   	  else  if (procestype.equals("Offer Released")){
      	   newStatusList=new ArrayList<Status>();
         for(Status s:statusLst){
          	String satu=s.getStatus();
          	System.out.println(satu);
          	/*if( satu.equals("Offered")){
          		newStatusList.add(s);
          	}*/
          	if( satu.equals("Offer Status")){
          		newStatusList.add(s);
          	}
              }//f
         }
        		  
   		
     	       
     	      if (newStatusList == null) {
  				//throw new WebApplicationException(404);
  				return new Response(ExceptionMessage.DataIsEmpty);
  			}
  			else 
  				
  				if(newStatusList.size()>0){
  					return new Response(ExceptionMessage.StatusSuccess,newStatusList);
  				}
  				else{
  				
  					return new Response(ExceptionMessage.DataIsEmpty,"200","NOEXCEPTION","NULL");
  				}	
     	    }
     	
     	
  		  @GET
			@Produces(MediaType.APPLICATION_JSON)
			@Path("{id}")
			public  @ResponseBody  Response read(@PathParam("id") Long id) {

		        Status status = this.statusDao.find(id);
				if (status == null) {
					return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
				}
				else
				return new Response(ExceptionMessage.StatusSuccess,status);
			}
	   
	    
	    @POST
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public @ResponseBody  Response create(Status status){
			System.out.println("==========inside post method====RPO");
			this.logger.info("create(): " + status);

			if ( this.statusDao.save(status) == null) {
				return new Response(ExceptionMessage.DataIsNotSaved);
			}
			else
			return new Response(ExceptionMessage.StatusSuccess,status);
		}
		   

	    @POST
	    @Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Path("{id}")
		public @ResponseBody  Response  update(@PathParam("id") Long id, Status status){
			System.out.println("==========inside post method====RPO");
			status.setId(id);
			this.logger.info("update(): " + status);
			

			if ( this.statusDao.save(status) == null) {
				return new Response(ExceptionMessage.DataIsNotSaved);
			}
			else
			return new Response(ExceptionMessage.StatusSuccess,status);
		}
	    
	    
	    
	    @DELETE
	    @Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Path("{id}")
	    public void delete(@PathParam("id") Long id, Status status)
	    {
	    	status.setId(id);

	        this.statusDao.delete(id);
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
