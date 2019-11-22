package com.ojas.rpo.security.dao.assign;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Assign;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.transfer.AssignmentsData;
import com.ojas.rpo.security.transfer.RequirementListTranfer;

public class JpaAssignDao extends JpaDao<Assign, Long> implements AssignDao {

	public JpaAssignDao() {
		super(Assign.class);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Assign> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Assign> criteriaQuery = builder.createQuery(Assign.class);

		Root<Assign> root = criteriaQuery.from(Assign.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<Assign> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}// findAll() closing

	@Override
	@Transactional(readOnly = true)
	public Assign find(Long id) {
		return this.getEntityManager().find(this.entityClass, id);
	}

	@Override
	@Transactional
	public Assign save(Assign entity) {
		return this.getEntityManager().merge(entity);
	}

	/*
	 * @Override public Assign asignTask(Assign Assign) { Assign at=
	 * find(Assign.getUserIdentifier()); return at; }
	 */

	/*
	 * public List<UserDetailsList> getSelecteduserList(String role){
	 * List<UserDetailsList> selectedList=new ArrayList<UserDetailsList>();
	 * List<UserDetailsList> userList=findAll();
	 * 
	 * for(UserDetailsList ul:userList){ if(role.equals(ul.getRole())){
	 * selectedList.add(ul); } }
	 * 
	 * return selectedList; }//getSelecteduserList() closing
	 */
	public List<Assign> findById(Long assignid) {

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Assign> cq = cb.createQuery(Assign.class);
		Root<Assign> e = cq.from(Assign.class);
		Join<Assign, Assign> r = e.join("users", JoinType.LEFT);
		Predicate p = cb.equal(r.get("id"), assignid);
		cq.where(p);
		TypedQuery<Assign> tq = getEntityManager().createQuery(cq);
		return tq.getResultList();
	}

	@Override
	public List<BdmReq> getReqByRecIdandClientId(Long userId, Long clientId, String status) {

		Query q = getEntityManager().createQuery(" select ass.bdmReq from  Assign ass where ass.bdmReq.client.id="
				+ clientId + " and  ass.users.id=" + userId + " " + " and  ass.bdmReq.status='" + status + "'");

		List<BdmReq> results = q.getResultList();

		return results;
	}

	@Override
	public Response getReqByRecIdandUserId(Long userId, String status, Integer pageNo, Integer pageSize,
			String sortingOrder, String sortingField, String searchText, String searchField) {
		Response response = null;
		Integer totalRecords = 0;
		Query query = null;
		// Query q = getEntityManager().createQuery(" select ass.bdmReq from
		// Assign ass
		// where ass.users.id=" + userId
		// + " " + " and ass.bdmReq.status='" + status + "'");

		// List<BdmReq> results = q.getResultList();

		StringBuilder sql = new StringBuilder(
				"SELECT br.id as reqId,c.id as clientId, a.id as assinedId,br.nameOfRequirement,c.clientName,"
				+ "ac.contact_Name,ac.mobile,ac.email,br.status,br.requirementStartdate,br.requirementEndDate,br.noticePeriod,"
						+ " concat(ss.firstName ,ss.lastName) as leadName ,a.date FROM `assign` a "
						+ "INNER JOIN `bdmreq` br ON a.bdmReq_id=br.id "
						+ "INNER JOIN `client` c ON br.client_id = c.id "
						+ "INNER JOIN `addcontact` ac ON br.addcontact_id = ac.id "
						+ "INNER JOIN `user` ss ON ss.id = ac.lead_id "
						+ "WHERE a.users_id = ? AND br.status = 'open' ");

		StringBuilder countSql = new StringBuilder("SELECT count(*) FROM `assign` a "
				+ "INNER JOIN `bdmreq` br ON a.bdmReq_id=br.id " + "INNER JOIN `client` c ON br.client_id = c.id "
				+ "INNER JOIN `addcontact` ac ON br.addcontact_id = ac.id "
				+ "INNER JOIN `user` ss ON ss.id = ac.lead_id " + " WHERE a.users_id = ? AND br.status = 'open' ");
		Query countQuery = this.getEntityManager().createNativeQuery(countSql.toString());
		countQuery.setParameter(1, userId);

		if (null != sortingOrder && sortingOrder.equalsIgnoreCase("ASC")) {
			sortingOrder = "ASC";
		} else {
			sortingOrder = "DESC";
		}

		if (null != sortingField && !sortingField.equalsIgnoreCase("undefined") && !sortingField.isEmpty()) {
			if (sortingField.equalsIgnoreCase("nameOfRequirement") || "nameOfRequirement".contains(sortingField)) {
				sortingField = "br.nameOfRequirement";
			} else if (sortingField.equalsIgnoreCase("clientName") || "clientName".contains(sortingField)) {
				sortingField = "c.clientName";
			} else if ((sortingField.equalsIgnoreCase("contactName") || sortingField.equalsIgnoreCase("spocName"))
					|| ("spocname".contains(sortingField) || "contactName".contains(sortingField))) {
				sortingField = "ac.contact_Name";
			} else if (sortingField.equalsIgnoreCase("mobile") || "mobile".contains(sortingField)) {
				sortingField = "ac.mobile";
			} else if (sortingField.equalsIgnoreCase("email") || "email".contains(sortingField)) {
				sortingField = "ac.email";
			} else if (sortingField.equalsIgnoreCase("status") || "status".contains(sortingField)) {
				sortingField = "req.status";
			} else if (sortingField.equalsIgnoreCase("id")) {
				sortingField = "a.date";
			}

		} else {
			sortingField = "a.date";
		}

		if (null != searchField && !searchField.equalsIgnoreCase("undefined") && !searchField.isEmpty()) {

			if (searchField.equalsIgnoreCase("nameOfRequirement") || "nameOfRequirement".contains(searchField)) {
				searchField = "br.nameOfRequirement";
			} else if (searchField.equalsIgnoreCase("clientName") || "clientName".contains(searchField)) {
				searchField = "c.clientName";
			} else if ((searchField.equalsIgnoreCase("contactName") || searchField.equalsIgnoreCase("spocName"))
					|| ("spocname".contains(searchField) || "contactName".contains(searchField))) {
				searchField = "ac.contact_Name";
			} else if (searchField.equalsIgnoreCase("mobile") || "mobile".contains(searchField)) {
				searchField = "ac.mobile";
			} else if (searchField.equalsIgnoreCase("email") || "email".contains(searchField)) {
				searchField = "ac.email";
			} else if (searchField.equalsIgnoreCase("status") || "status".contains(searchField)) {
				searchField = "req.status";
			} else if (searchField.equalsIgnoreCase("id")) {
				searchField = "a.id";
			}

		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		if (null != searchField && null != searchText && !searchField.equals("undefined")
				&& !searchText.equals("undefined") && !searchText.isEmpty()) {
			sql.append(" AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortingField + " "
					+ sortingOrder + " LIMIT " + startingRow + "," + pageSize);
		} else {
			sql.append(" ORDER BY " + sortingField + " " + sortingOrder + " LIMIT " + startingRow + "," + pageSize);
		}

		System.out.println("Query = " + sql.toString());

		try {
			query = this.getEntityManager().createNativeQuery(sql.toString());
			query.setParameter(1, userId);
			List<Object[]> results = query.getResultList();
			if (results.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Requirements Found");
			} else {
				List<RequirementListTranfer> result = new ArrayList<RequirementListTranfer>();
				for (Object[] data : results) {
					RequirementListTranfer respData = new RequirementListTranfer();
					if (null != data[0]) {
						respData.setId(Long.valueOf(data[0].toString()));
					}
					if (null != data[1]) {
						respData.setClientId(Long.valueOf(data[1].toString()));
					}
					if (null != data[2]) {
						respData.setAssignId(Long.valueOf(data[2].toString()));
					}

					respData.setNameOfRequirement(data[3].toString());
					respData.setClientName(data[4].toString());
					respData.setContact_Name(data[5].toString());
					respData.setMobile(data[6] + "");
					respData.setEmail(data[7] + "");
					respData.setStatus(data[8] + "");
					respData.setStartDate((Date) data[9]);
					respData.setEndDate((Date) data[10]);
					respData.setNoticePeriod(data[11] + "");
					respData.setLeadName(data[12] + "");
					respData.setAssigenedDate((Date) (data[13]));
					result.add(respData);
				}

				/* System.out.println(result); */
				totalRecords = Integer.valueOf(countQuery.getSingleResult().toString());
				response = new Response(ExceptionMessage.OK, result);
				response.setTotalRecords(totalRecords);

				if (totalRecords == 0) {
					response.setTotalPages(0);
				} else {
					Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
					totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
					response.setTotalPages(totalPages);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500",
					"Unable to Retrieve Requirements due to following Error : " + e.getMessage());
		}

		return response;
	}

	@Override
	public List<Client> getClientsByRecById(Long userId, String status) {

		Query q = getEntityManager().createQuery(
				" select ass.client.id,ass.client.clientName from  Assign ass where   ass.users.id=" + userId + " "
						+ " and  ass.bdmReq.status='" + status + "' group by ass.client.id,ass.client.clientName");

		List<Object[]> results = q.getResultList();
		List<Client> list = new ArrayList();
		for (Object obj[] : results) {
			Client client = new Client();
			client.setId(Long.parseLong(obj[0].toString()));
			client.setClientName((obj[1].toString()));
			list.add(client);
		}
		return list;
	}

	@Override
	public List<Assign> getAssigenByBdmId(Long userId, String role) {
		Query q = null;
		if (role.equalsIgnoreCase("BDM")) {

			q = getEntityManager().createQuery(
					"from Assign req where req.client.primaryContact.id=? or req.client.secondaryContact.id=? order by id desc");
			q.setParameter(1, userId);
			q.setParameter(2, userId);
		} else {
			q = getEntityManager().createQuery(" from Assign req  order by req.id desc");
		}
		List<Assign> list = q.getResultList();

		return list;
	}

	@Override
	@Transactional
	public int deleteByid(Long assigenid) {
		return getEntityManager().createNativeQuery("delete from assign where id=" + assigenid).executeUpdate();

	}

	@Override
	public Response getAssinedRequirementsByBdmId(Long userId, String role, Integer pageNo, Integer pageSize,
			String sortOrder, String sortField, String searchText, String searchField) {
		Response response = null;
		Integer totalRecords = 0;
		if ((null != sortOrder) && (sortOrder.startsWith("asc") || sortOrder.equalsIgnoreCase("asc"))) {
			sortOrder = "ASC";
		} else {
			sortOrder = "DESC";
		}

		if (null != sortField && !sortField.equalsIgnoreCase("undefined")) {

			if (sortField.equalsIgnoreCase("clientName") || "clientName".contains(sortField)) {
				sortField = "c.clientName";
			} else if (sortField.equalsIgnoreCase("nameOfRequirement") || "nameOfRequirement".contains(sortField)) {
				sortField = "r.nameOfRequirement";
			} else if (sortField.equalsIgnoreCase("recruiterName") || "recruiterName".contains(sortField)) {
				sortField = "CONCAT(u.firstname,' ',u.lastName)";
			} else if (sortField.equalsIgnoreCase("date") || "AssignedDate".contains(sortField)) {
				sortField = "asn.date";
			} else if (sortField.equalsIgnoreCase("id")) {
				sortField = "asn.id";
			}

		} else {
			sortField = "asn.lastUpdatedDate";
		}

		if (null != searchField && !searchField.equalsIgnoreCase("undefined")) {
			if (searchField.equalsIgnoreCase("clientName") || "clientName".contains(searchField)) {
				searchField = "c.clientName";
			}
			if (searchField.equalsIgnoreCase("requirementName") || "requirementName".contains(searchField)) {
				searchField = "r.nameOfRequirement";
			}
			if (searchField.equalsIgnoreCase("recruiterName") || "recruiterName".contains(searchField)) {
				searchField = "CONCAT(u.firstname,' ',u.lastName)";
			}
			if (searchField.equalsIgnoreCase("assignedDate") || "assignedDate".contains(searchField)) {
				searchField = "asnmt.date";
			} else if (searchField.equalsIgnoreCase("id")) {
				searchField = "asn.id";
			}
		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		try {
			List<AssignmentsData> assignList = new ArrayList<AssignmentsData>();
			Query q = null;
			String sql = " SELECT c.clientName AS clientName, r.nameOfRequirement AS nameOfRequirement,"
					+ " CONCAT(u.firstname,' ',u.lastName) AS nameOfRecruiter, asn.date AS assinDate, asn.id AS assignId ,c.id as clientId,r.id as reqirementId "
					+ " FROM assign asn "
					+ " INNER JOIN client c ON asn.client_id = c.id  INNER JOIN  bdmreq r ON asn.bdmReq_id=r.id  "
					+ "INNER JOIN user u ON asn.users_id=u.id "
					+ "INNER JOIN  addcontact con ON con.id=r.addContact_id  ";
			String countQuery = "select COUNT(*) FROM assign asn "
					+ " INNER JOIN client c ON asn.client_id = c.id  INNER JOIN  bdmreq r ON asn.bdmReq_id=r.id "
					+ " INNER JOIN user u ON asn.users_id=u.id "
					+ "INNER JOIN  addcontact con ON con.id=r.addContact_id ";
			StringBuilder sqlBuilder = new StringBuilder(sql);
			if (role.equalsIgnoreCase("BDM")) {

				if (null != searchField && null != searchText && !searchField.equalsIgnoreCase("undefined")
						&& !searchText.equalsIgnoreCase("undefined")) {
					sqlBuilder = sqlBuilder.append(" WHERE (con.primaryContact_id=? OR con.secondaryContact_id=?) AND "
							+ searchField + " LIKE '%" + searchText + "%' ORDER BY  " + sortField + " " + sortOrder
							+ " LIMIT " + startingRow + "," + pageSize);
				} else {
					sqlBuilder = sqlBuilder
							.append(" WHERE (con.primaryContact_id=? OR con.secondaryContact_id=?) ORDER BY "
									+ sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				}

				q = getEntityManager().createNativeQuery(sqlBuilder.toString());
				q.setParameter(1, userId);
				q.setParameter(2, userId);

				countQuery = countQuery + " WHERE (con.primaryContact_id=? OR con.secondaryContact_id=?) ";
				Query count = this.getEntityManager().createNativeQuery(countQuery);
				count.setParameter(1, userId);
				count.setParameter(2, userId);
				totalRecords = Integer.valueOf(count.getSingleResult().toString());

			}
			if (role.equalsIgnoreCase("AM")) {

				if (null != searchField && null != searchText && !searchField.equalsIgnoreCase("undefined")
						&& !searchText.equalsIgnoreCase("undefined")) {
					sqlBuilder = sqlBuilder.append("WHERE (con.accountManger_id = ?) AND " + searchField + " LIKE '%"
							+ searchText + "%' ORDER BY " + sortField + " " + sortOrder + " LIMIT " + startingRow + ", "
							+ pageSize);
				} else {
					sqlBuilder = sqlBuilder.append("WHERE (con.accountManger_id = ?) ORDER BY " + sortField + " "
							+ sortOrder + " LIMIT " + startingRow + ", " + pageSize);
				}

				q = getEntityManager().createNativeQuery(sqlBuilder.toString());
				q.setParameter(1, userId);

				countQuery = countQuery + " WHERE  (con.accountManger_id = ?) ";
				Query count = this.getEntityManager().createNativeQuery(countQuery);
				count.setParameter(1, userId);
				totalRecords = Integer.valueOf(count.getSingleResult().toString());

			}
			if (role.equalsIgnoreCase("Lead")) {

				if (null != searchField && null != searchText && !searchField.equalsIgnoreCase("undefined")
						&& !searchText.equalsIgnoreCase("undefined")) {
					sqlBuilder = sqlBuilder.append(
							" WHERE con.lead_id = ?   AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY "
									+ sortField + " " + sortOrder + " LIMIT " + startingRow + ", " + pageSize);
				} else {
					sqlBuilder = sqlBuilder.append("WHERE  con.lead_id = ? ORDER BY " + sortField + " " + sortOrder
							+ " LIMIT " + startingRow + ", " + pageSize);
				}

				q = getEntityManager().createNativeQuery(sqlBuilder.toString());
				q.setParameter(1, userId);

				countQuery = countQuery + " WHERE   con.lead_id = ? ";
				Query count = this.getEntityManager().createNativeQuery(countQuery);
				count.setParameter(1, userId);
				totalRecords = Integer.valueOf(count.getSingleResult().toString());

			}

			if (role.equalsIgnoreCase("recruiter")) {

				if (null != searchField && null != searchText && !searchField.equalsIgnoreCase("undefined")
						&& !searchText.equalsIgnoreCase("undefined")) {
					sqlBuilder = sqlBuilder.append(
							"WHERE (asn.users_id = ? ) AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY "
									+ sortField + " " + sortOrder + " LIMIT " + startingRow + ", " + pageSize);
				} else {
					sqlBuilder = sqlBuilder.append("WHERE (asn.users_id = ? ) ORDER BY " + sortField + " " + sortOrder
							+ " LIMIT " + startingRow + ", " + pageSize);
				}

				q = getEntityManager().createNativeQuery(sqlBuilder.toString());
				q.setParameter(1, userId);

				countQuery = countQuery + " WHERE (asn.users_id = ? ) ";
				Query count = this.getEntityManager().createNativeQuery(countQuery);
				count.setParameter(1, userId);
				totalRecords = Integer.valueOf(count.getSingleResult().toString());
			}

			List<Object[]> results = q.getResultList();
			if (results.isEmpty()) {
				return new Response(ExceptionMessage.DataIsEmpty, "No Assignments Found");
			} else {
				for (Object[] objects : results) {
					AssignmentsData data = new AssignmentsData();
					data.setClientName((String) objects[0]);
					data.setRequirementName((String) objects[1]);
					data.setRecruiterName((String) objects[2]);
					data.setAssignedDate(objects[3].toString());
					data.setAssignId(Long.valueOf(objects[4].toString()));
					data.setClientId(Long.valueOf(objects[5].toString()));
					data.setRequirementId(Long.valueOf(objects[6].toString()));
					assignList.add(data);
				}
				response = new Response(ExceptionMessage.OK, assignList);
			}

			response.setTotalRecords(totalRecords);
			if (totalRecords == 0) {
				response.setTotalRecords(totalRecords);
				response.setTotalPages(0);
			}

			if ((totalRecords > 0) && (Integer.valueOf(totalRecords) <= pageSize)) {
				response.setTotalPages(1);
			} else {
				Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
				totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
				response.setTotalPages(totalPages);
			}

			return response;

		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					"Unable to Retrieve Assigned Requirements.  " + " " + pe.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					" Unable to Retrieve Assigned Requirements. " + e.getLocalizedMessage());
		}

	}

	@Override
	public Response searchAssignedRequirementsByBdmId(String role, Long id, String searchInput, String searchField,
			Integer pageNo, Integer pageSize) {
		Query q = null;
		Response response = null;
		Integer totalRecords = 0;
		String sql = "SELECT c.clientName AS clientName, r.nameOfRequirement AS nameOfRequirement, CONCAT(u.firstname,' ',u.lastName) AS nameOfRecruiter, asnmt.date AS assinDate FROM `assign` asnmt "
				+ "INNER JOIN `client` c ON asnmt.client_id = c.id  INNER JOIN  `bdmreq` r ON asnmt.bdmReq_id=r.id  INNER JOIN `user` u ON asnmt.users_id=u.id ";
		// String countQuery = "select COUNT(*) FROM assign asn INNER JOIN
		// client c ON
		// asn.client_id = c.id INNER JOIN bdmreq r ON asn.bdmReq_id=r.id INNER
		// JOIN
		// user u ON asn.users_id=u.id ";
		StringBuilder sqlBuilder = new StringBuilder(sql);
		List<AssignmentsData> assignList = new ArrayList<AssignmentsData>();

		if (searchField.equalsIgnoreCase("clientName") || "clientName".contains(searchField)) {
			searchField = "c.clientName";
		}
		if (searchField.equalsIgnoreCase("requirementName") || "requirementName".contains(searchField)) {
			searchField = "r.nameOfRequirement";
		}
		if (searchField.equalsIgnoreCase("recruiterName") || "recruiterName".contains(searchField)) {
			searchField = "CONCAT(u.firstname,' ',u.lastName)";
		}
		if (searchField.equalsIgnoreCase("assignedDate") || "assignedDate".contains(searchField)) {
			searchField = "asnmt.date";
		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		try {
			if (null != id) {
				if (role.equalsIgnoreCase("BDM") || role.equalsIgnoreCase("bdmlead")) {
					sqlBuilder.append(
							" WHERE (c.primaryContact_id=? OR c.secondaryContact_id=?) AND " + searchField + " LIKE '%"
									+ searchInput + "%' ORDER BY asnmt.id DESC LIMIT " + startingRow + "," + pageSize);
					q = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
					q.setParameter(1, id);
					q.setParameter(2, id);
					// countQuery.concat(" WHERE (c.primaryContact_id=? OR
					// c.secondaryContact_id=?)
					// ");
				} else {
					sqlBuilder.append(" WHERE " + searchField + " LIKE '%" + searchInput
							+ "%' ORDER BY asnmt.id DESC LIMIT " + startingRow + "," + pageSize);
					q = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
					// q.setParameter(1, id);

					// countQuery.concat(" WHERE (c.primaryContact_id=? OR
					// c.secondaryContact_id=?)
					// ");
				}
			} else {
				sqlBuilder.append(" AND " + searchField + " LIKE '%" + searchInput + "%' ORDER BY asnmt.id DESC LIMIT "
						+ startingRow + "," + pageSize);
				q = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
			}

			List<Object[]> results = q.getResultList();
			if (results.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Assignments Found");
			} else {
				for (Object[] objects : results) {
					AssignmentsData data = new AssignmentsData();
					data.setClientName((String) objects[0]);
					data.setRequirementName((String) objects[1]);
					data.setRecruiterName((String) objects[2]);
					data.setAssignedDate((String) objects[3].toString());
					assignList.add(data);
				}

				response = new Response(ExceptionMessage.OK, assignList);
			}

			// totalRecords =
			// Integer.valueOf(this.getEntityManager().createNativeQuery(countQuery).getSingleResult().toString());
			totalRecords = results.size();
			response.setTotalRecords(totalRecords);
			if (totalRecords == 0) {
				response.setTotalRecords(totalRecords);
				response.setTotalPages(0);
			}

			if ((totalRecords > 0) && (Integer.valueOf(totalRecords) <= pageSize)) {
				response.setTotalPages(1);
			} else {
				Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
				totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
				response.setTotalPages(totalPages);
			}

			return response;

		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					"Unknown Column '" + searchField + "' .  Enter Correct Column Name. " + pe.getLocalizedMessage());

		} catch (Exception e) {
			e.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					" Unable to Search Assigned Requirements " + e.getLocalizedMessage());
		}
	}

	@Override
	@Transactional
	public Integer deleteAssignmentByRecriuterAndRequirement(Long requirementId, User users) {

		Query q = this.getEntityManager()
				.createNativeQuery("SELECT COUNT(*) FROM assign WHERE users_id = ? AND bdmReq_id = ?");
		q.setParameter(1, users.getId());
		q.setParameter(2, requirementId);

		return Integer.valueOf(q.getSingleResult().toString());

	}

}
