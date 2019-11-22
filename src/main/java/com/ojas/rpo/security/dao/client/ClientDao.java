package com.ojas.rpo.security.dao.client;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.Response;

public interface ClientDao extends Dao<Client, Long> {


	List<BdmReq> findResourcesForClient(Long id);
	public boolean chekduplicate(String clientName, String email, Long phone); 

	
	List<Client> getClientsByRole(Long id,String role);
	Response getAllClientsByRole(Long id, String role, Integer pageNo, Integer pageSize, String sortingOrder, String sortingField, String searchText, String searchField);
	Response searchClients(String role, Long id, String searchInput, String searchField, Integer pageNo, Integer pageSize);


}
