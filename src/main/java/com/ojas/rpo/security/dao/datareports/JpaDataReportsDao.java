package com.ojas.rpo.security.dao.datareports;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.CandidateMapping;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.ReportInputDataDTO;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.transfer.ReportResultsTransfer;
import com.ojas.rpo.security.transfer.ReportsListTransfer;

public class JpaDataReportsDao extends JpaDao<CandidateMapping, Long> implements DataReportsDao {

	public JpaDataReportsDao() {
		super(CandidateMapping.class);
	}

	@Autowired
	private ReportsBarChartTemplates barChartTemplates;

	@Autowired
	private UserDao userDao;

	@Override
	public Response getTotalSubmittedAndRejected(Long loginId, String role) {
		Response response = null;
		ReportResultsTransfer result = new ReportResultsTransfer();
		String sql = "SELECT  "
				+ "COUNT(CASE WHEN candidateStatus like '%Rejected%' THEN candidateStatus END) AS rejectedCount, "
				+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
				+ "FROM candidatemapping maping ";
		if (role.equalsIgnoreCase("recruiter")) {

			sql = sql + " where maping.mappedUser_id = " + loginId;

		}
		if (role.equalsIgnoreCase("Lead")) {

			sql = sql
					+ "  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id  and maping.bdmReq_id=req.id and (addcontacts.lead_id="
					+ loginId + " or maping.mappedUser_id=" + loginId + ")";
		}

		if (role.equalsIgnoreCase("AM")) {

			sql = sql
					+ " , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.accountManger_id="
					+ loginId + " or maping.mappedUser_id=" + loginId + ")";
		}

		if (role.equalsIgnoreCase("BDM")) {

			sql = sql
					+ " , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.primaryContact_id="
					+ loginId + " or maping.mappedUser_id=" + loginId + ")";
		}

		List<Object[]> resultList;
		try {
			Query createNativeQuery = this.getEntityManager().createNativeQuery(sql);
			resultList = createNativeQuery.getResultList();
			result = new ReportResultsTransfer();
			if (!resultList.isEmpty()) {
				for (Object[] data : resultList) {
					result.setTotalRejections(Integer.valueOf(data[0] + ""));
					result.setTotalSubmissions(Integer.valueOf(data[1] + ""));
				}

				String encodedString = barChartTemplates.getSubmittedAndRejected(result.getTotalSubmissions(),
						result.getTotalRejections());
				response = new Response(ExceptionMessage.OK, result, encodedString);

			} else {
				new Response(ExceptionMessage.DataIsEmpty, "No Results Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Response(ExceptionMessage.Exception, "Unable to Fetch Results");
		}
		return response;
	}

	@Override
	public Response getSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto) {
		Response response = null;
		StringBuilder countsql = null;
		ReportResultsTransfer result = new ReportResultsTransfer();
		String role = inputDto.getRole();
		try {
			Long userId = inputDto.getRoleUserId();
			if (null != inputDto) {

				if (inputDto.getReportType().contains("day")) {
					String date = inputDto.getDate();
					if (date == null) {
						date = " CURDATE() ";
					}

					result.setReportTypeValue(date);
					if (role.equalsIgnoreCase("recruiter")) {

						countsql = new StringBuilder("SELECT "
								+ "(SELECT count(*) FROM candidatemapping maping WHERE DATE(maping.lastUpdatedDate) = DATE('"
								+ date + "') " + " AND candidateStatus LIKE '%rejected%' AND mappedUser_id = " + userId
								+ ") AS rejections ,(SELECT count(*) FROM candidatemapping maping WHERE DATE(maping.lastUpdatedDate) = DATE('"
								+ date + "') AND mappedUser_id = " + userId + ") AS submissions");

						result.setUserId(userId);
					}

					else if (role.equalsIgnoreCase("Lead")) {

						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN candidateStatus like '%Rejected%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								" , bdmreq  req, addcontact addcontacts " + " where req.addContact_id=addcontacts.id "
										+ " and maping.bdmReq_id=req.id and (addcontacts.lead_id=" + userId + " "
										+ " or maping.mappedUser_id=" + userId
										+ ")  and DATE(maping.lastUpdatedDate) = DATE('" + date + "') ");
					}

					else if (role.equalsIgnoreCase("AM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN candidateStatus like '%Rejected%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");
						countsql.append(
								" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.accountManger_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and DATE(maping.lastUpdatedDate) = DATE('" + date + "')");
					}

					else if (role.equalsIgnoreCase("BDM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN candidateStatus like '%Rejected%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.primaryContact_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and DATE(maping.lastUpdatedDate) = DATE('" + date + "')");
					}

				}

				else if (inputDto.getReportType().contains("month")) {
					Integer month = inputDto.getMonth();
					Integer year = inputDto.getYear();
					/*
					 * if (month == null && year == null) { LocalDate ld =
					 * LocalDate.now(); month = ld.getMonthValue(); year =
					 * ld.getYear(); }
					 */

					result.setReportTypeValue(Month.of(month) + " " + year);
					if (inputDto.getRole().equalsIgnoreCase("recruiter")) {
						countsql = new StringBuilder("SELECT "
								+ "(SELECT count(*) FROM candidatemapping maping WHERE MONTH(maping.lastUpdatedDate)='"
								+ month + "' AND YEAR(lastUpdatedDate)=" + year
								+ " AND candidateStatus LIKE '%rejected%' AND mappedUser_id = " + userId
								+ ") AS rejections, (SELECT count(*) FROM candidatemapping maping WHERE MONTH(maping.lastUpdatedDate)='"
								+ month + "' AND YEAR(maping.lastUpdatedDate)=" + year + " AND mappedUser_id = "
								+ userId + ") AS submissions  ");
						result.setUserId(userId);
					}

					else if (role.equalsIgnoreCase("Lead")) {

						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN candidateStatus like '%Rejected%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								" , bdmreq  req, addcontact addcontacts " + " where req.addContact_id=addcontacts.id "
										+ " and maping.bdmReq_id=req.id and (addcontacts.lead_id=" + userId + " "
										+ " or maping.mappedUser_id=" + userId
										+ ")  and MONTH(maping.lastUpdatedDate) = '" + month + "'");
					}

					else if (role.equalsIgnoreCase("AM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN candidateStatus like '%Rejected%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");
						countsql.append(
								" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.accountManger_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and MONTH(maping.lastUpdatedDate) = '" + month + "'");
					}

					else if (role.equalsIgnoreCase("BDM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN candidateStatus like '%Rejected%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.primaryContact_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and MONTH(maping.lastUpdatedDate) = '" + month + "'");
					}

				} else if (inputDto.getReportType().contains("year")) {
					Integer year = inputDto.getYear();
					if (year == null) {
						LocalDate ld = LocalDate.now();
						year = ld.getYear();
					}

					result.setReportTypeValue(year.toString());
					if (inputDto.getRole().equalsIgnoreCase("recruiter")) {
						countsql = new StringBuilder(
								"SELECT (SELECT count(*) FROM candidatemapping maping WHERE YEAR(maping.lastUpdatedDate)="
										+ year + " AND candidateStatus LIKE '%rejected%' AND mappedUser_id = " + userId
										+ ") AS rejections ,"
										+ " (SELECT count(*) FROM candidatemapping maping WHERE YEAR(maping.lastUpdatedDate)="
										+ year + " AND mappedUser_id = " + userId + ") AS submissions ");
						result.setUserId(userId);
					}

					else if (role.equalsIgnoreCase("Lead")) {

						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN candidateStatus like '%Rejected%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								" , bdmreq  req, addcontact addcontacts " + " where req.addContact_id=addcontacts.id "
										+ " and maping.bdmReq_id=req.id and (addcontacts.lead_id=" + userId + " "
										+ " or maping.mappedUser_id=" + userId
										+ ")  and year(maping.lastUpdatedDate) = '" + year + "'");
					}

					else if (role.equalsIgnoreCase("AM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN candidateStatus like '%Rejected%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");
						countsql.append(
								" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.accountManger_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and year(maping.lastUpdatedDate) = '" + year + "'");
					}

					else if (role.equalsIgnoreCase("BDM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN candidateStatus like '%Rejected%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.primaryContact_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and year(maping.lastUpdatedDate) = '" + year + "'");
					}

				}

				if (null != countsql) {
					Query createNativeQuery = this.getEntityManager().createNativeQuery(countsql.toString());
					List<Object[]> resultList = createNativeQuery.getResultList();
					for (Object[] data : resultList) {
						result.setTotalRejections(Integer.valueOf(data[0] + ""));
						result.setTotalSubmissions(Integer.valueOf(data[1] + ""));
					}
					result.setReportType(inputDto.getReportType());
					String base64 = barChartTemplates.getSubmissionsVsRejectionsByReportType(result);
					result.setBase64String(base64);
					response = new Response(result, ExceptionMessage.OK);

				} else {
					response = new Response(ExceptionMessage.Bad_Request, "Invalid Request");
				}
			}

		} catch (Exception e) {
			response = new Response(ExceptionMessage.Exception, "Unable to Fetch Results");
			e.printStackTrace();
		}

		return response;
	}

	public Response getRecruiterWiseSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto) {
		Response response = null;

		Query q1 = null;
		Query q2 = null;
		ReportResultsTransfer result = new ReportResultsTransfer();

		StringBuilder submissionsCountQuery = new StringBuilder(
				" SELECT mappedUser_id, COUNT(*) AS SUBMISSIONS FROM candidatemapping maping ");
		StringBuilder rejectionsCountQuery = new StringBuilder(
				" SELECT mappedUser_id, COUNT(*) AS REJECTIONS FROM candidatemapping maping");
		String role = inputDto.getRole();
		Long userId = inputDto.getRoleUserId();

		if (role.equalsIgnoreCase("Lead")) {

			submissionsCountQuery.append(" , bdmreq  req, addcontact addcontacts "
					+ " where req.addContact_id=addcontacts.id " + " and maping.bdmReq_id=req.id");

			rejectionsCountQuery.append(" , bdmreq  req, addcontact addcontacts "
					+ " where req.addContact_id=addcontacts.id " + " and maping.bdmReq_id=req.id");

			if (inputDto.getSelectedId() != null) {
				submissionsCountQuery.append("and (addcontacts.lead_id=" + userId + " " + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ")");
				rejectionsCountQuery.append("and (addcontacts.lead_id=" + userId + " " + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ")");
			} else {
				submissionsCountQuery.append("and (addcontacts.lead_id=" + userId + " )");
				rejectionsCountQuery.append("and (addcontacts.lead_id=" + userId + " )");
			}

		}

		else if (role.equalsIgnoreCase("AM")) {

			submissionsCountQuery.append(
					" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id ");
			rejectionsCountQuery.append(
					" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id ");

			if (inputDto.getSelectedId() != null) {
				submissionsCountQuery.append("and (addcontacts.accountManger_id=" + userId + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ")");
				rejectionsCountQuery.append("and (addcontacts.accountManger_id=" + userId + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ")");
			} else {
				submissionsCountQuery.append("and (addcontacts.accountManger_id=" + userId + " )");
				rejectionsCountQuery.append("and (addcontacts.accountManger_id=" + userId + " )");
			}

		}

		else if (role.equalsIgnoreCase("BDM")) {

			submissionsCountQuery.append(
					"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id ");

			rejectionsCountQuery.append(
					"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id ");
			if (inputDto.getSelectedId() != null) {
				submissionsCountQuery.append("and (addcontacts.primaryContact_id=" + userId
						+ " or maping.mappedUser_id=" + inputDto.getSelectedId() + ") ");
				rejectionsCountQuery.append("and (addcontacts.primaryContact_id=" + userId + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ") ");
			} else {
				submissionsCountQuery.append("and (addcontacts.primaryContact_id=" + userId + " ) ");
				rejectionsCountQuery.append("and (addcontacts.primaryContact_id=" + userId + " ) ");
			}
		}

		String date = inputDto.getDate();
		Integer month = inputDto.getMonth();
		Integer year = inputDto.getYear();

		try {
			if (null != inputDto) {

				if (inputDto.getReportType().contains("day")) {
					if (date == null) {
						date = LocalDate.now().toString();
					}

					result.setReportTypeValue(date);
					submissionsCountQuery
							.append(" and DATE(maping.lastUpdatedDate) = DATE('" + date + "') GROUP BY mappedUser_id ");
					rejectionsCountQuery
							.append(" and candidateStatus LIKE '%rejected%' AND DATE(maping.lastUpdatedDate) = DATE('"
									+ date + "') GROUP BY mappedUser_id");
					q1 = this.getEntityManager().createNativeQuery(submissionsCountQuery.toString());
					q2 = this.getEntityManager().createNativeQuery(rejectionsCountQuery.toString());
					result.setReportType(inputDto.getReportType());
					result.setDay(inputDto.getDate());
				}

				else if (inputDto.getReportType().contains("month")) {

					month = inputDto.getMonth();
					year = inputDto.getYear();

					result.setReportTypeValue(Month.of(month) + " " + year);
					submissionsCountQuery.append(" and MONTH(maping.lastUpdatedDate)=" + month
							+ " AND YEAR(maping.lastUpdatedDate)=" + year + " GROUP BY mappedUser_id ");
					rejectionsCountQuery
							.append(" and candidateStatus LIKE '%rejected%' AND MONTH(maping.lastUpdatedDate)=" + month
									+ " AND YEAR(maping.lastUpdatedDate)=" + year + " GROUP BY mappedUser_id ");
					q1 = this.getEntityManager().createNativeQuery(submissionsCountQuery.toString());
					q2 = this.getEntityManager().createNativeQuery(rejectionsCountQuery.toString());
					result.setReportType(inputDto.getReportType());
					result.setMonth(inputDto.getMonth());
					result.setYear(inputDto.getYear());
				}

				else if (inputDto.getReportType().contains("year")) {

					result.setReportTypeValue(year + "");
					submissionsCountQuery.append(
							" and YEAR(maping.lastUpdatedDate)=" + inputDto.getYear() + " GROUP BY mappedUser_id ");
					rejectionsCountQuery
							.append(" and candidateStatus LIKE '%rejected%' AND YEAR(maping.lastUpdatedDate)=" + year
									+ " GROUP BY mappedUser_id ");
					q1 = this.getEntityManager().createNativeQuery(submissionsCountQuery.toString());
					q2 = this.getEntityManager().createNativeQuery(rejectionsCountQuery.toString());
					result.setReportType(inputDto.getReportType());
					result.setYear(inputDto.getYear());
				}
			}

			LinkedHashSet<Long> userSet = new LinkedHashSet<Long>();
			HashMap<Long, Integer> submissions = new HashMap<Long, Integer>();
			HashMap<Long, Integer> rejections = new HashMap<Long, Integer>();
			ArrayList<ReportsListTransfer> resultData = new ArrayList<ReportsListTransfer>();

			List<Object[]> sumissionsResults = q1.getResultList();
			for (Object[] data : sumissionsResults) {
				if (data[0] != null && !data[0].toString().isEmpty()) {
					userSet.add(Long.valueOf(data[0] + ""));
					submissions.put(Long.valueOf(data[0] + ""), Integer.valueOf(data[1] + ""));
				}
			}

			List<Object[]> rejectionsResults = q2.getResultList();
			for (Object[] data : rejectionsResults) {
				if (data[0] != null && !data[0].toString().isEmpty()) {
					userSet.add(Long.valueOf(data[0] + ""));
					rejections.put(Long.valueOf(data[0] + ""), Integer.valueOf(data[1] + ""));
				}
			}

			if (!userSet.isEmpty()) {
				for (Long id : userSet) {
					ReportsListTransfer reportData = new ReportsListTransfer();

					reportData.setUserId(id);
					User u = userDao.find(id);
					String uname = u.getFirstName() + " " + u.getLastName();
					reportData.setNameOfUser(uname);

					if (null == submissions.get(id)) {
						reportData.setSubmissions(0);
					} else {
						reportData.setSubmissions(submissions.get(id));
					}

					if (null == rejections.get(id)) {
						reportData.setRejections(0);
					} else {
						reportData.setRejections(rejections.get(id));
					}
					resultData.add(reportData);
				}
				result.setResultsList(resultData);
				String base64 = barChartTemplates.getRecruiterWiseSubmissionsVsRejectionsByReportType(result);
				result.setBase64String(base64);
				if (sumissionsResults != null && sumissionsResults.isEmpty()) {
					response = new Response(result, ExceptionMessage.OK);
					response.setStatus2(sumissionsResults.size() + "");
				} else {
					response = new Response(result, ExceptionMessage.OK);
				}

			} else {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Data Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "Unable Fetch Results");
		}
		return response;
	}

	public Response getRecruiterWiseSubmissionsVsClosuresByReportType(ReportInputDataDTO inputDto) {
		Response response = null;

		Query q1 = null;
		Query q2 = null;
		ReportResultsTransfer result = new ReportResultsTransfer();

		StringBuilder submissionsCountQuery = new StringBuilder(
				" SELECT mappedUser_id, COUNT(*) AS SUBMISSIONS FROM candidatemapping maping ");
		StringBuilder rejectionsCountQuery = new StringBuilder(
				" SELECT mappedUser_id, COUNT(*) AS REJECTIONS FROM candidatemapping maping");
		String role = inputDto.getRole();
		Long userId = inputDto.getRoleUserId();

		if (role.equalsIgnoreCase("Lead")) {

			submissionsCountQuery.append(" , bdmreq  req, addcontact addcontacts "
					+ " where req.addContact_id=addcontacts.id " + " and maping.bdmReq_id=req.id");

			rejectionsCountQuery.append(" , bdmreq  req, addcontact addcontacts "
					+ " where req.addContact_id=addcontacts.id " + " and maping.bdmReq_id=req.id");

			if (inputDto.getSelectedId() != null) {
				submissionsCountQuery.append("and (addcontacts.lead_id=" + userId + " " + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ")");
				rejectionsCountQuery.append("and (addcontacts.lead_id=" + userId + " " + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ")");
			} else {
				submissionsCountQuery.append("and (addcontacts.lead_id=" + userId + " )");
				rejectionsCountQuery.append("and (addcontacts.lead_id=" + userId + " )");
			}

		}

		else if (role.equalsIgnoreCase("AM")) {

			submissionsCountQuery.append(
					" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id ");
			rejectionsCountQuery.append(
					" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id ");

			if (inputDto.getSelectedId() != null) {
				submissionsCountQuery.append(" and (addcontacts.accountManger_id=" + userId + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ")");
				rejectionsCountQuery.append(" and (addcontacts.accountManger_id=" + userId + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ")");
			} else {
				submissionsCountQuery.append(" and (addcontacts.accountManger_id=" + userId + " )");
				rejectionsCountQuery.append(" and (addcontacts.accountManger_id=" + userId + " )");
			}

		}

		else if (role.equalsIgnoreCase("BDM")) {

			submissionsCountQuery.append(
					"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id ");

			rejectionsCountQuery.append(
					"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id ");
			if (inputDto.getSelectedId() != null) {
				submissionsCountQuery.append("and (addcontacts.primaryContact_id=" + userId
						+ " or maping.mappedUser_id=" + inputDto.getSelectedId() + ") ");
				rejectionsCountQuery.append("and (addcontacts.primaryContact_id=" + userId + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ") ");
			} else {
				submissionsCountQuery.append("and (addcontacts.primaryContact_id=" + userId + " ) ");
				rejectionsCountQuery.append("and (addcontacts.primaryContact_id=" + userId + " ) ");
			}
		}

		String date = inputDto.getDate();
		Integer month = inputDto.getMonth();
		Integer year = inputDto.getYear();

		try {
			if (null != inputDto) {

				if (inputDto.getReportType().contains("day")) {
					if (date == null) {
						date = LocalDate.now().toString();
					}

					result.setReportTypeValue(date);
					submissionsCountQuery
							.append(" and DATE(maping.lastUpdatedDate) = DATE('" + date + "') GROUP BY mappedUser_id ");
					rejectionsCountQuery
							.append(" and offereStatus like '%Offer Released%' AND DATE(maping.lastUpdatedDate) = DATE('"
									+ date + "') GROUP BY mappedUser_id");
					q1 = this.getEntityManager().createNativeQuery(submissionsCountQuery.toString());
					q2 = this.getEntityManager().createNativeQuery(rejectionsCountQuery.toString());
					result.setReportType(inputDto.getReportType());
					result.setDay(inputDto.getDate());
				}

				else if (inputDto.getReportType().contains("month")) {

					month = inputDto.getMonth();
					year = inputDto.getYear();

					result.setReportTypeValue(Month.of(month) + " " + year);
					submissionsCountQuery.append(" and MONTH(maping.lastUpdatedDate)=" + month
							+ " AND YEAR(maping.lastUpdatedDate)=" + year + " GROUP BY mappedUser_id ");
					rejectionsCountQuery
							.append(" and offereStatus like '%Offer Released%' AND MONTH(maping.lastUpdatedDate)="
									+ month + " AND YEAR(maping.lastUpdatedDate)=" + year + " GROUP BY mappedUser_id ");
					q1 = this.getEntityManager().createNativeQuery(submissionsCountQuery.toString());
					q2 = this.getEntityManager().createNativeQuery(rejectionsCountQuery.toString());
					result.setReportType(inputDto.getReportType());
					result.setMonth(inputDto.getMonth());
					result.setYear(inputDto.getYear());
				}

				else if (inputDto.getReportType().contains("year")) {

					result.setReportTypeValue(year + "");
					submissionsCountQuery.append(
							" and YEAR(maping.lastUpdatedDate)=" + inputDto.getYear() + " GROUP BY mappedUser_id ");
					rejectionsCountQuery
							.append(" and offereStatus like '%Offer Released%' AND YEAR(maping.lastUpdatedDate)=" + year
									+ " GROUP BY mappedUser_id ");
					q1 = this.getEntityManager().createNativeQuery(submissionsCountQuery.toString());
					q2 = this.getEntityManager().createNativeQuery(rejectionsCountQuery.toString());
					result.setReportType(inputDto.getReportType());
					result.setYear(inputDto.getYear());
				}
			}

			LinkedHashSet<Long> userSet = new LinkedHashSet<Long>();
			HashMap<Long, Integer> submissions = new HashMap<Long, Integer>();
			HashMap<Long, Integer> rejections = new HashMap<Long, Integer>();
			ArrayList<ReportsListTransfer> resultData = new ArrayList<ReportsListTransfer>();

			List<Object[]> sumissionsResults = q1.getResultList();
			for (Object[] data : sumissionsResults) {
				if (data[0] != null && !data[0].toString().isEmpty()) {
					userSet.add(Long.valueOf(data[0] + ""));
					submissions.put(Long.valueOf(data[0] + ""), Integer.valueOf(data[1] + ""));
				}
			}

			List<Object[]> rejectionsResults = q2.getResultList();
			for (Object[] data : rejectionsResults) {
				if (data[0] != null && !data[0].toString().isEmpty()) {
					userSet.add(Long.valueOf(data[0] + ""));
					rejections.put(Long.valueOf(data[0] + ""), Integer.valueOf(data[1] + ""));
				}
			}

			if (!userSet.isEmpty()) {
				for (Long id : userSet) {
					ReportsListTransfer reportData = new ReportsListTransfer();

					reportData.setUserId(id);
					User u = userDao.find(id);
					String uname = u.getFirstName() + " " + u.getLastName();
					reportData.setNameOfUser(uname);

					if (null == submissions.get(id)) {
						reportData.setSubmissions(0);
					} else {
						reportData.setSubmissions(submissions.get(id));
					}

					if (null == rejections.get(id)) {
						reportData.setRejections(0);
					} else {
						reportData.setRejections(rejections.get(id));
					}
					resultData.add(reportData);
				}
				result.setResultsList(resultData);
				String base64 = barChartTemplates.getRecruiterWiseSubmissionsVsClousersByReportType(result);
				int count = 0;
				if (sumissionsResults != null) {
					count = sumissionsResults.size();
				}
				result.setBase64String(base64);

				response = new Response(result, ExceptionMessage.OK);
				if (count > 4) {
					response.setStatus2(1200 + "");
				}
			} else {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Data Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "Unable Fetch Results");
		}
		return response;
	}

	public Response getRecruiterWiseClosersVsJoinersByReportType(ReportInputDataDTO inputDto) {
		Response response = null;

		Query q1 = null;
		Query q2 = null;
		ReportResultsTransfer result = new ReportResultsTransfer();

		StringBuilder submissionsCountQuery = new StringBuilder(
				" SELECT mappedUser_id, COUNT(*) AS SUBMISSIONS FROM candidatemapping maping ");
		StringBuilder rejectionsCountQuery = new StringBuilder(
				" SELECT mappedUser_id, COUNT(*) AS REJECTIONS FROM candidatemapping maping");
		String role = inputDto.getRole();
		Long userId = inputDto.getRoleUserId();

		if (role.equalsIgnoreCase("Lead")) {

			submissionsCountQuery.append(" , bdmreq  req, addcontact addcontacts "
					+ " where req.addContact_id=addcontacts.id " + " and maping.bdmReq_id=req.id");

			rejectionsCountQuery.append(" , bdmreq  req, addcontact addcontacts "
					+ " where req.addContact_id=addcontacts.id " + " and maping.bdmReq_id=req.id");

			if (inputDto.getSelectedId() != null) {
				submissionsCountQuery.append("and (addcontacts.lead_id=" + userId + " " + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ")");
				rejectionsCountQuery.append("and (addcontacts.lead_id=" + userId + " " + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ")");
			} else {
				submissionsCountQuery.append("and (addcontacts.lead_id=" + userId + " )");
				rejectionsCountQuery.append("and (addcontacts.lead_id=" + userId + " )");
			}

		}

		else if (role.equalsIgnoreCase("AM")) {

			submissionsCountQuery.append(
					" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id ");
			rejectionsCountQuery.append(
					" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id ");

			if (inputDto.getSelectedId() != null) {
				submissionsCountQuery.append("and (addcontacts.accountManger_id=" + userId + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ")");
				rejectionsCountQuery.append("and (addcontacts.accountManger_id=" + userId + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ")");
			} else {
				submissionsCountQuery.append("and (addcontacts.accountManger_id=" + userId + " )");
				rejectionsCountQuery.append("and (addcontacts.accountManger_id=" + userId + " )");
			}

		}

		else if (role.equalsIgnoreCase("BDM")) {

			submissionsCountQuery.append(
					"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id ");

			rejectionsCountQuery.append(
					"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id ");
			if (inputDto.getSelectedId() != null) {
				submissionsCountQuery.append("and (addcontacts.primaryContact_id=" + userId
						+ " or maping.mappedUser_id=" + inputDto.getSelectedId() + ") ");
				rejectionsCountQuery.append("and (addcontacts.primaryContact_id=" + userId + " or maping.mappedUser_id="
						+ inputDto.getSelectedId() + ") ");
			} else {
				submissionsCountQuery.append("and (addcontacts.primaryContact_id=" + userId + " ) ");
				rejectionsCountQuery.append("and (addcontacts.primaryContact_id=" + userId + " ) ");
			}
		}

		String date = inputDto.getDate();
		Integer month = inputDto.getMonth();
		Integer year = inputDto.getYear();

		try {
			if (null != inputDto) {

				if (inputDto.getReportType().contains("day")) {
					if (date == null) {
						date = LocalDate.now().toString();
					}

					result.setReportTypeValue(date);
					submissionsCountQuery
							.append(" and  offereStatus like '%Offer Released%' and DATE(maping.lastUpdatedDate) = DATE('"
									+ date + "') GROUP BY mappedUser_id ");
					rejectionsCountQuery
							.append(" and onBoardedStatus like '%on Boarded%' AND DATE(maping.lastUpdatedDate) = DATE('"
									+ date + "') GROUP BY mappedUser_id");
					q1 = this.getEntityManager().createNativeQuery(submissionsCountQuery.toString());
					q2 = this.getEntityManager().createNativeQuery(rejectionsCountQuery.toString());
					result.setReportType(inputDto.getReportType());
					result.setDay(inputDto.getDate());
				}

				else if (inputDto.getReportType().contains("month")) {

					month = inputDto.getMonth();
					year = inputDto.getYear();

					result.setReportTypeValue(Month.of(month) + " " + year);
					submissionsCountQuery
							.append(" and offereStatus like '%Offer Released%' and MONTH(maping.lastUpdatedDate)="
									+ month + " AND YEAR(maping.lastUpdatedDate)=" + year + " GROUP BY mappedUser_id ");
					rejectionsCountQuery
							.append(" and onBoardedStatus like '%on Boarded%' AND MONTH(maping.lastUpdatedDate)="
									+ month + " AND YEAR(maping.lastUpdatedDate)=" + year + " GROUP BY mappedUser_id ");
					q1 = this.getEntityManager().createNativeQuery(submissionsCountQuery.toString());
					q2 = this.getEntityManager().createNativeQuery(rejectionsCountQuery.toString());
					result.setReportType(inputDto.getReportType());
					result.setMonth(inputDto.getMonth());
					result.setYear(inputDto.getYear());
				}

				else if (inputDto.getReportType().contains("year")) {

					result.setReportTypeValue(year + "");
					submissionsCountQuery
							.append(" and offereStatus like '%Offer Released%' and YEAR(maping.lastUpdatedDate)="
									+ inputDto.getYear() + " GROUP BY mappedUser_id ");
					rejectionsCountQuery
							.append(" and onBoardedStatus like '%on Boarded%' AND YEAR(maping.lastUpdatedDate)=" + year
									+ " GROUP BY mappedUser_id ");
					q1 = this.getEntityManager().createNativeQuery(submissionsCountQuery.toString());
					q2 = this.getEntityManager().createNativeQuery(rejectionsCountQuery.toString());
					result.setReportType(inputDto.getReportType());
					result.setYear(inputDto.getYear());
				}
			}

			LinkedHashSet<Long> userSet = new LinkedHashSet<Long>();
			HashMap<Long, Integer> submissions = new HashMap<Long, Integer>();
			HashMap<Long, Integer> rejections = new HashMap<Long, Integer>();
			ArrayList<ReportsListTransfer> resultData = new ArrayList<ReportsListTransfer>();

			List<Object[]> sumissionsResults = q1.getResultList();
			for (Object[] data : sumissionsResults) {
				if (data[0] != null && !data[0].toString().isEmpty()) {
					userSet.add(Long.valueOf(data[0] + ""));
					submissions.put(Long.valueOf(data[0] + ""), Integer.valueOf(data[1] + ""));
				}
			}

			List<Object[]> rejectionsResults = q2.getResultList();
			for (Object[] data : rejectionsResults) {
				if (data[0] != null && !data[0].toString().isEmpty()) {
					userSet.add(Long.valueOf(data[0] + ""));
					rejections.put(Long.valueOf(data[0] + ""), Integer.valueOf(data[1] + ""));
				}
			}

			if (!userSet.isEmpty()) {
				for (Long id : userSet) {
					ReportsListTransfer reportData = new ReportsListTransfer();

					reportData.setUserId(id);
					User u = userDao.find(id);
					String uname = u.getFirstName() + " " + u.getLastName();
					reportData.setNameOfUser(uname);

					if (null == submissions.get(id)) {
						reportData.setSubmissions(0);
					} else {
						reportData.setSubmissions(submissions.get(id));
					}

					if (null == rejections.get(id)) {
						reportData.setRejections(0);
					} else {
						reportData.setRejections(rejections.get(id));
					}
					resultData.add(reportData);
				}
				result.setResultsList(resultData);
				String base64 = barChartTemplates.getRecruiterWiseClosuerVsJoinereByReportType(result);
				result.setBase64String(base64);
				int count = 0;
				if (sumissionsResults != null) {
					count = sumissionsResults.size();
				}
				response = new Response(result, ExceptionMessage.OK);
				if(count > 4) {
					response.setStatus2(1200 + "");
				}
			} else {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Data Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "Unable Fetch Results");
		}
		return response;
	}

	@Override
	public Response getLeadWiseSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto) {

		Response response = null;
		ReportResultsTransfer result = new ReportResultsTransfer();
		StringBuilder leadQuery = null;
		result.setUserRole("Lead");
		if (inputDto == null) {
			response = new Response(ExceptionMessage.Bad_Request, "Please Provide Input Details.");
			return response;
		}

		String date = inputDto.getDate();
		Integer month = inputDto.getMonth();
		Integer year = inputDto.getYear();

		if (date == null) {
			date = LocalDate.now().toString();
		}

		if (month == null && year == null) {
			LocalDate ld = LocalDate.now();
			month = ld.getMonthValue();
			year = ld.getYear();
		}

		if (year == null) {
			LocalDate ld = LocalDate.now();
			year = ld.getYear();
		}

		if (null != inputDto.getRoleUserId()) {
			leadQuery = new StringBuilder(
					"SELECT c.lead_id AS leadId, CONCAT(u.firstName,' ',u.lastName) AS leadName, COUNT(*) as submissions "
							+ " FROM `candidatemapping` cm INNER JOIN `bdmReq` b ON cm.bdmReq_id=b.id "
							+ " INNER JOIN `client` c ON b.client_id = c.id "
							+ " RIGHT JOIN `user` u ON c.lead_id = u.id " + " WHERE b.client_id = c.id AND c.lead_id = "
							+ inputDto.getRoleUserId() + " " + " UNION "
							+ " SELECT c.lead_id as leadId, concat(u.firstName,' ',u.lastName) as leadName, count(*) as rejections "
							+ " FROM candidatemapping cm INNER JOIN `bdmReq` b ON cm.bdmReq_id=b.id "
							+ " INNER JOIN `client` c ON b.client_id = c.id "
							+ " RIGHT JOIN `user` u ON c.lead_id = u.id "
							+ " WHERE b.client_id = c.id AND cm.candidateStatus LIKE '%rejected%' AND c.lead_id = "
							+ inputDto.getRoleUserId());

			result.setUserId(inputDto.getRoleUserId());

			if (inputDto.getReportType().equalsIgnoreCase("day")) {
				result.setReportType("day");
				result.setReportTypeValue(date);
				result.setDay(date);
				leadQuery.append(" AND DATE(cm.lastUpdatedDate) = DATE('" + date + "')");
			}

			if (inputDto.getReportType().equalsIgnoreCase("month")) {
				result.setReportType("month");
				result.setReportTypeValue(Month.of(month) + " " + year);
				result.setMonth(month);
				result.setYear(year);
				leadQuery.append(" AND MONTH(cm.lastUpdatedDate)=" + month + " AND YEAR(cm.lastUpdatedDate)=" + year);
			}

			if (inputDto.getReportType().equalsIgnoreCase("year")) {
				result.setReportType("year");
				result.setReportTypeValue(year + "");
				result.setYear(year);
				leadQuery.append(" AND YEAR(cm.lastUpdatedDate)=" + year);
			}
		} else {
			leadQuery = new StringBuilder(
					"SELECT c.lead_id AS leadId, CONCAT(u.firstName,' ',u.lastName) AS leadName, COUNT(*) as submissions "
							+ " FROM `candidatemapping` cm INNER JOIN `bdmReq` b ON cm.bdmReq_id=b.id "
							+ " INNER JOIN `client` c ON b.client_id = c.id "
							+ " RIGHT JOIN `user` u ON c.lead_id = u.id "
							+ " WHERE b.client_id = c.id GROUP BY c.lead_id " + " UNION "
							+ " SELECT c.lead_id as leadId, concat(u.firstName,' ',u.lastName) as leadName, count(*) as rejections "
							+ " FROM candidatemapping cm INNER JOIN `bdmReq` b ON cm.bdmReq_id=b.id "
							+ " INNER JOIN `client` c ON b.client_id = c.id "
							+ " RIGHT JOIN `user` u ON c.lead_id = u.id "
							+ " WHERE b.client_id = c.id AND cm.candidateStatus LIKE '%rejected%' ");

			if (inputDto.getReportType().equalsIgnoreCase("day")) {
				result.setReportType("day");
				result.setReportTypeValue(date);
				result.setDay(date);
				leadQuery.append(" AND DATE(cm.lastUpdatedDate) = DATE('" + date + "') GROUP BY c.lead_id");
			}

			if (inputDto.getReportType().equalsIgnoreCase("month")) {
				result.setReportType("month");
				result.setReportTypeValue(Month.of(month) + " " + year);
				result.setMonth(month);
				result.setYear(year);
				leadQuery.append(" AND MONTH(cm.lastUpdatedDate)=" + month + " AND YEAR(cm.lastUpdatedDate)=" + year
						+ " GROUP BY c.lead_id ");
			}

			if (inputDto.getReportType().equalsIgnoreCase("year")) {
				result.setReportType("year");
				result.setReportTypeValue(year + "");
				result.setYear(year);
				leadQuery.append(" AND YEAR(cm.lastUpdatedDate)=" + year + " GROUP BY c.lead_id ");
			}
		}

		try {
			HashMap<Long, ReportsListTransfer> map = new HashMap<Long, ReportsListTransfer>();
			Query q = this.getEntityManager().createNativeQuery(leadQuery.toString());
			List<Object[]> results = q.getResultList();

			int i = 0;
			if (!results.isEmpty()) {
				for (Object[] data : results) {

					if (data[0] != null) {
						Long key = Long.valueOf(data[0] + "");
						if (map.containsKey(key)) {
							if (data[2] == null) {
								map.get(key).setRejections(0);
							} else {
								map.get(key).setRejections(Integer.valueOf(data[2] + ""));
							}
						} else {
							ReportsListTransfer report = new ReportsListTransfer();
							report.setUserId(Long.valueOf(data[0] + ""));
							report.setNameOfUser(data[1] + "");
							if (data[2] == null) {
								report.setSubmissions(0);
							} else {
								report.setSubmissions(Integer.valueOf(data[2] + ""));
							}

							map.put(Long.valueOf(data[0] + ""), report);
						}
					}
				}
				Collection<ReportsListTransfer> resultData = map.values();
				result.setResultsList(resultData);
			} else {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Data Found");
			}

			String base64 = barChartTemplates.getLeadOrAMWiseSubmissionsVsRejectionsByReportType(result);
			result.setBase64String(base64);
			response = new Response(result, ExceptionMessage.OK);

		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "Unable Fetch Results");
		}
		return response;
	}

	@Override
	public Response getAMWiseSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto) {
		Response response = null;
		ReportResultsTransfer result = new ReportResultsTransfer();
		StringBuilder leadQuery = null;

		if (inputDto == null) {
			response = new Response(ExceptionMessage.Bad_Request, "Please Provide Input Details.");
			return response;
		}

		result.setUserRole("AM");
		String date = inputDto.getDate();
		Integer month = inputDto.getMonth();
		Integer year = inputDto.getYear();

		if (date == null) {
			date = LocalDate.now().toString();
		}

		if (month == null && year == null) {
			LocalDate ld = LocalDate.now();
			month = ld.getMonthValue();
			year = ld.getYear();
		}

		if (year == null) {
			LocalDate ld = LocalDate.now();
			year = ld.getYear();
		}

		if (null != inputDto.getRoleUserId()) {
			leadQuery = new StringBuilder(
					"SELECT c.accountManger_id AS amId, CONCAT(u.firstName,' ',u.lastName) AS amName, COUNT(*) as submissions "
							+ " FROM `candidatemapping` cm INNER JOIN `bdmReq` b ON cm.bdmReq_id=b.id "
							+ " INNER JOIN `client` c ON b.client_id = c.id "
							+ " RIGHT JOIN `user` u ON c.accountManger_id = u.id "
							+ " WHERE b.client_id = c.id AND c.accountManger_id = " + inputDto.getRoleUserId() + " "
							+ " UNION "
							+ " SELECT c.accountManger_id as amId, concat(u.firstName,' ',u.lastName) as amName, count(*) as rejections "
							+ " FROM candidatemapping cm INNER JOIN `bdmReq` b ON cm.bdmReq_id=b.id "
							+ " INNER JOIN `client` c ON b.client_id = c.id "
							+ " RIGHT JOIN `user` u ON c.accountManger_id = u.id "
							+ " WHERE b.client_id = c.id AND cm.candidateStatus LIKE '%rejected%' AND c.accountManger_id = "
							+ inputDto.getRoleUserId());

			result.setUserId(inputDto.getRoleUserId());

			if (inputDto.getReportType().equalsIgnoreCase("day")) {
				result.setReportType("day");
				result.setReportTypeValue(date);
				result.setDay(date);
				leadQuery.append(" AND DATE(cm.lastUpdatedDate) = DATE('" + date + "')");
			}

			if (inputDto.getReportType().equalsIgnoreCase("month")) {
				result.setReportType("month");
				result.setReportTypeValue(Month.of(month) + " " + year);
				result.setMonth(month);
				result.setYear(year);
				leadQuery.append(" AND MONTH(cm.lastUpdatedDate)=" + month + " AND YEAR(cm.lastUpdatedDate)=" + year);
			}

			if (inputDto.getReportType().equalsIgnoreCase("year")) {
				result.setReportType("year");
				result.setReportTypeValue(year + "");
				result.setYear(year);
				leadQuery.append(" AND YEAR(cm.lastUpdatedDate)=" + year);
			}
		} else {
			leadQuery = new StringBuilder(
					"SELECT c.accountManger_id AS amId, CONCAT(u.firstName,' ',u.lastName) AS amName, COUNT(*) as submissions "
							+ " FROM `candidatemapping` cm INNER JOIN `bdmReq` b ON cm.bdmReq_id=b.id "
							+ " INNER JOIN `client` c ON b.client_id = c.id "
							+ " RIGHT JOIN `user` u ON c.accountManger_id = u.id "
							+ " WHERE b.client_id = c.id GROUP BY c.accountManger_id " + " UNION "
							+ " SELECT c.accountManger_id as amId, concat(u.firstName,' ',u.lastName) as amName, count(*) as rejections "
							+ " FROM candidatemapping cm INNER JOIN `bdmReq` b ON cm.bdmReq_id=b.id "
							+ " INNER JOIN `client` c ON b.client_id = c.id "
							+ " RIGHT JOIN `user` u ON c.accountManger_id = u.id "
							+ " WHERE b.client_id = c.id AND cm.candidateStatus LIKE '%rejected%' ");

			if (inputDto.getReportType().equalsIgnoreCase("day")) {
				result.setReportType("day");
				result.setReportTypeValue(date);
				result.setDay(date);
				leadQuery.append(" AND DATE(cm.lastUpdatedDate) = DATE('" + date + "') GROUP BY c.accountManger_id");
			}

			if (inputDto.getReportType().equalsIgnoreCase("month")) {
				result.setReportType("month");
				result.setReportTypeValue(Month.of(month) + " " + year);
				result.setMonth(month);
				result.setYear(year);
				leadQuery.append(" AND MONTH(cm.lastUpdatedDate)=" + month + " AND YEAR(cm.lastUpdatedDate)=" + year
						+ " GROUP BY c.accountManger_id ");
			}

			if (inputDto.getReportType().equalsIgnoreCase("year")) {
				result.setReportType("year");
				result.setReportTypeValue(year + "");
				result.setYear(year);
				leadQuery.append(" AND YEAR(cm.lastUpdatedDate)=" + year + " GROUP BY c.accountManger_id ");
			}
		}

		try {
			HashMap<Long, ReportsListTransfer> map = new HashMap<Long, ReportsListTransfer>();
			Query q = this.getEntityManager().createNativeQuery(leadQuery.toString());
			List<Object[]> results = q.getResultList();

			int i = 0;
			if (!results.isEmpty()) {
				for (Object[] data : results) {

					if (data[0] != null) {
						Long key = Long.valueOf(data[0] + "");
						if (map.containsKey(key)) {
							if (data[2] == null) {
								map.get(key).setRejections(0);
							} else {
								map.get(key).setRejections(Integer.valueOf(data[2] + ""));
							}
						} else {
							ReportsListTransfer report = new ReportsListTransfer();
							report.setUserId(Long.valueOf(data[0] + ""));
							report.setNameOfUser(data[1] + "");
							if (data[2] == null) {
								report.setSubmissions(0);
							} else {
								report.setSubmissions(Integer.valueOf(data[2] + ""));
							}

							map.put(Long.valueOf(data[0] + ""), report);
						}
					}
				}
				Collection<ReportsListTransfer> resultData = map.values();

				result.setResultsList(resultData);
			} else {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Data Found");
			}

			String base64 = barChartTemplates.getLeadOrAMWiseSubmissionsVsRejectionsByReportType(result);
			result.setBase64String(base64);
			response = new Response(result, ExceptionMessage.OK);

		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "Unable Fetch Results");
		}
		return response;
	}

	@Override
	public String getClosuresVsJoining() {
		return null;
	}

	@Override
	public Response getSubmissionsVsClosures(Long loginId, String role) {

		Response response = null;
		ReportResultsTransfer result = new ReportResultsTransfer();
		String sql = "SELECT  "
				+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN candidateStatus END) AS rejectedCount, "
				+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
				+ "FROM candidatemapping maping ";
		if (role.equalsIgnoreCase("recruiter")) {

			sql = sql + " where maping.mappedUser_id = " + loginId;

		}
		if (role.equalsIgnoreCase("Lead")) {

			sql = sql
					+ "  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.lead_id="
					+ loginId + " or maping.mappedUser_id=" + loginId + ")";
		}

		if (role.equalsIgnoreCase("AM")) {

			sql = sql
					+ " , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.accountManger_id="
					+ loginId + " or maping.mappedUser_id=" + loginId + ")";
		}

		if (role.equalsIgnoreCase("BDM")) {

			sql = sql
					+ " , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.primaryContact_id="
					+ loginId + " or maping.mappedUser_id=" + loginId + ")";
		}

		List<Object[]> resultList;
		try {
			Query createNativeQuery = this.getEntityManager().createNativeQuery(sql);
			resultList = createNativeQuery.getResultList();
			result = new ReportResultsTransfer();
			if (!resultList.isEmpty()) {
				for (Object[] data : resultList) {
					result.setTotalRejections(Integer.valueOf(data[0] + ""));
					result.setTotalSubmissions(Integer.valueOf(data[1] + ""));
				}

				String encodedString = barChartTemplates.getSubmittedAndClosures(result.getTotalSubmissions(),
						result.getTotalRejections());
				response = new Response(ExceptionMessage.OK, result, encodedString);

			} else {
				new Response(ExceptionMessage.DataIsEmpty, "No Results Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Response(ExceptionMessage.Exception, "Unable to Fetch Results");
		}
		return response;
	}

	@Override
	public Response getRequirementWiseSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto) {

		Response response = null;
		ReportResultsTransfer result = new ReportResultsTransfer();
		String reqVsSubVSRejQuery = null;
		StringBuilder q1, q2;
		q1 = q2 = null;

		if (inputDto == null) {
			response = new Response(ExceptionMessage.Bad_Request, "Please Provide Input Details.");
			return response;
		}

		result.setUserRole("BDM");
		String date = inputDto.getDate();
		Integer month = inputDto.getMonth();
		Integer year = inputDto.getYear();

		if (date == null) {
			date = LocalDate.now().toString();
		}

		if (month == null && year == null) {
			LocalDate ld = LocalDate.now();
			month = ld.getMonthValue();
			year = ld.getYear();
		}

		if (year == null) {
			LocalDate ld = LocalDate.now();
			year = ld.getYear();
		}

		q1 = new StringBuilder(
				" SELECT c.clientName ,cm.bdmReq_id, req.nameOfRequirement, COUNT(*) FROM `candidatemapping` cm "
						+ " INNER JOIN `bdmreq` req ON cm.bdmReq_id=req.id "
						+ " INNER JOIN `client` c ON req.client_id=c.id  ");

		q2 = new StringBuilder(
				" SELECT c.clientName ,cm.bdmReq_id, req.nameOfRequirement, COUNT(*) FROM `candidatemapping` cm "
						+ " INNER JOIN `bdmreq` req ON cm.bdmReq_id=req.id "
						+ " INNER JOIN `client` c ON req.client_id=c.id "
						+ " WHERE cm.candidateStatus LIKE '%reject%' ");

		if (inputDto.getReportType().equalsIgnoreCase("day")) {
			result.setReportType("day");
			result.setReportTypeValue(date);
			result.setDay(date);
			q1.append(" AND DATE(cm.lastUpdatedDate) = DATE('" + date + "') GROUP BY cm.bdmReq_id");
			q2.append(" AND DATE(cm.lastUpdatedDate) = DATE('" + date + "') GROUP BY cm.bdmReq_id");
		}

		if (inputDto.getReportType().equalsIgnoreCase("month")) {
			result.setReportType("month");
			result.setReportTypeValue(Month.of(month) + " " + year);
			result.setMonth(month);
			result.setYear(year);
			q1.append(" AND MONTH(cm.lastUpdatedDate)=" + month + " AND YEAR(cm.lastUpdatedDate)=" + year
					+ " GROUP BY cm.bdmReq_id ");
			q2.append(" AND MONTH(cm.lastUpdatedDate)=" + month + " AND YEAR(cm.lastUpdatedDate)=" + year
					+ " GROUP BY cm.bdmReq_id ");
		}

		if (inputDto.getReportType().equalsIgnoreCase("year")) {
			result.setReportType("year");
			result.setReportTypeValue(year + "");
			result.setYear(year);
			q1.append(" AND YEAR(cm.lastUpdatedDate)=" + year + " GROUP BY cm.bdmReq_id ");
			q2.append(" AND YEAR(cm.lastUpdatedDate)=" + year + " GROUP BY cm.bdmReq_id ");
		}

		reqVsSubVSRejQuery = (q1 + " UNION " + q2).toString();
		try {
			HashMap<Long, ReportsListTransfer> map = new HashMap<Long, ReportsListTransfer>();
			Query q = this.getEntityManager().createNativeQuery(reqVsSubVSRejQuery.toString());
			List<Object[]> results = q.getResultList();

			if (!results.isEmpty()) {
				for (Object[] data : results) {

					if (data[1] != null) {
						Long key = Long.valueOf(data[1] + "");
						if (map.containsKey(key)) {
							if (data[3] == null) {
								map.get(key).setRejections(0);
							} else {
								map.get(key).setRejections(Integer.valueOf(data[3] + ""));
							}
						} else {
							ReportsListTransfer report = new ReportsListTransfer();
							report.setClientName(data[0] + "");
							report.setRequirementId(Long.valueOf(data[1] + ""));
							report.setRequirementName(data[2] + "");

							if (data[3] == null) {
								report.setSubmissions(0);
							} else {
								report.setSubmissions(Integer.valueOf(data[3] + ""));
							}

							map.put(Long.valueOf(data[1] + ""), report);
						}
					}
				}
				Collection<ReportsListTransfer> resultData = map.values();

				result.setResultsList(resultData);
			} else {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Data Found");
			}

			String base64 = barChartTemplates.getRequirementVsSubmissionsByReportType(result);
			result.setBase64String(base64);
			response = new Response(result, ExceptionMessage.OK);

		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "Unable Fetch Results");
		}
		return response;
	}

	@Override
	public Response getClosuresVsJoinings(Long loginId, String role) {
		Response response = null;
		ReportResultsTransfer result = new ReportResultsTransfer();

		String sql = "SELECT  "
				+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN offereStatus END) AS rejectedCount, "
				+ "COUNT(CASE WHEN onBoardedStatus like '%on Boarded%' THEN onBoardedStatus END) AS submissioncount "
				+ "FROM candidatemapping maping ";
		if (role.equalsIgnoreCase("recruiter")) {

			sql = sql + " where maping.mappedUser_id = " + loginId;

		}
		if (role.equalsIgnoreCase("Lead")) {

			sql = sql
					+ "  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id  and maping.bdmReq_id=req.id and (addcontacts.lead_id="
					+ loginId + " or maping.mappedUser_id=" + loginId + ")";
		}

		if (role.equalsIgnoreCase("AM")) {

			sql = sql
					+ " , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.accountManger_id="
					+ loginId + " or maping.mappedUser_id=" + loginId + ")";
		}

		if (role.equalsIgnoreCase("BDM")) {

			sql = sql
					+ " , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.primaryContact_id="
					+ loginId + " or maping.mappedUser_id=" + loginId + ")";
		}

		List<Object[]> resultList;
		try {
			Query createNativeQuery = this.getEntityManager().createNativeQuery(sql);
			resultList = createNativeQuery.getResultList();
			result = new ReportResultsTransfer();
			if (!resultList.isEmpty()) {
				for (Object[] data : resultList) {
					result.setTotalRejections(Integer.valueOf(data[0] + ""));
					result.setTotalSubmissions(Integer.valueOf(data[1] + ""));
				}

				String encodedString = barChartTemplates.getClosuresAndJoining(result.getTotalSubmissions(),
						result.getTotalRejections());
				response = new Response(ExceptionMessage.OK, result, encodedString);

			} else {
				new Response(ExceptionMessage.DataIsEmpty, "No Results Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Response(ExceptionMessage.Exception, "Unable to Fetch Results");
		}
		return response;
	}

	@Override
	public Response getSubmissionsVsClosuresByReportType(ReportInputDataDTO inputDto) {
		Response response = null;
		StringBuilder countsql = null;
		ReportResultsTransfer result = new ReportResultsTransfer();
		String role = inputDto.getRole();
		try {
			Long userId = inputDto.getRoleUserId();
			if (null != inputDto) {

				if (inputDto.getReportType().contains("day")) {
					String date = inputDto.getDate();
					if (date == null) {
						date = " CURDATE() ";
					}

					result.setReportTypeValue(date);
					if (role.equalsIgnoreCase("recruiter")) {

						countsql = new StringBuilder("SELECT "
								+ "(SELECT count(*) FROM candidatemapping maping WHERE DATE(maping.lastUpdatedDate) = DATE('"
								+ date + "') " + " AND offereStatus LIKE '%Offer Released%' AND mappedUser_id = "
								+ userId
								+ ") AS rejections ,(SELECT count(*) FROM candidatemapping maping WHERE DATE(maping.lastUpdatedDate) = DATE('"
								+ date + "') AND mappedUser_id = " + userId + ") AS submissions");

						result.setUserId(userId);
					}

					else if (role.equalsIgnoreCase("Lead")) {

						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN offereStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								" , bdmreq  req, addcontact addcontacts " + " where req.addContact_id=addcontacts.id "
										+ " and maping.bdmReq_id=req.id and (addcontacts.lead_id=" + userId + " "
										+ " or maping.mappedUser_id=" + userId
										+ ")  and DATE(maping.lastUpdatedDate) = DATE('" + date + "') ");
					}

					else if (role.equalsIgnoreCase("AM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN offereStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");
						countsql.append(
								" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.accountManger_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and DATE(maping.lastUpdatedDate) = DATE('" + date + "')");
					}

					else if (role.equalsIgnoreCase("BDM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN offereStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.primaryContact_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and DATE(maping.lastUpdatedDate) = DATE('" + date + "')");
					}

				}

				else if (inputDto.getReportType().contains("month")) {
					Integer month = inputDto.getMonth();
					Integer year = inputDto.getYear();
					/*
					 * if (month == null && year == null) { LocalDate ld =
					 * LocalDate.now(); month = ld.getMonthValue(); year =
					 * ld.getYear(); }
					 */

					result.setReportTypeValue(Month.of(month) + " " + year);
					if (inputDto.getRole().equalsIgnoreCase("recruiter")) {
						countsql = new StringBuilder("SELECT "
								+ "(SELECT count(*) FROM candidatemapping maping WHERE MONTH(maping.lastUpdatedDate)='"
								+ month + "' AND YEAR(lastUpdatedDate)=" + year
								+ " AND offereStatus LIKE '%Offer Released%' AND mappedUser_id = " + userId
								+ ") AS rejections, (SELECT count(*) FROM candidatemapping maping WHERE MONTH(maping.lastUpdatedDate)='"
								+ month + "' AND YEAR(maping.lastUpdatedDate)=" + year + " AND mappedUser_id = "
								+ userId + ") AS submissions  ");
						result.setUserId(userId);
					}

					else if (role.equalsIgnoreCase("Lead")) {

						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN offereStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								" , bdmreq  req, addcontact addcontacts " + " where req.addContact_id=addcontacts.id "
										+ " and maping.bdmReq_id=req.id and (addcontacts.lead_id=" + userId + " "
										+ " or maping.mappedUser_id=" + userId
										+ ")  and MONTH(maping.lastUpdatedDate) = '" + month + "'");
					}

					else if (role.equalsIgnoreCase("AM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN offereStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");
						countsql.append(
								" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.accountManger_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and MONTH(maping.lastUpdatedDate) = '" + month + "'");
					}

					else if (role.equalsIgnoreCase("BDM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN offereStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.primaryContact_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and MONTH(maping.lastUpdatedDate) = '" + month + "'");
					}

				} else if (inputDto.getReportType().contains("year")) {
					Integer year = inputDto.getYear();
					if (year == null) {
						LocalDate ld = LocalDate.now();
						year = ld.getYear();
					}

					result.setReportTypeValue(year.toString());
					if (inputDto.getRole().equalsIgnoreCase("recruiter")) {
						countsql = new StringBuilder(
								"SELECT (SELECT count(*) FROM candidatemapping maping WHERE YEAR(maping.lastUpdatedDate)="
										+ year + " AND offereStatus LIKE '%Offer Released%' AND mappedUser_id = "
										+ userId + ") AS rejections ,"
										+ " (SELECT count(*) FROM candidatemapping maping WHERE YEAR(maping.lastUpdatedDate)="
										+ year + " AND mappedUser_id = " + userId + ") AS submissions ");
						result.setUserId(userId);
					}

					else if (role.equalsIgnoreCase("Lead")) {

						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN offereStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								" , bdmreq  req, addcontact addcontacts " + " where req.addContact_id=addcontacts.id "
										+ " and maping.bdmReq_id=req.id and (addcontacts.lead_id=" + userId + " "
										+ " or maping.mappedUser_id=" + userId
										+ ")  and year(maping.lastUpdatedDate) = '" + year + "'");
					}

					else if (role.equalsIgnoreCase("AM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN offereStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");
						countsql.append(
								" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.accountManger_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and year(maping.lastUpdatedDate) = '" + year + "'");
					}

					else if (role.equalsIgnoreCase("BDM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN offereStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN candidateStatus = 0 THEN candidateStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.primaryContact_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and year(maping.lastUpdatedDate) = '" + year + "'");
					}

				}

				if (null != countsql) {
					Query createNativeQuery = this.getEntityManager().createNativeQuery(countsql.toString());
					List<Object[]> resultList = createNativeQuery.getResultList();
					for (Object[] data : resultList) {
						result.setTotalRejections(Integer.valueOf(data[0] + ""));
						result.setTotalSubmissions(Integer.valueOf(data[1] + ""));
					}
					result.setReportType(inputDto.getReportType());
					String base64 = barChartTemplates.getSubmissionsVsClosuresByReportType(result);
					result.setBase64String(base64);
					response = new Response(result, ExceptionMessage.OK);

				} else {
					response = new Response(ExceptionMessage.Bad_Request, "Invalid Request");
				}
			}

		} catch (Exception e) {
			response = new Response(ExceptionMessage.Exception, "Unable to Fetch Results");
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public Response getClosuresVsJoiningByReportType(ReportInputDataDTO inputDto) {
		Response response = null;
		StringBuilder countsql = null;
		ReportResultsTransfer result = new ReportResultsTransfer();
		String role = inputDto.getRole();

		try {
			Long userId = inputDto.getRoleUserId();
			if (null != inputDto) {

				if (inputDto.getReportType().contains("day")) {
					String date = inputDto.getDate();
					if (date == null) {
						date = " CURDATE() ";
					}

					result.setReportTypeValue(date);
					if (role.equalsIgnoreCase("recruiter")) {

						countsql = new StringBuilder("SELECT "
								+ "(SELECT count(*) FROM candidatemapping maping WHERE DATE(maping.lastUpdatedDate) = DATE('"
								+ date + "') " + " AND offereStatus LIKE '%Offer Released%' AND mappedUser_id = "
								+ userId
								+ ") AS rejections ,(SELECT count(*) FROM  candidatemapping maping  WHERE onBoardedStatus LIKE '%On Boarded%' and DATE(maping.lastUpdatedDate) = DATE('"
								+ date + "') AND mappedUser_id = " + userId + ") AS submissions");

						result.setUserId(userId);
					}

					else if (role.equalsIgnoreCase("Lead")) {

						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN offereStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN onBoardedStatus LIKE '%On Boarded%' THEN onBoardedStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								" , bdmreq  req, addcontact addcontacts " + " where req.addContact_id=addcontacts.id "
										+ " and maping.bdmReq_id=req.id and (addcontacts.lead_id=" + userId + " "
										+ " or maping.mappedUser_id=" + userId
										+ ")  and DATE(maping.lastUpdatedDate) = DATE('" + date + "') ");
					}

					else if (role.equalsIgnoreCase("AM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN offereStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN onBoardedStatus LIKE '%On Boarded%' THEN onBoardedStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");
						countsql.append(
								" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.accountManger_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and DATE(maping.lastUpdatedDate) = DATE('" + date + "')");
					}

					else if (role.equalsIgnoreCase("BDM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN offereStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN onBoardedStatus LIKE '%On Boarded%' THEN onBoardedStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.primaryContact_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and DATE(maping.lastUpdatedDate) = DATE('" + date + "')");
					}

				}

				else if (inputDto.getReportType().contains("month")) {
					Integer month = inputDto.getMonth();
					Integer year = inputDto.getYear();
					/*
					 * if (month == null && year == null) { LocalDate ld =
					 * LocalDate.now(); month = ld.getMonthValue(); year =
					 * ld.getYear(); }
					 */

					result.setReportTypeValue(Month.of(month) + " " + year);
					if (inputDto.getRole().equalsIgnoreCase("recruiter")) {
						countsql = new StringBuilder("SELECT "
								+ "(SELECT count(*) FROM candidatemapping maping WHERE MONTH(maping.lastUpdatedDate)='"
								+ month + "' AND YEAR(lastUpdatedDate)=" + year
								+ " AND offereStatus LIKE '%Offer Released%' AND mappedUser_id = " + userId
								+ ") AS rejections, (SELECT count(*) FROM candidatemapping maping WHERE MONTH(maping.lastUpdatedDate)='"
								+ month + "' AND YEAR(maping.lastUpdatedDate)=" + year + " AND mappedUser_id = "
								+ userId +  " AND onBoardedStatus LIKE '%On Boarded%') AS submissions    ");
						result.setUserId(userId);
					}

					else if (role.equalsIgnoreCase("Lead")) {

						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN onBoardedStatus LIKE '%On Boarded%' THEN onBoardedStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								" , bdmreq  req, addcontact addcontacts " + " where req.addContact_id=addcontacts.id "
										+ " and maping.bdmReq_id=req.id and (addcontacts.lead_id=" + userId + " "
										+ " or maping.mappedUser_id=" + userId
										+ ")  and MONTH(maping.lastUpdatedDate) = '" + month + "'");
					}

					else if (role.equalsIgnoreCase("AM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN onBoardedStatus LIKE '%On Boarded%' THEN onBoardedStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");
						countsql.append(
								" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.accountManger_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and MONTH(maping.lastUpdatedDate) = '" + month + "'");
					}

					else if (role.equalsIgnoreCase("BDM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN onBoardedStatus LIKE '%On Boarded%' THEN onBoardedStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.primaryContact_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and MONTH(maping.lastUpdatedDate) = '" + month + "'");
					}

				} else if (inputDto.getReportType().contains("year")) {
					Integer year = inputDto.getYear();
					if (year == null) {
						LocalDate ld = LocalDate.now();
						year = ld.getYear();
					}

					result.setReportTypeValue(year.toString());
					if (inputDto.getRole().equalsIgnoreCase("recruiter")) {
						countsql = new StringBuilder(
								"SELECT (SELECT count(*) FROM candidatemapping maping WHERE YEAR(maping.lastUpdatedDate)="
										+ year + " AND offereStatus LIKE '%Offer Released%' AND mappedUser_id = "
										+ userId + ") AS rejections ,"
										+ " (SELECT count(*) FROM candidatemapping maping WHERE YEAR(maping.lastUpdatedDate)="
										+ year + " AND mappedUser_id = " + userId+" AND onBoardedStatus LIKE '%On Boarded%' ) AS submissions  ");
						result.setUserId(userId);
					}

					else if (role.equalsIgnoreCase("Lead")) {

						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN onBoardedStatus LIKE '%On Boarded%' THEN onBoardedStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								" , bdmreq  req, addcontact addcontacts " + " where req.addContact_id=addcontacts.id "
										+ " and maping.bdmReq_id=req.id and (addcontacts.lead_id=" + userId + " "
										+ " or maping.mappedUser_id=" + userId
										+ ")  and year(maping.lastUpdatedDate) = '" + year + "'");
					}

					else if (role.equalsIgnoreCase("AM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN onBoardedStatus LIKE '%On Boarded%' THEN onBoardedStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");
						countsql.append(
								" , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.accountManger_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and year(maping.lastUpdatedDate) = '" + year + "'");
					}

					else if (role.equalsIgnoreCase("BDM")) {
						countsql = new StringBuilder("SELECT  "
								+ "COUNT(CASE WHEN offereStatus like '%Offer Released%' THEN candidateStatus END) AS rejectedCount, "
								+ "COUNT(CASE WHEN onBoardedStatus LIKE '%On Boarded%' THEN onBoardedStatus END) AS submissioncount "
								+ "FROM candidatemapping maping ");

						countsql.append(
								"  , bdmreq  req, addcontact addcontacts where req.addContact_id=addcontacts.id and maping.bdmReq_id=req.id and (addcontacts.primaryContact_id="
										+ userId + " or maping.mappedUser_id=" + userId
										+ ") and year(maping.lastUpdatedDate) = '" + year + "'");
					}

				}

				if (null != countsql) {
					Query createNativeQuery = this.getEntityManager().createNativeQuery(countsql.toString());
					List<Object[]> resultList = createNativeQuery.getResultList();
					for (Object[] data : resultList) {
						result.setTotalRejections(Integer.valueOf(data[0] + ""));
						result.setTotalSubmissions(Integer.valueOf(data[1] + ""));
					}
					result.setReportType(inputDto.getReportType());
					String base64 = barChartTemplates.getClosersVsJoiningByReportType(result);
					result.setBase64String(base64);
					response = new Response(result, ExceptionMessage.OK);

				}
			}

		} catch (Exception e) {
			response = new Response(ExceptionMessage.Exception, "Unable to Fetch Results");
			e.printStackTrace();
		}

		return response;
	}

}
