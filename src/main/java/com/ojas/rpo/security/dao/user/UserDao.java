package com.ojas.rpo.security.dao.user;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.RequestBody;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;

public interface UserDao extends Dao<User, Long>
{
    User loadUserByUsername(String username) throws UsernameNotFoundException;

    User findByName(String name);
    List<User> findByRole(String role);
    
    List<User> findByRoleAndId(Long id, String role);

	List<User> findByOnlyBdmleadRole();

	List<User> findByOnlyAMRole();
	
    Response updateUserById(Long id, User userUpdate);

	Response findAllUsers(Integer pageNo, Integer pageSize, String sortingOrder, String sortingField, String searchField, String searchInput);
	
	//List<String> findReportingManagerByUser(String role);
	public Response getAllReportingManagers();

	Response getUserNamesByRole(String role);

	Response getRecruitersByReportingId(Long id);

   
}
