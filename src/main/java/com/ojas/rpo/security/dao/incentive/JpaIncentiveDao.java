package com.ojas.rpo.security.dao.incentive;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.CandidateData;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.IncentiveNew;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.transfer.IncentivesData;

public class JpaIncentiveDao extends JpaDao<IncentiveNew, Long> implements IncentiveDao {
	// StoredProcedureQuery query=null;
	public JpaIncentiveDao() {
		super(IncentiveNew.class);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional
	public IncentiveNew save(IncentiveNew entity) {
		return this.getEntityManager().merge(entity);
	}

	public Response getRecruiterIncentive(Long userId) {
		StoredProcedureQuery query = null;
		try {

			query = getEntityManager().createNamedStoredProcedureQuery("incentiveUnProceedStepUp")
					.setParameter("userId", userId);

			query.execute();
		} catch (Exception e) {
		}
		try {
			query = getEntityManager().createNamedStoredProcedureQuery("CalIncentiveForDebitStepDown")
					.setParameter("userId", userId);

			query.execute();
		} catch (Exception e) {
		}
		try {
			query = getEntityManager().createNamedStoredProcedureQuery("calIncentives").setParameter("userId", userId);

			query.execute();
		} catch (Exception e) {
		}
		try {
			query = getEntityManager().createNamedStoredProcedureQuery("CalIncentiveDebit").setParameter("userId",
					userId);

			query.execute();
		} catch (Exception e) {
		}

		return new Response(ExceptionMessage.StatusSuccess, "200", "Successfuly incentives generated");
	}

	/**
	 * 
	 */

	public Response getAllRecruitersIncentives(List<CandidateData> users) {
		StoredProcedureQuery query = null;

		for (CandidateData user : users) {

			try {

				query = getEntityManager().createNamedStoredProcedureQuery("incentiveUnProceedStepUp")
						.setParameter("userId", user.getUserId());

				query.execute();
			} catch (Exception e) {
			}
			try {
				query = getEntityManager().createNamedStoredProcedureQuery("CalIncentiveForDebitStepDown")
						.setParameter("userId", user.getUserId());

				query.execute();
			} catch (Exception e) {
			}
			try {
				query = getEntityManager().createNamedStoredProcedureQuery("calIncentives").setParameter("userId",
						user.getUserId());

				query.execute();
			} catch (Exception e) {
			}
			try {
				query = getEntityManager().createNamedStoredProcedureQuery("CalIncentiveDebit").setParameter("userId",
						user.getUserId());

				query.execute();
			} catch (Exception e) {
			}

		}
		return new Response(ExceptionMessage.StatusSuccess, "200", "Successfuly incentives generated");

	}

	@Override
	public Response getIncentiveList(String role, Long id, Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField) {

		String sortOrder = "ASC";
		String sortField = "incen.id";
		Integer totalRecords = 0;
		Response response = null;
		Query countQuery = null;
		Query query = null;
		List<IncentivesData> list = new ArrayList<IncentivesData>();

		if ((null != sortingOrder) && (sortingOrder.startsWith("asc") || sortingOrder.equalsIgnoreCase("asc"))) {
			sortOrder = "ASC";
		} else {
			sortOrder = "DESC";
		}

		if (null != sortingField && !sortingField.equals("undefined") && !sortingField.isEmpty()) {
			if (sortingField.equalsIgnoreCase("recId") || "recId".contains(sortingField)) {
				sortField = "incen.recId";
			} else if (sortingField.equalsIgnoreCase("date") || "date".contains(sortingField)) {
				sortField = "incen.date";
			}
		} else {
			sortField = "incen.recId";
		}

		if (null != searchField && !searchField.equals("undefined") && !searchField.isEmpty()) {
			if (searchField.equalsIgnoreCase("recId") || "recId".contains(searchField)) {
				searchField = "incen.recId";
			} else if (searchField.equalsIgnoreCase("incen.date") || "incen.date".contains(searchField)) {
				searchField = "incen.date";
			}

		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		String sql = " select incen.id as incenId,incen.date as calDate,incen.recId as  recid, incen.canId as canId ,incen.cr_Amount,"
				+ "   incen.dr_Amount ,can.firstName as cfname, can.lastName as clname,users.email ,users.firstName as ufanme,users.lastName as ulname, "
				+ "   users.role   from  incentivenew incen ,user users   , candidate can  where  can.id =incen.canId and users.id=incen.recId ";

		String countSql = " SELECT count(*) from   incentivenew incen , user users  where incen.date>=(CURDATE()-INTERVAL 1 MONTH) and users.id=incen.recId ";

		StringBuilder sqlbuilder = new StringBuilder(sql);
		if (role.equalsIgnoreCase("BDM") || role.equalsIgnoreCase("recruiter")) {

			sqlbuilder.append(" and incen.recId = " + id);

			countSql = countSql + " and incen.recId=" + id;
		}

		if (role.equalsIgnoreCase("Lead")) {

			sqlbuilder.append(" and ( incen.recId=" + id + " or users.reportingId_id=" + id + ")");

			countSql = countSql + " and ( incen.recId=" + id + " or users.reportingId_id=" + id + ")";
		}

		if ((null != searchText) && (null != searchField) && !searchField.equalsIgnoreCase("undefined")) {
			if (searchField.equalsIgnoreCase("incen.date")) {
				searchText = getDate(searchText);
			}
			sqlbuilder.append(" AND " + searchField + "='" + searchText + "' ORDER BY " + sortField + " " + sortOrder
					+ " LIMIT " + startingRow + "," + pageSize);
		} else {
			sqlbuilder.append(" and incen.date>=(CURDATE()-INTERVAL 1 MONTH) ORDER BY " + sortField + " " + sortOrder
					+ " LIMIT " + startingRow + "," + pageSize);
		}

		query = this.getEntityManager().createNativeQuery(sqlbuilder.toString());

		countQuery = this.getEntityManager().createNativeQuery(countSql.toString());

		List<Object[]> searchList = query.getResultList();
		if (searchList.isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
		} else {
			for (Object[] req : searchList) {
				IncentivesData transferObj = new IncentivesData();
				transferObj.setId(Long.parseLong(req[0].toString()));
				transferObj.setDate((Date) req[1]);
				transferObj.setRecId(Long.parseLong(req[2].toString()));
				transferObj.setCanId(Long.parseLong(req[3].toString()));
				if (req[4] != null) {
					transferObj.setCr_Amount(Double.parseDouble(req[4].toString()));
				}
				if (req[5] != null) {
					transferObj.setDr_Amount(Double.parseDouble(req[5].toString()));
				}
				transferObj.setCandidateName((String) req[6] + (String) req[7]);
				transferObj.setEmailId((String) req[8]);

				transferObj.setRecruiterName((String) req[9] + (String) req[10]);
				transferObj.setRole((String) req[11]);
				list.add(transferObj);
			}
			response = new Response(ExceptionMessage.StatusSuccess, list);
		}

		totalRecords = Integer.valueOf(countQuery.getSingleResult().toString());
		response.setTotalRecords(totalRecords);
		if (totalRecords == 0) {
			response.setTotalRecords(totalRecords);
			response.setTotalPages(0);
		}

		Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
		totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
		response.setTotalPages(totalPages);

		return response;

	}

	public String getDate(String input) {

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);
		Date date = null;
		try {
			date = inputFormat.parse(input);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		// outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		String output = outputFormat.format(date);
		return output;
	}

}
