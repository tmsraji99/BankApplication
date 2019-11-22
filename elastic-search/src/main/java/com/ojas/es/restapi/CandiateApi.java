package com.ojas.es.restapi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ojas.es.dao.CandidatelistDao;
import com.ojas.es.dao.impl.LocationDao;
import com.ojas.es.dao.impl.SkillDao;
import com.ojas.es.entity.AddQualification;
import com.ojas.es.entity.Candidate;
import com.ojas.es.entity.CandidateSearchResponse;
import com.ojas.es.entity.IndexResponse;
import com.ojas.es.entity.Location;
import com.ojas.es.entity.Skill;
import com.ojas.es.util.CandidateSearch;
import com.ojas.es.util.MessageEntity;
import com.ojas.es.util.ReadPropertiesFile;
import com.ojas.es.util.WhatsAppMessageSender;


@Component
@Path("/candiate")
public class CandiateApi {

	@Autowired
	private SkillDao skillDao;

	@Autowired
	private LocationDao locationDao;

	@Autowired
	private com.ojas.es.dao.impl.QualificationDao qualificationDao;

	@Autowired
	private CandidatelistDao canDao;

	@POST
	@Path("/uploadResource")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)

	public @ResponseBody String uploadResource(@FormDataParam("file") InputStream fileInputString,
			@FormDataParam("file") FormDataContentDisposition fileInputDetails) {
		Workbook workbook = null;
		try {

			String SAVE_FOLDER = "D:\\apache-tomcat-9.0.20-windows-x64\\apache-tomcat-9.0.20\\webapps\\Documents\\"
					+ fileInputDetails.getFileName();
			OutputStream out = null;
			long file_size = 0;

			File temp = new File(SAVE_FOLDER);
			if (temp.exists()) {
				temp.delete();
			}

			// Save the file

			out = new FileOutputStream(new File(SAVE_FOLDER));
			byte[] buffer = new byte[2048];
			int bytes = 0;

			while ((bytes = fileInputString.read(buffer)) != -1) {
				out.write(buffer, 0, bytes);
				file_size += bytes;
			}

			out.flush();
			out.close();
			workbook = new XSSFWorkbook(temp);
			String name = "";
			String lastName = "";
			String email = "";
			String phone = "";
			String currentLocation = "";
			String preferredLocations = "";
			String totalExp = "";
			String currentCompany = "";
			String role = "";
			String skillString = "";
			String cCTc = "";
			String notice = "";
			String degree = "";
			String pg = "";
			String gender = "";
			String workPermit = "";
			
			Sheet firstSheet = workbook.getSheetAt(0);
			for (int i = 1; i <= firstSheet.getLastRowNum(); i++) {
				for (int j = 0; j <= firstSheet.getRow(i).getLastCellNum(); j++) {

					try {
						if (firstSheet.getRow(i).getCell(0) != null) {
							name = printCellValue(firstSheet.getRow(i).getCell(0));
							lastName = printCellValue(firstSheet.getRow(i).getCell(0));
						}
						if (firstSheet.getRow(i).getCell(1) != null) {
							email = printCellValue(firstSheet.getRow(i).getCell(1));
						} 
						if (firstSheet.getRow(i).getCell(2) != null) {
							phone = printCellValue(firstSheet.getRow(i).getCell(2));
						} 
						if (firstSheet.getRow(i).getCell(3) != null) {
							currentLocation = printCellValue(firstSheet.getRow(i).getCell(3));
						} 
						if (firstSheet.getRow(i).getCell(4) != null) {
							preferredLocations = printCellValue(firstSheet.getRow(i).getCell(4));
						} 
						if (firstSheet.getRow(i).getCell(5) != null) {
							totalExp = printCellValue(firstSheet.getRow(i).getCell(5));
						} 
						if (firstSheet.getRow(i).getCell(6) != null) {
							currentCompany = printCellValue(firstSheet.getRow(i).getCell(6));
						} 
						if (firstSheet.getRow(i).getCell(7) != null) {
							role = printCellValue(firstSheet.getRow(i).getCell(7));
						} 
						if (firstSheet.getRow(i).getCell(8) != null) {
							skillString = printCellValue(firstSheet.getRow(i).getCell(8));
						} 
						if (firstSheet.getRow(i).getCell(9) != null) {
							cCTc = printCellValue(firstSheet.getRow(i).getCell(9));
						} 
						if (firstSheet.getRow(i).getCell(10) != null) {
							notice = printCellValue(firstSheet.getRow(i).getCell(10));
						} 
						if (firstSheet.getRow(i).getCell(11) != null) {
							degree = printCellValue(firstSheet.getRow(i).getCell(11));
						} 
						if (firstSheet.getRow(i).getCell(12) != null) {
							pg = printCellValue(firstSheet.getRow(i).getCell(12));
						} 
						if (firstSheet.getRow(i).getCell(13) != null) {
							gender = printCellValue(firstSheet.getRow(i).getCell(13));
						} 

						Candidate can = new Candidate();
						can.setFirstName(name);
						can.setLastName(lastName);
						can.setEmail(email);

						if (phone != null && !phone.isEmpty()) {

							if (phone.contains(",")) {
								String array[] = phone.split(",");
								for (String arr : array) {
									if (arr != null && !arr.isEmpty()) {
										if (arr.length() == 12) {
											String slipPhaone = arr.substring(2, arr.length());
											can.setMobile(slipPhaone);
										} else if (arr.length() == 13) {
											String slipPhaone = arr.substring(3, arr.length());
											can.setMobile(slipPhaone);
										} else {
											can.setMobile(arr);
										}
									}

								}

							} else {
								if (phone.length() == 12) {
									String slipPhaone = phone.substring(2, phone.length());
									can.setMobile(slipPhaone);
								} else if (phone.length() == 13) {
									String slipPhaone = phone.substring(3, phone.length());
									can.setMobile(slipPhaone);
								} else {
									can.setMobile(phone);
								}
							}
						}

						can.setCurrentLocation(currentLocation);

						Location location = null;
						Long id = locationDao.getLocaltionByName(preferredLocations);
						if (id.longValue() == 0L) {
							if (!preferredLocations.isEmpty()) {
								location = new Location();
								location.setLocationName(preferredLocations);
								location = locationDao.save(location); // saving locations
							}

						} else {
							location = new Location();
							location.setLocationName(preferredLocations);
							location.setId(id);
						}
						if (!preferredLocations.equalsIgnoreCase("")) {
							can.setLocation(location);
						}

						String exp = totalExp;

						char charAtZero = exp.charAt(0);

						char charAt10 = exp.charAt(10);
						can.setTotalExperience(charAtZero + "");
						can.setNoOfMonths(charAt10 + "");
						can.setCurrentCompanyName(currentCompany);

						can.setAppliedPossitionFor(role);
						String skillsd = skillString;
						if(!skillsd.isEmpty()){
							String skills[] = skillsd.split(",");

							List<Skill> list = new ArrayList<Skill>();
							for (String str : skills) {
								Long skillId = skillDao.getSkillByName(str);
								if (skillId.longValue() == 0L) {
									Skill skill = new Skill();
									skill.setSkillName(str);
									Skill s = skillDao.save(skill); // saving skills
									list.add(s);
								} else {
									Skill skill = new Skill();
									skill.setSkillName(str);
									skill.setId(skillId);
									list.add(skill);
								}
							}
						}
						
						

						can.setCurrentCTC(cCTc);
						String noticePeriod = notice;
						if (noticePeriod.equalsIgnoreCase("15 Days or less")) {
							can.setNoticePeriod("15");
						} else if (noticePeriod.equalsIgnoreCase("1 Months")) {
							can.setNoticePeriod("30");
						} else if (noticePeriod.equalsIgnoreCase("2 Months")) {
							can.setNoticePeriod("60");
						} else if (noticePeriod.equalsIgnoreCase("3 Months")) {
							can.setNoticePeriod("90");
						} else if (noticePeriod.equalsIgnoreCase("More than 3 Months")) {
							can.setNoticePeriod("120");
						} else if (noticePeriod.equalsIgnoreCase("NA")
								&& noticePeriod.equalsIgnoreCase("Serving Notice Period")) {

							can.setNoticePeriod("0");
						}

						List<AddQualification> deslist = new ArrayList<>();
						if (!degree.equalsIgnoreCase("NA")) {
							Long desid = qualificationDao.getRoleByName(degree);
							if (desid.longValue() == 0L) {
								if (!degree.isEmpty()) {
									AddQualification des = new AddQualification();
									des.setQualificationName(degree);
									des = qualificationDao.save(des); // saving degree qualification
									deslist.add(des);
								}
							} else {
								AddQualification des = new AddQualification();
								des.setId(desid);
								des.setQualificationName(degree);
								deslist.add(des);
							}

						}

						if (!pg.equalsIgnoreCase("NA")) {
							Long desid = qualificationDao.getRoleByName(pg);
							if (desid.longValue() == 0L) {
								if (!pg.isEmpty()) {
									AddQualification des = new AddQualification();
									des.setQualificationName(pg);
									des = qualificationDao.save(des); // saving pg qualification
									deslist.add(des);
								}

							} else {
								AddQualification des = new AddQualification();
								des.setId(desid);
								des.setQualificationName(pg);
								deslist.add(des);
							}

						}

						can.setGender(gender);
						can.setWorkpermit(workPermit);
						Candidate ca = canDao.save(can); // saving candidate details
						
						// Sending WhatsApp Message
						MessageEntity msgEntity = ReadPropertiesFile.readMessageConfig();
						String msgText = "Dear  " + can.getFirstName() + " " + can.getLastName()
								+ ", your profile is successfully registered with Ojas Innovative Technologies ";

						msgEntity.setMsgText(msgText);
						msgEntity.setToNumber(can.getMobile());
						WhatsAppMessageSender.sendMessage(msgEntity);

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
			
			 final String uri = "http://localhost:8089/elastic-search/candiate/indexresources";
		     
			    RestTemplate restTemplate = new RestTemplate();
			    restTemplate.postForObject(uri, new Object(),Candidate.class);
			
			// canDao.save(can);

		} catch (Exception e) {

		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return "Success";
	}

	// cod for test
	@POST
	@Path("/getResourcessssss/{pageNo}/{pageSize}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response getResource(@RequestBody CandidateSearch search, @PathParam("pageNo") Integer pageNo,
			@PathParam("pageSize") Integer pageSize) {
		CandidateSearchResponse canlist = canDao.search(search, pageNo, pageSize);
		return Response.ok(canlist, MediaType.APPLICATION_JSON).build();
	}
	
	
	@POST
	@Path("/indexresources")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response indexResource() {
		IndexResponse response=canDao.candidateIndex();
		return Response.ok(response,MediaType.APPLICATION_JSON).build();
	}

	private String printCellValue(Cell cell) {
		switch (cell.getCellTypeEnum()) {

		case STRING:
			return cell.getRichStringCellValue().getString();

		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue() + "";
			} else {
				return new BigDecimal(cell.toString()).toPlainString();
			}

		default:
			return "";
		}

	}

}
