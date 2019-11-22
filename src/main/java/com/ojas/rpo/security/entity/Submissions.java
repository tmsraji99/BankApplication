package com.ojas.rpo.security.entity;

import java.util.ArrayList;

public class Submissions  implements Entity  {

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

   private Long id;
   private Long noofProfileSumitted;
   private Long requirementId;
   private String nameofRequirenment ;
   private String candidateStatus;
   private String recuriterName;
   private String clientName;
   private String target;
public String getTarget() {
	return target;
}
public void setTarget(String target) {
	this.target = target;
}
public Long getNoofProfileSumitted() {
	return noofProfileSumitted;
}
public void setNoofProfileSumitted(Long noofProfileSumitted) {
	this.noofProfileSumitted = noofProfileSumitted;
}
public Long getRequirementId() {
	return requirementId;
}
public void setRequirementId(Long requirementId) {
	this.requirementId = requirementId;
}
public String getNameofRequirenment() {
	return nameofRequirenment;
}
public void setNameofRequirenment(String nameofRequirenment) {
	this.nameofRequirenment = nameofRequirenment;
}
public String getCandidateStatus() {
	return candidateStatus;
}
public void setCandidateStatus(String candidateStatus) {
	this.candidateStatus = candidateStatus;
}
public String getRecuriterName() {
	return recuriterName;
}
public void setRecuriterName(String recuriterName) {
	this.recuriterName = recuriterName;
}
public String getClientName() {
	return clientName;
}
public void setClientName(String clientName) {
	this.clientName = clientName;
}


@Override
public String toString() {
	return "Submissions [id=" + id + ", noofProfileSumitted=" + noofProfileSumitted + ", requirementId="
			+ requirementId + ", nameofRequirenment=" + nameofRequirenment + ", candidateStatus=" + candidateStatus
			+ ", recuriterName=" + recuriterName + ", clientName=" + clientName + ", target=" + target + "]";
}
	
	
}
