package com.ojas.rpo.security.dao.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.transfer.UserListTransfer;
import com.ojas.rpo.security.transfer.UsersList;

public class JpaUserDao extends JpaDao<User, Long> implements UserDao {
	public JpaUserDao() {
		super(User.class);
	}

	@Override
	@Transactional(readOnly = true)
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		Predicate p;
		Predicate p1;
		Predicate p2;
		final CriteriaQuery<User> criteriaQuery = builder.createQuery(this.entityClass);

		Root<User> root = criteriaQuery.from(this.entityClass);
		Path<String> namePath = root.get("name");
		Path<String> statuspath = root.get("status");
		p = builder.equal(namePath, username);
		p1 = builder.equal(statuspath, "Active");
		p2 = builder.and(p, p1);
		criteriaQuery.where(p2);
		TypedQuery<User> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<User> users = typedQuery.getResultList();

		if (users.isEmpty()) {
			throw new UsernameNotFoundException("The user with name " + username + " was not found");
		}

		return users.get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public User findByName(String name) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<User> criteriaQuery = builder.createQuery(this.entityClass);

		Root<User> root = criteriaQuery.from(this.entityClass);
		Path<String> namePath = root.get("name");
		criteriaQuery.where(builder.equal(namePath, name));

		TypedQuery<User> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<User> users = typedQuery.getResultList();

		if (users.isEmpty()) {
			return null;
		}

		return users.iterator().next();
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByRole(String role) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<User> criteriaQuery = builder.createQuery(this.entityClass);

		Root<User> root = criteriaQuery.from(this.entityClass);
		Path<String> namePath = root.get("role");
		criteriaQuery.where(builder.equal(namePath, role));

		TypedQuery<User> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<User> users = typedQuery.getResultList();
		return users;
	}

	@Transactional(readOnly = true)
	public List<User> findByReportingId(Long id) {
		Query query = null;
		List<User> requirementsList = null;
		query = getEntityManager().createQuery("from User req where req.reportingId.id = " + id + "order by id desc");

		requirementsList = query.getResultList();
		return requirementsList;

	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByOnlyBdmleadRole() {
		Query query = null;
		List<User> requirementsList = null;
		query = getEntityManager().createQuery("from User req where req.role in('BDM') order by id desc");

		requirementsList = query.getResultList();
		return requirementsList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByOnlyAMRole() {
		Query query = null;
		List<User> requirementsList = null;
		query = getEntityManager().createQuery("from User req where req.role in('AM') order by id desc");

		requirementsList = query.getResultList();
		return requirementsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);

		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<User> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		System.out.println(typedQuery.getResultList().toString());
		return typedQuery.getResultList();
	}

	@Override
	public List<User> findByRoleAndId(Long id, String role) {
		Query q = null;
		if (role.equalsIgnoreCase("BDM")) {
			q = getEntityManager()
					.createQuery("from User user where user.reportingId.id=" + id + " and role='" + role + "'");
		} else {
			q = getEntityManager().createQuery("from User user where role='" + role + "'");
		}
		List<User> results = q.getResultList();

		return results;
	}

	@Transactional
	public Response updateUserById(Long id, User userUpdate) {
		try {
			User user = find(id);
			// String reportingQuery="select name from user where reportingId=
			// "+user.getReportingId();
			String sql = "UPDATE `user` SET ";

			StringBuilder sqlBuilder = new StringBuilder(sql);
			if (null != user) {
				if (userUpdate.getFirstName() != null) {
					sqlBuilder.append(" firstName ='" + userUpdate.getFirstName() + "',");
				}
				if (userUpdate.getLastName() != null) {
					sqlBuilder.append(" lastname='" + userUpdate.getLastName() + "',");
				}
				if (userUpdate.getEmail() != null) {
					sqlBuilder.append(" email='" + userUpdate.getEmail() + "',");
				}
				if (userUpdate.getContactNumber() != null) {
					sqlBuilder.append(" contactNumber=" + userUpdate.getContactNumber() + ",");
				}
				if (userUpdate.getExtension() != null) {
					sqlBuilder.append(" extension=" + userUpdate.getExtension() + ",");
				}
				if (userUpdate.getRole() != null) {
					sqlBuilder.append(" role='" + userUpdate.getRole() + "',");
				}
				if (userUpdate.getDoj() != null) {

					sqlBuilder.append(" doj='" + new java.sql.Date(userUpdate.getDoj().getTime()) + "',");
				}
				if (userUpdate.getStatus() != null) {
					sqlBuilder.append(" status='" + userUpdate.getStatus() + "',");
				}
				if (userUpdate.getReportingId() != null) {
					sqlBuilder.append(" reporting_id=" + userUpdate.getReportingId().getId() + ",");
				}
				if (userUpdate.getSalary() != null) {
					sqlBuilder.append(" salary=" + userUpdate.getSalary() + ",");
				}
				if (userUpdate.getVariablepay() != null) {
					sqlBuilder.append(" variablepay=" + userUpdate.getVariablepay() + ",");
				}
				if (userUpdate.getCtc() != null) {
					sqlBuilder.append(" ctc=" + userUpdate.getCtc() + ",");
				}
				if (userUpdate.getMintarget() != null) {
					sqlBuilder.append(" mintarget=" + userUpdate.getMintarget() + ",");
				}
				if (userUpdate.getMaxtarget() != null) {
					sqlBuilder.append(" maxtarget=" + userUpdate.getMaxtarget() + ",");
				}

				if (userUpdate.getTargetduration() != null) {
					sqlBuilder.append(" targetduration='" + userUpdate.getTargetduration() + "',");
				}
				sqlBuilder.setCharAt(sqlBuilder.length() - 1, ' ');
				sqlBuilder.append(" WHERE id  = ?");
				int updateResult = this.getEntityManager().createNativeQuery(sqlBuilder.toString()).setParameter(1, id)
						.executeUpdate();
				System.out.println("Update Result is " + updateResult);
				if (updateResult > 0) {
					Response res = new Response(ExceptionMessage.OK);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(ExceptionMessage.OK);
	}

	@Override
	public Response findAllUsers(Integer pageNo, Integer pageSize, String sortOrder, String sortField,
			String searchingField, String searchingInput) {

		UsersList listss = null;
		// ;
		String sql = " SELECT u1.id, u1.name, CONCAT(u1.firstName,' ',u1.lastName) AS fullName, u1.contactNumber, u1.extension, u1.email, u1.role, u1.status, CONCAT(u2.firstName,' ',u2.lastName) AS reportsTo, u2.name AS reportingMail FROM `user` u1 "
				+ " INNER JOIN `user` u2 ON u2.id = u1.reportingId_id ";

		if (null != searchingInput && null != searchingField) {

			String searchField = searchingField;

			if (searchField.equalsIgnoreCase("name") || "name".contains(searchingField)) {
				searchField = "u1.name";
			} else if (searchField.equalsIgnoreCase("fullName") || "fullName".contains(searchingField)) {
				searchField = "CONCAT(u1.firstName,' ',u1.lastName)";
			} else if (searchField.equalsIgnoreCase("contactNumber") || "contactNumber".contains(searchingField)) {
				searchField = "u1.contactNumber";
			} else if (searchField.equalsIgnoreCase("extension") || "extension".contains(searchingField)) {
				searchField = "u1.extension";
			} else if (searchField.equalsIgnoreCase("email") || "email".contains(searchingField)) {
				searchField = "u1.email";
			} else if (searchField.equalsIgnoreCase("role") || "role".contains(searchingField)) {
				searchField = "u1.role";
			} else if (searchField.equalsIgnoreCase("status") || "status".contains(searchingField)) {
				searchField = "u1.status";
			} else if (searchField.equalsIgnoreCase("reportsTo") || "reportsTo".contains(searchingField)) {
				searchField = "CONCAT(u2.firstName,' ',u2.lastName)";
			} else if (searchField.equalsIgnoreCase("reportingMail") || "reportingMail".contains(searchingField)) {
				searchField = "u2.name";
			} else {
				searchField = "u1.id";
			}

			sql = sql + " WHERE " + searchField + " LIKE '%" + searchingInput + "%' ";
		}

		if (null != sortField) {
			String sortingField = sortField;

			if (sortingField.equalsIgnoreCase("name") || "name".contains(sortingField)) {
				sortingField = "u1.name";
			} else if (sortingField.equalsIgnoreCase("fullName") || "fullName".contains(sortingField)) {
				sortingField = "CONCAT(u1.firstName,' ',u1.lastName)";
			} else if (sortingField.equalsIgnoreCase("contactNumber") || "contactNumber".contains(sortingField)) {
				sortingField = "u1.contactNumber";
			} else if (sortingField.equalsIgnoreCase("extension") || "extension".contains(sortingField)) {
				sortingField = "u1.extension";
			} else if (sortingField.equalsIgnoreCase("email") || "email".contains(sortingField)) {
				sortingField = "u1.email";
			} else if (sortingField.equalsIgnoreCase("role") || "role".contains(sortingField)) {
				sortingField = "u1.role";
			} else if (sortingField.equalsIgnoreCase("status") || "status".contains(sortingField)) {
				sortingField = "u1.status";
			} else if (sortingField.equalsIgnoreCase("reportsTo") || "reportsTo".contains(sortingField)) {
				sortingField = "CONCAT(u2.firstName,' ',u2.lastName)";
			} else if (sortingField.equalsIgnoreCase("reportingMail") || "reportingMail".contains(sortingField)) {
				sortingField = "u2.name";
			} else {
				sortingField = "u1.id";
			}

			sql = sql + " ORDER BY " + sortingField + " ";

		} else {
			sql = sql + " ORDER BY u1.id ";
		}

		if (null != sortOrder && (sortOrder.equalsIgnoreCase("ASC"))) {
			sql = sql + " ASC ";
		} else {
			sql = sql + " DESC ";
		}

		int startingRow = 0;
		if ((null != pageNo) && (null != pageSize)) {

			if (pageNo == 1) {
				startingRow = 0;
			} else {
				startingRow = ((pageNo - 1) * pageSize);
			}

			sql = sql + " LIMIT " + startingRow + "," + pageSize;

			List<UserListTransfer> userList;

			Query selectQuery = this.getEntityManager().createNativeQuery(sql);
			List<Object[]> results = selectQuery.getResultList();

			userList = new ArrayList<UserListTransfer>();
			for (Object[] obj : results) {
				UserListTransfer user = new UserListTransfer();
				user.setId(Long.valueOf(obj[0].toString()));
				user.setName((String) obj[1]);
				user.setContactNumber(Long.valueOf(obj[3].toString()));
				user.setExtension((Integer.valueOf(obj[4].toString())));
				user.setEmail((String) obj[5]);
				user.setRole((String) obj[6]);
				user.setStatus((String) obj[7]);

				user.setFullName((String) obj[2]);
				user.setReportsTo((String) obj[8]);
				user.setReportingMail((String) obj[9]);

				userList.add(user);
			}
			listss = new UsersList();
			listss.setList(userList);

			int totalRecords = findAll().size();
			listss.setTotalRecords(totalRecords);
			if (totalRecords == 0) {
				listss.setTotalPages(0);
			}

			if ((totalRecords > 0) && (Integer.valueOf(totalRecords) <= pageSize)) {
				listss.setTotalPages(1);
			} else {
				Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
				totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
				listss.setTotalPages(totalPages);
			}

		}

		return new Response(ExceptionMessage.StatusSuccess, listss);

	}

	public Response getAllReportingManagers() {

		List<String> reportingManagerList = null;
		Query query = null;
		Response response = null;
		try {

			query = getEntityManager().createNativeQuery(
					"SELECT concat(firstname,' ',lastname) As reportsTo FROM testing.user where user.role!='recruiter' And user.role!='Admin'");

			reportingManagerList = query.getResultList();

			if (reportingManagerList.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty);
			} else {
				response = new Response(ExceptionMessage.OK, reportingManagerList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500", "Unable to Retrive Managers List");
		}
		return response;

	}

	@Override
	public Response getUserNamesByRole(String role) {
		Response response = null;
		Query userQuery = getEntityManager().createNativeQuery("SELECT u.id, concat(u.firstName,' ',u.lastName) from user `u` where u.role=?");
		userQuery.setParameter(1, role);
		try {
			List<Object[]> results = userQuery.getResultList();
			List<UserNameTransfer> result = new ArrayList<UserNameTransfer>();
			for(Object[] data:results){
				UserNameTransfer unt = new UserNameTransfer();
				unt.setId(Long.valueOf(data[0].toString()));
				unt.setFullNameOfUser(data[1].toString());
				result.add(unt);
			}
			if(results.isEmpty()){
				response = new Response(ExceptionMessage.DataIsEmpty, "No Users found By Role = "+role);
			}else{
				response = new Response(ExceptionMessage.OK, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500", "Unable Retrieve User List due to : "+e.getMessage());
		}
		return response;
	}

	@Override
	public Response getRecruitersByReportingId(Long id) {
		Response response = null;
		Query userQuery = getEntityManager().createNativeQuery("SELECT id, concat(firstName,' ',lastName) AS recruiterName "
				+ "FROM user WHERE role in('recruiter' ,'Lead')");
		try {
			List<Object[]> results = userQuery.getResultList();
			List<UserNameTransfer> result = new ArrayList<UserNameTransfer>();
			for(Object[] data:results){
				UserNameTransfer unt = new UserNameTransfer();
				unt.setId(Long.valueOf(data[0].toString()));
				unt.setFullNameOfUser(data[1].toString());
				result.add(unt);
			}
			if(results.isEmpty()){
				response = new Response(ExceptionMessage.DataIsEmpty, "No Users found By Reporting Id = "+id);
			}else{
				response = new Response(ExceptionMessage.OK, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500", "Unable Retrieve User List due to : "+e.getMessage());
		}
		return response;
	}
}

class UserNameTransfer{
	Long id;
	String fullNameOfUser;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullNameOfUser() {
		return fullNameOfUser;
	}
	public void setFullNameOfUser(String fullNameOfUser) {
		this.fullNameOfUser = fullNameOfUser;
	}
}
