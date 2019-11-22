package com.ojas.rpo.security.dao.client;

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

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.transfer.ClientsListTransfer;

@Repository
public class ClientDaoImpl extends JpaDao<Client, Long> implements ClientDao {

	public ClientDaoImpl() {
		super(Client.class);
	}

	@Transactional(readOnly = true)
	public List<Client> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Client> criteriaQuery = builder.createQuery(Client.class);

		Root<Client> root = criteriaQuery.from(Client.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<Client> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override

	@Transactional
	public boolean chekduplicate(String clientName, String email, Long phone) {
		boolean result = false;

		try {
			Query query = getEntityManager()
					.createNativeQuery("select clientName FROM client WHERE email=? OR phone=? OR clientName=?");

			query.setParameter(1, email);
			query.setParameter(2, phone);
			query.setParameter(3, clientName);

			@SuppressWarnings("unchecked")
			List<String> results = query.getResultList();
			List<String> listData = new ArrayList<String>();

			for (String cName : results) {
				if (clientName.equalsIgnoreCase(cName)) {
					listData.add(clientName);
				}
			}
			System.out.println("final lisr Size " + listData);
			if (listData.isEmpty()) {
				result = false;
			} else {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	@Transactional
	public Client save(Client client) {
		System.out.println("saving data ......");
		return this.getEntityManager().merge(client);
	}

	@Override
	public List<BdmReq> findResourcesForClient(Long id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<BdmReq> cq = cb.createQuery(BdmReq.class);
		Root<BdmReq> e = cq.from(BdmReq.class);
		Join<BdmReq, BdmReq> r = e.join("client", JoinType.LEFT);
		Predicate p = cb.equal(r.get("id"), id);
		cq.where(p);
		TypedQuery<BdmReq> tq = getEntityManager().createQuery(cq);
		return tq.getResultList();
	}

	@Override
	public List<Client> getClientsByRole(Long id, String role) {
		Query query = null;
		List<Client> list = new ArrayList<Client>();

		query = getEntityManager().createQuery("from Client cl  order by id desc");
		list = query.getResultList();

		return list;

	}

	@Override
	public Response getAllClientsByRole(Long id, String role, Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField) {
		Response response = null;
		List<ClientsListTransfer> responseList = new ArrayList<ClientsListTransfer>();
		List<Object[]> clientsList = null;
		Integer totalRecords = 0;
		String sortOrder = "DESC";
		String sortField = "c.lastUpdatedDate";
		if ((null != sortingOrder) && (sortingOrder.startsWith("asc") || sortingOrder.equalsIgnoreCase("asc"))) {
			sortOrder = "ASC";
		}

		if (null != sortingField && !sortingField.equalsIgnoreCase("undefined")) {
			if (sortingField.equalsIgnoreCase("clientName") || "clientName".contains(sortingField)) {
				sortField = "c.clientName";
			} else if (sortingField.equalsIgnoreCase("startDate") || "startDate".contains(sortingField)) {
				sortField = "c.startDate";
			} else if (sortingField.equalsIgnoreCase("customerType") || "customerType".contains(sortingField)) {
				sortField = "ct.customerType";
			} else if (sortingField.equalsIgnoreCase("clientId")) {
				sortField = "c.id";
			}

		}

		if (null != searchField && !searchField.equalsIgnoreCase("undefined")) {
			if (searchField.equalsIgnoreCase("clientName") || "clientName".contains(searchField)) {
				searchField = "c.clientName";
			} else if (searchField.equalsIgnoreCase("startDate") || "startDate".contains(searchField)) {
				searchField = "c.startDate";
			} else if (searchField.equalsIgnoreCase("customerType") || "customerType".contains(searchField)) {
				searchField = "ct.customerType";
			} else if (searchField.equalsIgnoreCase("clientId")) {
				searchField = "c.id";
			}
		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		Query query = null;
		try {

			if ((null != searchText) && (null != searchField) && !searchField.equalsIgnoreCase("undefined")
					&& !searchText.equalsIgnoreCase("undefined")) {
				query = getEntityManager().createNativeQuery(
						"SELECT c.id, c.clientName,ct.customerType, c.startDate,c.lastUpdatedDate FROM `client` c "
								+ "INNER JOIN `customertype` ct ON c.customerType_id = ct.id  where " + searchField
								+ " LIKE '%" + searchText + "%' ORDER BY " + sortField + " " + sortOrder + " LIMIT "
								+ startingRow + "," + pageSize);
			} else {
				query = getEntityManager().createNativeQuery(
						" SELECT c.id, c.clientName,ct.customerType, c.startDate ,c.lastUpdatedDate FROM `client` c INNER JOIN `customertype` ct ON c.customerType_id = ct.id  "
								+ " ORDER BY " + sortField + " " + sortOrder + " LIMIT " + startingRow + ","
								+ pageSize);
			}
			clientsList = query.getResultList();

			String countSql = "SELECT COUNT(*) FROM `client` c  INNER JOIN `customertype` ct ON c.customerType_id = ct.id ";
			Query countQuery = this.getEntityManager().createNativeQuery(countSql);

			Object countResult = countQuery.getSingleResult();
			totalRecords = Integer.valueOf(countResult.toString());

			if (clientsList.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
			} else {
				for (Object[] req : clientsList) {
					ClientsListTransfer transferObj = new ClientsListTransfer();
					transferObj.setId(Long.valueOf(req[0] + ""));
					transferObj.setClientName((String) req[1]);
					transferObj.setCustomerType((String) req[2]);
					transferObj.setStartDate((Date) req[3]);
					transferObj.setLastUpdatedDate((Date) req[4]);
					responseList.add(transferObj);
				}
				response = new Response(ExceptionMessage.OK, responseList);
			}

			response.setTotalRecords(totalRecords);
			if (totalRecords == 0) {
				response.setTotalPages(0);
			} else {
				Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
				totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
				response.setTotalPages(totalPages);
			}

			return response;

		} catch (

		PersistenceException pe) {
			pe.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					"Unable to Retrieve Customers List. " + " " + pe.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					" Unable Retrieve Customers List " + e.getLocalizedMessage());
		}

	}

	@Override
	public Response searchClients(String role, Long id, String searchInput, String searchField, Integer pageNo,
			Integer pageSize) {
		Response response = null;
		Integer totalRecords = 0;
		List<ClientsListTransfer> reqList = new ArrayList<ClientsListTransfer>();
		List<Object[]> clientsList = null;
		Query query = null;

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		if (searchField.equalsIgnoreCase("clientName") || "clientName".contains(searchField)) {
			searchField = "c.clientName";
		} else if (searchField.equalsIgnoreCase("startDate") || "startDate".contains(searchField)) {
			searchField = "c.startDate";
		} else if (searchField.equalsIgnoreCase("customerType") || "customerType".contains(searchField)) {
			searchField = "ct.customerType";
		} else if (searchField.equalsIgnoreCase("primaryContact") || "primaryContact".contains(searchField)) {
			searchField = "u1.name";
		} else if (searchField.equalsIgnoreCase("secondaryContact") || "secondaryContact".contains(searchField)) {
			searchField = "u2.name";
		}

		String sql = " SELECT c.id, c.clientName,ct.customerType, c.startDate, u1.name AS primaryContact, u2.name AS secondaryContact  FROM `client` c "
				+ "INNER JOIN `customertype` ct ON c.customerType_id = ct.id "
				+ "INNER JOIN `user` u1 ON u1.id = c.primaryContact_id "
				+ "INNER JOIN `user` u2 ON u2.id = c.secondaryContact_id " + "WHERE ";
		StringBuilder sqlBuilder = new StringBuilder(sql);
		/*
		 * String countquery =
		 * " SELECT count(*) FROM `client` c INNER JOIN `customertype` ct ON c.customerType_id = ct.id "
		 * +
		 * "INNER JOIN `user` u1 ON u1.id = c.primaryContact_id INNER JOIN `user` u2 ON u2.id = c.secondaryContact_id WHERE "
		 * ;
		 */

		try {
			if (null != id) {
				if (role.equalsIgnoreCase("BDM")) {
					sqlBuilder.append(" (c.primaryContact_id=? OR c.secondaryContact_id=?) AND " + searchField
							+ " LIKE '%" + searchInput + "%' ORDER BY c.id DESC LIMIT " + startingRow + "," + pageSize);
					query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
					query.setParameter(1, id);
					query.setParameter(2, id);
					// countquery.concat(" c.primaryContact_id=? OR
					// c.secondaryContact_id=? ");
				} else {
					sqlBuilder.append(" c.accountManger_id = ? AND " + searchField + " LIKE '%" + searchInput
							+ "%' ORDER BY c.id DESC LIMIT " + startingRow + "," + pageSize);
					query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
					query.setParameter(1, id);
					// countquery.concat(" c.accountManger_id = ? ");
				}
			} else {
				sqlBuilder.append(searchField + " LIKE '%" + searchInput + "%' ORDER BY req.id DESC LIMIT "
						+ startingRow + "," + pageSize);
				query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
			}

			clientsList = query.getResultList();
			totalRecords = clientsList.size();
			if (clientsList.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
			} else {
				for (Object[] req : clientsList) {
					ClientsListTransfer transferObj = new ClientsListTransfer();
					transferObj.setId(Long.valueOf(req[0] + ""));
					transferObj.setClientName((String) req[1]);
					transferObj.setCustomerType((String) req[2]);
					transferObj.setStartDate((Date) req[3]);
					transferObj.setPrimaryContact((String) req[4]);
					transferObj.setSecondaryContact((String) req[5]);
					reqList.add(transferObj);
				}
				response = new Response(ExceptionMessage.OK, reqList);
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
			return new Response(ExceptionMessage.Exception, "500",
					"Unknown Column '" + searchField + "' .  Enter Correct Column Name. " + pe.getLocalizedMessage());

		} catch (Exception e) {
			return new Response(ExceptionMessage.Exception, "500",
					" Unable to Search Customers " + " " + e.getLocalizedMessage());
		}
	}

}
