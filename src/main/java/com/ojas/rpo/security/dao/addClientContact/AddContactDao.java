package com.ojas.rpo.security.dao.addClientContact;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.AddContact;
import com.ojas.rpo.security.entity.Response;

public interface AddContactDao extends Dao<AddContact, Long> {

	List<AddContact> findContactByClientId(Long id);

	Response findContactByBdmId(Long id, String role, String sortField,String sortOrder,String searchField,String searchText, Integer pageNo, Integer pageSize);

	public int updatingStatus(Long id, String status);
	/* List<AddContact> findContactByBdmId(Long id, String role); */

	List<AddContact> findActiveContactByClientId(Long id,String role, Long userId);

	Integer findActiveContactByEmail(Long clientId, String email);

	List<Object[]> findActiveContactByEmailList(Long clientId, String email);

}
