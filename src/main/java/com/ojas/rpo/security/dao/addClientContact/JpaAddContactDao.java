package com.ojas.rpo.security.dao.addClientContact;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.ojas.rpo.security.entity.AddContact;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.transfer.ContactListTransfer;

public class JpaAddContactDao extends JpaDao<AddContact, Long> implements AddContactDao {

	public JpaAddContactDao() {
		super(AddContact.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional
	public AddContact save(AddContact entity) {
		// TODO Auto-generated method stub
		return this.getEntityManager().merge(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AddContact> findContactByClientId(Long id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AddContact> cq = cb.createQuery(AddContact.class);
		Root<AddContact> e = cq.from(AddContact.class);
		Join<AddContact, AddContact> r = e.join("client", JoinType.LEFT);
		Predicate p = cb.equal(r.get("id"), id);
		cq.where(p);
		TypedQuery<AddContact> tq = getEntityManager().createQuery(cq);
		return tq.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<AddContact> findActiveContactByClientId(Long id, String role, Long userId) {
		Predicate p = null;
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AddContact> cq = cb.createQuery(AddContact.class);
		Root<AddContact> e = cq.from(AddContact.class);
		Join<AddContact, AddContact> r = e.join("client", JoinType.LEFT);
		if (role.equalsIgnoreCase("Lead")) {
					p = cb.and(cb.equal(r.get("id"), id), cb.equal(e.get("status"), "Active"),
					cb.equal(e.get("lead_id"), userId));
		} else if (role.equalsIgnoreCase("AM")) {
			p = cb.and(cb.equal(r.get("id"), id), cb.equal(e.get("status"), "Active"),
					cb.equal(e.get("accountManger_id"), userId));
		} else if (role.equalsIgnoreCase("BDM")) {
			p = cb.and(cb.equal(r.get("id"), id), cb.equal(e.get("status"), "Active"),
					cb.equal(e.get("primaryContact_id"), userId));
		} 

		cq.where(p);
		TypedQuery<AddContact> tq = getEntityManager().createQuery(cq);
		return tq.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Response findContactByBdmId(Long id, String role, String sortField, String sortOrder, String searchField,
			String searchText, Integer pageNo, Integer pageSize) {
		Response response = null;
		Query query = null;
		Query query1 = null;
		int totalRecords = 0;
		String sql = "SELECT a.contact_Name,a.mobile,a.email,c.clientName,a.id , u1.firstName as bdm ,u2.firstName as secbdm, "
				+ " u3.firstName as amname ,u4.firstName as leadname ,a.lastUpdatedDate FROM `addcontact` a INNER JOIN `client` c "
				+ "on a.client_id=c.id " + "INNER JOIN `user` u1 ON  u1.id = a.primaryContact_id "
				+ "INNER JOIN `user` u2 ON u2.id = a.secondaryContact_id "
				+ "INNER JOIN `user` u3 ON u3.id = a.accountManger_id " + " INNER JOIN `user` u4 ON u4.id = a.lead_id ";
		StringBuilder sqlbuilder = new StringBuilder(sql);

		if (null != sortField && !sortField.isEmpty() && !sortField.equalsIgnoreCase("undefined")) {
			if (sortField.equalsIgnoreCase("contactName")) {
				sortField = "a.contact_Name";
			}
			if (sortField.equalsIgnoreCase("clientName")) {
				sortField = "c.clientName";
			}
			if (sortField.equalsIgnoreCase("email")) {
				sortField = "a.email";
			}
			if (sortField.equalsIgnoreCase("mobile")) {
				sortField = "a.mobile";
			}
			if (sortField.equalsIgnoreCase("id")) {
				sortField = "a.id";
			}
		} else {
			sortField = "a.lastUpdatedDate";
		}

		if (sortOrder.equalsIgnoreCase("ASC")) {
			sortOrder = "ASC";
		} else {
			sortOrder = "DESC";
		}

		if (null != searchField && !searchField.isEmpty() && !searchField.equalsIgnoreCase("undefined")) {
			if (searchField.equalsIgnoreCase("contactName")) {
				searchField = "a.contact_Name";
			}
			if (searchField.equalsIgnoreCase("clientName")) {
				searchField = "c.clientName";
			}
			if (searchField.equalsIgnoreCase("email")) {
				searchField = "a.email";
			}
			if (searchField.equalsIgnoreCase("mobile")) {
				searchField = "a.mobile";
			}
			if (searchField.equalsIgnoreCase("id")) {
				searchField = "a.id";
			}
		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		try {
			if (role.equalsIgnoreCase("BDM")) {
				sqlbuilder.append(" WHERE (a.primaryContact_id=? OR a.secondaryContact_id=?) ");

				if (null != searchField && !searchField.equals("undefined") && null != searchText
						&& !searchText.equals("undefined") && !searchText.isEmpty()) {
					sqlbuilder.append(" AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortField + " "
							+ sortOrder + " LIMIT " + startingRow + "," + pageSize);
				} else {
					sqlbuilder.append(
							" ORDER BY " + sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				}

				System.out.println("query = " + sqlbuilder.toString());

				String queryCount = "SELECT count(*) FROM addcontact a INNER JOIN client c on a.client_id=c.id  "
						+ " WHERE (a.primaryContact_id=? OR a.secondaryContact_id=?)";

				query1 = getEntityManager().createNativeQuery(queryCount.toString());
				query1.setParameter(1, id);
				query1.setParameter(2, id);
				totalRecords = Integer.parseInt(query1.getSingleResult().toString());
				query = getEntityManager().createNativeQuery(sqlbuilder.toString());
				query.setParameter(1, id);
				query.setParameter(2, id);
			} else if (role.equalsIgnoreCase("AM")) {

				sqlbuilder.append(" WHERE a.accountManger_id=? ");
				if (null != searchField && !searchField.equals("undefined") && null != searchText
						&& !searchText.equals("undefined") && !searchText.isEmpty()) {
					sqlbuilder.append(" AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortField + " "
							+ sortOrder + " " + " LIMIT " + startingRow + "," + pageSize);
				} else {
					sqlbuilder.append(
							" ORDER BY " + sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				}

				String countQuery = "SELECT count(*) FROM `addcontact` a INNER JOIN `client` c on a.client_id=c.id  WHERE a.accountManger_id=?";
				query1 = getEntityManager().createNativeQuery(countQuery.toString());
				query1 = getEntityManager().createNativeQuery(countQuery.toString());
				query1.setParameter(1, id);
				totalRecords = Integer.parseInt(query1.getSingleResult().toString());

				query = getEntityManager().createNativeQuery(sqlbuilder.toString());
				query.setParameter(1, id);
			} else if (role.equalsIgnoreCase("Lead")) {

				sqlbuilder.append(" WHERE a.lead_id=? ");
				if (null != searchField && !searchField.equals("undefined") && null != searchText
						&& !searchText.equals("undefined") && !searchText.isEmpty()) {
					sqlbuilder.append(" AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortField + " "
							+ sortOrder + " " + " LIMIT " + startingRow + "," + pageSize);
				} else {
					sqlbuilder.append(
							" ORDER BY " + sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				}

				String countQuery = "SELECT count(*) FROM `addcontact` a INNER JOIN `client` c on a.client_id=c.id  WHERE a.lead_id=?";
				query1 = getEntityManager().createNativeQuery(countQuery.toString());
				query1 = getEntityManager().createNativeQuery(countQuery.toString());
				query1.setParameter(1, id);
				totalRecords = Integer.parseInt(query1.getSingleResult().toString());

				query = getEntityManager().createNativeQuery(sqlbuilder.toString());
				query.setParameter(1, id);
			}

			List<Object[]> results = query.getResultList();
			List<ContactListTransfer> contactList = new ArrayList<ContactListTransfer>();
			if (!results.isEmpty()) {
				for (Object[] data : results) {
					ContactListTransfer contact = new ContactListTransfer();

					contact.setContactName(data[0] + "");
					contact.setMobile((String) data[1]);
					contact.setEmail(data[2] + "");
					contact.setClientName(data[3] + "");
					contact.setId(data[4] + "");
					contact.setBdmName(data[5] + "");
					contact.setAmName(data[7] + "");
					contact.setLeadName(data[8] + "");
					contact.setLastUpdatedDate((Date) data[9]);
					contactList.add(contact);
				}

				response = new Response(ExceptionMessage.OK, contactList);
			} else {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Contacts Found");
			}
			response.setTotalRecords(totalRecords);

			if (totalRecords == 0) {
				response.setTotalRecords(totalRecords);
				response.setTotalPages(0);
			} else {
				Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
				totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
				response.setTotalPages(totalPages);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500",
					" Unable Retrieve Contact List Due to following Exception : " + e.getMessage());
		}

		return response;

	}

	@Override
	@Transactional
	public int updatingStatus(Long id, String status) {
		int i = 0;
		if (status != null) {
			Query q = getEntityManager().createNativeQuery("update addcontact set status=?  where id =?");
			q.setParameter(1, status);
			q.setParameter(2, id);
			i = q.executeUpdate();

		}

		return i;

	}

	@Transactional(readOnly = true)
	@Override
	public Integer findActiveContactByEmail(Long clientId, String email) {
		Query query = null;
		String sql = "SELECT * from addcontact where email='" + email + "' and client_id=" + clientId;
		query = getEntityManager().createNativeQuery(sql);
		List<Object[]> results = query.getResultList();

		if (results != null && results.size() > 0) {
			return results.size();
		}

		return 0;
		// query.setParameter(1, id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Object[]> findActiveContactByEmailList(Long clientId, String email) {
		Query query = null;
		String sql = "SELECT id,client_id from addcontact where email='" + email + "' and client_id=" + clientId;
		query = getEntityManager().createNativeQuery(sql);
		List<Object[]> results = query.getResultList();

		return results;

	}
}
