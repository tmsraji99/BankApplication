package com.ojas.rpo.security.dao.DashBoard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Submissions;

public class submissionsImpl extends JpaDao<Submissions, Long> {

	public submissionsImpl() {
		super(Submissions.class);
	}

	/*
	 * EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
	 * "Eclipselink_JPA" ); EntityManager entitymanager =
	 * emfactory.createEntityManager();
	 */
	
	
	
	public List getData1(Long requirementID) {
		Query q = getEntityManager().createNativeQuery(
				 " SELECT  COUNT(*)  noofprofilessummited," +"bdmreq.id requirementId,"+"assign.target," +"bdmreq.nameOfRequirement," +"candidate.candidateStatus, "
				 		+ "USER.name, "
	    		  +" clients.clientName " 
	    		  +" FROM   bdmreq  bdmreq, "
	    		  +" candidate  candidate, "
	    		  +" assign assign , "
	    		         +" user USER, "  
	    		          +" recuritermap recuritermap, "
	    		          +" CLIENT clients " 
	    		          +" WHERE bdmreq.id=candidate.bdmReq_id "
	    		          +"  AND  bdmreq.id=?"
	    		                    +" AND assign.bdmReq_id=bdmreq.id "
	    		                    +" AND recuritermap.recuriter_ID=USER.id "
	    		                    +" AND assign.id=recuritermap.Assign_ID " 
	    		                    +"AND candidate.user_id=recuritermap.recuriter_ID"
	    		                    +" AND clients.id = bdmreq.client_id GROUP BY "
	    		                     +"bdmreq.id");

		q.setParameter(1, requirementID);
		List<Object[]> list = q.getResultList();
		Submissions submissions=null;
		Iterator<Object[]> it = list.iterator( );
		List<Submissions> listSubmissions= new ArrayList<Submissions>();
		while (it.hasNext( )) {
		    Object[] result = (Object[])it.next(); // Iterating through array object 
		    submissions = new Submissions();
		    submissions.setNoofProfileSumitted(Long.parseLong(result[0].toString()));
		    submissions.setRequirementId(Long.parseLong(result[1].toString()));
		    submissions.setTarget(result[2].toString());
		    submissions.setNameofRequirenment(result[3].toString());
		    submissions.setCandidateStatus(result[4].toString());
		    submissions.setRecuriterName(result[5].toString());
		    submissions.setClientName(result[6].toString());
		    
		    listSubmissions.add(submissions);

		    }
	

		return listSubmissions;

	}
	
	
	public List getData(Long requriterID) {
		Query q = getEntityManager().createNativeQuery(
				 " SELECT  COUNT(*)  noofprofilessummited," +"bdmreq.id requirementId,"+"assign.target,"+"bdmreq.nameOfRequirement," +"candidate.candidateStatus, "
				 		+ "USER.name, "
	    		  +" clients.clientName " 
	    		  +" FROM   bdmreq  bdmreq, "
	    		  +" candidate  candidate, "
	    		  +" assign assign , "
	    		         +" USER USER, "  
	    		          +" recuritermap recuritermap, "
	    		          +" CLIENT clients " 
	    		          +" WHERE bdmreq.id=candidate.bdmReq_id "
	    		                    +"  AND   USER.id=?"
	    		                    +" AND assign.bdmReq_id=bdmreq.id "
	    		                    +" AND recuritermap.recuriter_ID=USER.id "
	    		                    +" AND assign.id=recuritermap.Assign_ID "
	    		                    +"AND candidate.user_id=recuritermap.recuriter_ID"
	    		                    +" AND clients.id = bdmreq.client_id GROUP BY "
	    		                     +" bdmreq.id ");
		
		q.setParameter(1, requriterID);
		
		
		List<Object[]> list = q.getResultList();
		Submissions submissions=null;
		Iterator<Object[]> it = list.iterator( );
		List<Submissions> listSubmissions= new ArrayList<Submissions>();
		while (it.hasNext( )) {
		    Object[] result = (Object[])it.next(); // Iterating through array object 
		    submissions = new Submissions();
		    submissions.setNoofProfileSumitted(Long.parseLong(result[0].toString()));
		    submissions.setRequirementId(Long.parseLong(result[1].toString()));
		   /*
		    submissions.setNameofRequirenment(result[2].toString());
		    submissions.setCandidateStatus(result[3].toString());
		    submissions.setRecuriterName(result[4].toString());
		    submissions.setClientName(result[5].toString());
		    
		    */
		    if(result[2]==null) {
		     submissions.setTarget(0+"");	
		    }else {
		    submissions.setTarget(result[2].toString());
		    }
		    submissions.setNameofRequirenment(result[3].toString());
		    submissions.setCandidateStatus(result[4].toString());
		    submissions.setRecuriterName(result[5].toString());
		    submissions.setClientName(result[6].toString());
		    
		    
		    
		    listSubmissions.add(submissions);

		    }
		return listSubmissions;

	}

	/*
	 * @Override public Response getSubmissionsAndRejections() { // TODO
	 * Auto-generated method stub return null; }
	 * 
	 * @Override public Response getClosuresAndSubmissions() { // TODO
	 * Auto-generated method stub return null; }
	 */

}
