package com.ojas.rpo.security.dao.candidateRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.CandidateRequestByRecruiters;
import com.ojas.rpo.security.entity.CandidateRequestByRecruitersRequestDto;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;

public class JpaCandidateRequestByRecruiters extends JpaDao<CandidateRequestByRecruiters, Long>
		implements CandidateRequestByRecruitersDao {

	public JpaCandidateRequestByRecruiters() {
		super(CandidateRequestByRecruiters.class);
	}

	@Transactional
	public Response saveRequest(CandidateRequestByRecruitersRequestDto dto) {

		Response response = null;
		try {
			// Adding the New REcruiter Request

			Query q = this.getEntityManager().createNativeQuery(
					"INSERT INTO candidaterequestbyrecruiters(requestStatus,requestedDate,candidateId,ownerUserId,requestedUserId) VALUES(?,?,?,?,?)");
			q.setParameter(1, dto.getRequestStatus());
			q.setParameter(2, java.sql.Date.valueOf(LocalDate.now()));
			q.setParameter(3, dto.getCandidateId());
			q.setParameter(4, dto.getOwnerUserId());
			q.setParameter(5, dto.getRequestedUserId());
			int update = q.executeUpdate();
			if (update > 0) {
				response = new Response(ExceptionMessage.OK, "Request For Candidate Is Successfull");
			} else {
				response = new Response(ExceptionMessage.OK, "Request For Candidate Is Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.OK, "Unable to Add Request By Recruter due to :" + e.getMessage());
		}
		return response;

	}

	@Override
	public Response getAllcandidateRequestByRecruiters(Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField, Long loginId, String userrole) {
		Response response = null;
		Integer totalRecords = 0;
		String sortOrder = "DESC";
		String sortField = "ss.id";
		if ((null != sortingOrder) && (sortingOrder.startsWith("asc") || sortingOrder.equalsIgnoreCase("asc"))) {
			sortOrder = "ASC";
		}
		/*
		 * if (null != sortingField &&
		 * !sortingField.equalsIgnoreCase("undefined")) { if
		 * (sortingField.equalsIgnoreCase("id") || "id".contains(sortingField))
		 * { sortField = "id"; } if (null != searchField &&
		 * !searchField.equalsIgnoreCase("undefined")) { if
		 * (searchField.equalsIgnoreCase("id") || "id".contains(searchField)) {
		 * searchField = "id"; } } }
		 */

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}
		
		try {
			Query q = null;
			Query q1 = null;
			if (null != searchField && null != searchText && !searchField.equalsIgnoreCase("undefined")
					&& !searchText.equalsIgnoreCase("undefined")) {
				q = this.getEntityManager()
						.createNativeQuery("SELECT ss.id as ssid , can.id as candidateId,  "
								+ "  concat(can.firstName  ,'  ' , can.lastName ) as candName "
								+ "  , can.mobile,can.email , concat(s.firstName  ,'  ' , s.lastName ) "
								+ "   as ownerName,concat(s1.firstName  ,'  ' , s1.lastName ) as requestedName ,ss.requestedDate ,ss.requestStatus ,ss.ownerUserId ,"
								+ "  ss.requestedUser_id FROM candidaterequestbyrecruiters ss  "
								+ "     inner join user s on ss.ownerUserId=s.id  "
								+ "    inner join user s1 on ss.requestedUserId=s1.id "
								+ "    inner join candidate can on ss.candidateId=can.id "
								+ "     WHERE  (ss.ownerUserId = ? OR ss.requestedUserId = ?) and " + searchField
								+ " LIKE '%" + searchText + "%' ORDER BY " + sortField + " " + sortOrder + " LIMIT "
								+ startingRow + "," + pageSize);
				q.setParameter(1, loginId);
				q.setParameter(2, loginId);
			} else {
				q = this.getEntityManager()
						.createNativeQuery("SELECT ss.id as ssid , can.id as candidateId,  "
								+ "  concat(can.firstName  ,'  ' , can.lastName ) as candName "
								+ "  , can.mobile,can.email , concat(s.firstName  ,'  ' , s.lastName ) "
								+ "   as ownerName,concat(s1.firstName  ,'  ' , s1.lastName ) as requestedName ,ss.requestedDate ,ss.requestStatus ,"
								+ "  ss.ownerUserId ,"
								+ "  ss.requestedUserId FROM candidaterequestbyrecruiters ss  "
								+ "     inner join user s on ss.ownerUserId=s.id  "
								+ "    inner join user s1 on ss.requestedUserId=s1.id "
								+ "    inner join candidate can on ss.candidateId=can.id  and  (ss.ownerUserId = ? OR ss.requestedUserId = ?) ORDER BY "
								+ sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				q.setParameter(1, loginId);
				q.setParameter(2, loginId);
			}

			q1 = this.getEntityManager()
					.createNativeQuery("SELECT count(*)   FROM candidaterequestbyrecruiters ss  "
							+ "     inner join user s on ss.ownerUserId=s.id  "
							+ "    inner join user s1 on ss.requestedUserId=s1.id "
							+ "    inner join candidate can on ss.candidateId=can.id ");

			totalRecords = Integer.parseInt(q1.getSingleResult() + "");

			List<Object[]> results = q.getResultList();
			List<CandidateRequestByRecruitersRequestDto> list = new ArrayList<CandidateRequestByRecruitersRequestDto>();
			for (Object[] data : results) {
				CandidateRequestByRecruitersRequestDto dto = new CandidateRequestByRecruitersRequestDto();
				dto.setId(Long.valueOf(data[0] + ""));
				dto.setCandidateId(Long.parseLong(data[1]+""));
				dto.setCandidateName(data[2]+"");
				dto.setMobile(data[3]+"");
				dto.setEmail(data[4]+"");
				dto.setOwnerName(data[5]+"");
				dto.setRequestedRecuiterName(data[6]+"");
				if(data[7]!=null){
				dto.setRequestedDate((Date)(data[7]));
				}
				dto.setRequestStatus(data[8]+"");
				dto.setOwnerUserId(Long.parseLong(data[9]+""));
				dto.setRequestedUserId(Long.parseLong(data[10]+""));
				list.add(dto);
			}

			response = new Response(ExceptionMessage.OK, list);
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
			response = new Response(ExceptionMessage.Exception,
					"Unable to Retrieve Records Due to : " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response getcandidateRequestByRecruitersId(Long id) {
		Response response = null;
		CandidateRequestByRecruiters result = find(id);
		if (null != result) {
			response = new Response(result, ExceptionMessage.OK);
		} else {
			response = new Response(ExceptionMessage.DataIsEmpty, "No record Found");
		}
		return response;
	}

	@Override
	public CandidateRequestByRecruiters getRequestByCandidateAndRecruiter(Long candidateId, Long requestedUserId) {
		Query q = this.getEntityManager().createNativeQuery("select * from candidaterequestbyrecruiters where candidateId = ? and requestedUserId = ?");
		q.setParameter(1, candidateId);
		q.setParameter(2, requestedUserId);
		List<Object[]> result = q.getResultList();
		CandidateRequestByRecruiters object = new CandidateRequestByRecruiters();
		if(null != result && !result.isEmpty()){
			return null;
		}else{
			for (Object[] data : result) {
				
				object.setId(Long.valueOf(data[0]+""));
				object.setCandidateId(Long.valueOf(data[1]+""));
				object.setOwnerUserId(Long.valueOf(data[2]+""));
				object.setRequestStatus(data[3]+"");
				object.setRequestedDate((Date)data[4]);
				object.setRequestedUserId(Long.valueOf(data[5]+""));
				
			}
		}
		return object;
	}
	
	public void deleteRequestByCandidateAndRecruiter(Long candidateId, Long requestedUserId){
		if(null != getRequestByCandidateAndRecruiter(candidateId,requestedUserId)){
			Query q = this.getEntityManager().createNativeQuery("delete from candidaterequestbyrecruiters where candidateId = ? and requestedUserId = ?");
			q.setParameter(1, candidateId);
			q.setParameter(2, requestedUserId);
			q.executeUpdate();
		}
	}
}
