package com.ojas.rpo.security.dao.assign;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Assign;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;

public interface AssignDao extends Dao<Assign, Long> {
	public List<Assign> findById(Long assignid);

	List<BdmReq> getReqByRecIdandClientId(Long userId, Long clientId, String status);

	Response getReqByRecIdandUserId(Long userId, String status, Integer pageNo, Integer pageSize, String sortOrder, String sortField, String searchText, String searchField);

	List<Client> getClientsByRecById(Long userId, String status);

	List<Assign> getAssigenByBdmId(Long userId, String role);

	 int deleteByid(Long assigenid);

	Response getAssinedRequirementsByBdmId(Long userId, String role, Integer pageNo, Integer pageSize, String sortingOrder, String sortingField, String searchText, String searchField);
	
	Response searchAssignedRequirementsByBdmId(String role, Long id, String searchInput, String searchField, Integer pageNo, Integer pageSize);

	public Integer deleteAssignmentByRecriuterAndRequirement(Long requirementId, User users);

	// List<Assign> findReqiremetsByRecId();
}
