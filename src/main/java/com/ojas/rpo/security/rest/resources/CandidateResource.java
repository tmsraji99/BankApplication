
package com.ojas.rpo.security.rest.resources;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.XmlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CandidateData;
import com.ojas.rpo.security.entity.CandidateStatus;
import com.ojas.rpo.security.entity.CandidateStatusDTO;
import com.ojas.rpo.security.entity.CtcDetails;
import com.ojas.rpo.security.entity.Deduction;
import com.ojas.rpo.security.entity.Employee;
import com.ojas.rpo.security.entity.EmployeeEarning;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.OfferDetails;
import com.ojas.rpo.security.entity.OfferedRealse;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Resume;
import com.ojas.rpo.security.entity.Skill;
import com.ojas.rpo.security.entity.SummaryReport;
import com.ojas.rpo.security.service.EmployeeService;
import com.ojas.rpo.security.util.DateParsing;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.MailGenaration;
import com.ojas.rpo.security.util.MessageEntity;
import com.ojas.rpo.security.util.OutlookMailSender;
import com.ojas.rpo.security.util.ReadPropertiesFile;
import com.ojas.rpo.security.util.SearchTrackerList;
import com.ojas.rpo.security.util.TrackerFileds;
import com.ojas.rpo.security.util.WhatsAppMessageSender;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component
@Path("/addCandidate")
public class CandidateResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CandidatelistDao candidateDao;

	@Autowired
	private Resume resume;

	@Autowired
	OutlookMailSender mailSender;

	@Autowired
	private EmployeeService employeeService;

	@Value("${fileUploadPath}")
	private String documentsfolder;

	@Value("${resumeUploadPath}")
	private String resumesfolder;

	@Value("${offerletterUploadPath}")
	private String offerletterfolder;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/list/{role}/{status}/{id}")
	public @ResponseBody Response getdata(@PathParam("role") String role, @PathParam("status") String status,
			@PathParam("id") Long id) throws IOException {
		this.logger.info("list()");
		/*
		 * ObjectWriter viewWriter; if (this.isAdmin()) { viewWriter =
		 * this.mapper.writerWithView(JsonViews.Admin.class); } else { viewWriter =
		 * this.mapper.writerWithView(JsonViews.User.class); }
		 */
		List<Candidate> candidate = null;
		if (role.equalsIgnoreCase("AM")) {
			candidate = this.candidateDao.findAll(status);
		}
		if (role.equalsIgnoreCase("recruiter")) {
			candidate = this.candidateDao.getCandiateByRecurtierId(id);
		}

		if (candidate == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidate);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		Candidate candidate = this.candidateDao.find(id);
		if (candidate == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidate);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/display")
	public @ResponseBody Response display() {
		System.out.println("*******display***************************");
		return new Response(ExceptionMessage.StatusSuccess);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getResume/{id}")
	public @ResponseBody Response getResume(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		Candidate candidate = this.candidateDao.find(id);

		if (candidate == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else {
			if (candidate.getResume() != null) {
				resume.setResume(candidate.getResume());
				return new Response(ExceptionMessage.StatusSuccess, resume);
			} else {
				return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
			}
		}

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/PostResume/{id}")
	public @ResponseBody Response postResume(@PathParam("id") Long id, Resume resume) {
		System.out.println("==========inside post method====RPO");
		Candidate candidate = this.candidateDao.find(id);

		candidate.setId(id);
		candidate.setResume(resume.getResume());
		// candidate.setSubmittionDate(new Date());
		this.logger.info("update(): " + candidate);

		if (this.candidateDao.save(candidate) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/requirement/{id}")
	public @ResponseBody Response readRequitrementDetails(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		List<Candidate> candidatedetails = this.candidateDao.getCandiateByRequirementId(id);
		if (candidatedetails == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidatedetails);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Candidate candidate, @Context HttpServletRequest request) {
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + candidate);
		boolean b = false;
		// candidate.setCandidateStatus("Created");
		if (candidate.getId() == null) {
			b = this.candidateDao.chekduplicate(candidate.getMobile(), candidate.getEmail(),
					candidate.getPancardNumber());
			if (b) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		} else {
			Long id = null;
			List<Object[]> list = this.candidateDao.getMobileDetails(candidate.getMobile(), candidate.getEmail(),
					candidate.getPancardNumber());

			for (Object[] obj : list) {
				id = Long.parseLong(obj[0] + "");

			}

			if (id != null && id.longValue() != candidate.getId().longValue()) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}

		}

		candidate.setSubmissionDate(new Date());

		String firstname = DateParsing.textConvertor(candidate.getFirstName());
		candidate.setFirstName(firstname);
		String lastname = DateParsing.textConvertor(candidate.getLastName());
		candidate.setLastName(lastname);
		candidate.setStatusLastUpdatedDate(new Date());
		candidate.setSubmissionDate(new Date());
		Candidate c = this.candidateDao.save(candidate);
		
		 final String uri = "http://localhost:8089/elastic-search/candiate/indexresources";
	     
		    RestTemplate restTemplate = new RestTemplate();
		    restTemplate.postForObject(uri, candidate,Candidate.class);
		
		EmailEntity emailEntity = ReadPropertiesFile.readConfig();// to read
																	// config
																	// file
		emailEntity.setMessagesubject("Profile Registered");// add subject
		emailEntity.setTo(c.getEmail());

		String mes = "Dear <b>" + c.getFirstName() + "  " + c.getLastName() + "</b> <br>"
				+ "Thanks for sharing your profile to ojas innovative technologies. Your profile is registered with us."
				+ " your profile updates will be shared after completion of each process."
				+ "<br><br><b>Thanks & Regards</b><br>" + "<img src= \"cid:image\" alt=" + " Logo Not Available "
				+ " width=" + "160" + " " + "height=" + " " + "80>";
		emailEntity.setLogoImagePath(getAppUrl(request) + "/images/ojas-logo.png");
		emailEntity.setMessageBody(mes);
		mailSender.sendMail(emailEntity);

		// Sending WhatsApp Message
		MessageEntity msgEntity = ReadPropertiesFile.readMessageConfig();
		String msgText = "Dear  " + candidate.getFirstName() + " " + candidate.getLastName()
				+ ", your profile is successfully registered with Ojas Innovative Technologies ";

		msgEntity.setMsgText(msgText);
		msgEntity.setToNumber(candidate.getMobile());
		WhatsAppMessageSender.sendMessage(msgEntity);

		// Official Message Body
		emailEntity.setMessagesubject("New Profile Added");
		StringBuilder skills = null;
		for (Skill s : c.getSkills()) {
			if (skills == null) {
				skills = new StringBuilder(s.getSkillName());
			} else {
				skills.append(", " + s.getSkillName());
			}

		}
		String officialMessage = "Dear <b>" + c.getUser().getReportingId().getFirstName() + "  "
				+ c.getUser().getReportingId().getLastName() + "</b><br><br>" + " One New Profile is Added <i>"
				+ c.getUser().getFirstName() + " " + c.getUser().getLastName() + " </i>"
				+ " <b><i>Candidate Details : </i></b>" + "<div><table border = 1>"
				+ "<tr><th  colspan=\"2\"  style = \" background-color: #ffb84d; \">Candidate Details</th></tr>"
				+ "<tbody>" + "<tr><td><b>Candidate ID</b></td><td>" + c.getId() + "</td></tr>"
				+ "<tr><td><b>Candidate Name</b></td><td>" + c.getFirstName() + " " + c.getLastName() + "</td></tr>"
				+ "<tr><td><b>Skills </b></td><td>" + skills.toString() + "</td></tr>"
				+ "<tr><td><b>Relevant Experience</b></td><td>" + c.getRelevantExperience() + "</td></tr>"
				+ "<tr><td><b>total Experience</b></td><td>" + c.getTotalExperience() + "</td></tr>"
				+ "</tbody></table></div>" + "<br><br><b>Thanks & Regards</b><br>" + "<img src= \"cid:image\" alt="
				+ " Logo Not Available " + " width=" + "160" + " " + "height=" + " " + "80>";
		emailEntity.setTo(c.getUser().getEmail() + "," + c.getUser().getReportingId().getEmail());
		emailEntity.setMessageBody(officialMessage);
		emailEntity
				.setMessagesubject("New Profile Added || Candidate Name: " + c.getFirstName() + " " + c.getLastName());
		mailSender.sendMail(emailEntity);

		Response r = new Response(ExceptionMessage.StatusSuccess, c);
		r.setRes("Candidate added successfully");

		return r;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updating/{candidateId}")
	public @ResponseBody Response update(@PathParam("candidateId") Long candidateId, Candidate candidate) {
		candidate.setId(candidateId);
		this.logger.info("update(): " + candidate);
		candidate.setSubmissionDate(new Date());
		/*
		 * // Blob blob=candidate.getResume(); if
		 * (candidate.getCandidateStatus().equalsIgnoreCase("Rejected")) {
		 * candidate.setCandidateStatus("pending Review");
		 * this.candidateDao.save(candidate); }
		 */
		if (this.candidateDao.save(candidate) == null) {

			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {

			return new Response(ExceptionMessage.StatusSuccess, candidate);
		}

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updatingstatus/test") // /test/{candidateId}/{reqId}
	public @ResponseBody void changeCandidateStatus(CandidateStatusDTO statusDto) {

		String s = "Offer Pending";
		this.candidateDao.updateCandiate(statusDto, s);

		new Response(ExceptionMessage.StatusSuccess);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("update/{candidateId}/{reqId}/{loginId}/{status}")
	public @ResponseBody Response updatestatus(@PathParam("candidateId") Long candidateId,
			@PathParam("reqId") Long reqId, @PathParam("loginId") Long loginId, @PathParam("status") String status) {
		System.out.println("==========inside post method====RPO");

		CandidateStatusDTO statusDto = new CandidateStatusDTO();
		statusDto.setCandidateId(candidateId);
		statusDto.setRequirementId(reqId);
		statusDto.setLoginId(loginId);

		if (status.equalsIgnoreCase("Submit to Lead")) {
			status = "pending Review";
			this.candidateDao.updateCandiate(statusDto, status);
		}

		if (this.candidateDao.updateCandiate(statusDto, status) == false) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {
			Candidate can = candidateDao.find(candidateId);
			new MailGenaration().sendSpecificMail(can, status);

			return new Response(ExceptionMessage.StatusSuccess, can);

		}
	}
	// offere status

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("accepteddata/{id}")
	public @ResponseBody Response offerstatus(@PathParam("id") Long id, OfferedRealse release) {
		System.out.println("==========inside post method====RPO");
		Candidate candidate = this.candidateDao.find(id);

		candidate.setId(id);

		// candidate.setOffereStatus(release.getOffereStatus());
		/*
		 * if (candidate.getOffereStatus().equalsIgnoreCase("accepted")) {
		 * candidate.setCandidateStatus("Candidate OfferAccepted");
		 * candidate.setDoj(release.getDoj());
		 * 
		 * this.candidateDao.save(candidate); } else if
		 * (candidate.getOffereStatus().equalsIgnoreCase("rejected")) {
		 * candidate.setCandidateStatus("Candidate OfferRejected");
		 * candidate.setOffRejectedReasion(release.getOffRejectedReasion());
		 * this.candidateDao.save(candidate); }
		 */
		/*
		 * if (this.candidateDao.save(candidate) == null) { return new
		 * Response(ExceptionMessage.DataIsNotSaved); } else {
		 * 
		 * new MailGenaration().sendSpecificMail(candidate,"Status");
		 * 
		 * return new Response(ExceptionMessage.StatusSuccess, candidate);
		 * 
		 * }
		 */

		return null;
	}

	// offered letter releasing

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/offereletter/{id}")
	public @ResponseBody Response offereData(@PathParam("id") Long id, OfferedRealse release) {

		Candidate candidate = this.candidateDao.find(id);

		candidate.setId(id);
		candidate.setDoj(release.getDoj());
		// candidate.setOffereLetter(release.getOffereLetter());
		candidate.setSubmissionDate(new Date());

		this.logger.info("update(): " + candidate);

		if (this.candidateDao.save(candidate) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {
			this.candidateDao.save(candidate);
			new MailGenaration().sendSpecificMail(candidate, "Offer Released");

			return new Response(ExceptionMessage.StatusSuccess);
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getOfferDetails/{id}")
	public @ResponseBody Response getOfferDetails(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		Candidate candidate = this.candidateDao.find(id);

		if (candidate == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else {
			// if (candidate.getResume() != null) {
			// SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM
			// yyyy");

			OfferDetails od = new OfferDetails();
			String firstname = DateParsing.textConvertor(candidate.getFirstName());
			od.setFirstName(firstname);
			String lastname = DateParsing.textConvertor(candidate.getLastName());
			od.setLastName(lastname);
			// od.setLastName(candidate.getLastName());

			if (candidate.getDoj() != null) {
				System.out.println("D" + candidate.getDoj());
				od.setDoj1(DateParsing.dateParsing(candidate.getDoj()));
				// od.setDoj1(formatter.format(candidate.getDoj()).toString());

			} else {
				od.setDoj1("");
			}

			od.setExpectedPackage(candidate.getExpectedCTC());
			od.setHrFirstName(candidate.getUser().getFirstName());
			od.setHrLastName(candidate.getUser().getLastName());

			return new Response(ExceptionMessage.StatusSuccess, od);

		}

	}

	@POST
	@Path("/uploadOfferLetter/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response uploadOfferLetter(@FormDataParam("file") InputStream fileInputString,
			@FormDataParam("file") FormDataContentDisposition fileInputDetails, @PathParam("id") long id) {

		String SAVE_FOLDER = offerletterfolder + id + fileInputDetails.getFileName();
		String fileLocation = SAVE_FOLDER + fileInputDetails.getFileName();
		String status = null;
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		OutputStream out = null;
		long file_size = 0;
		// Save the file
		try {
			out = new FileOutputStream(new File(fileLocation));
			byte[] buffer = new byte[1024];
			int bytes = 0;

			while ((bytes = fileInputString.read(buffer)) != -1) {
				out.write(buffer, 0, bytes);
				file_size += bytes;
			}
			out.flush();
			out.close();

			logger.info(String.format("Inside uploadFile==> fileName: %s,  fileSize: %s",
					fileInputDetails.getFileName(), myFormat.format(file_size)));
			status = "SUCCESS";

		} catch (IOException ex) {
			logger.error("Unable to save file: " + fileLocation);
			return new Response(status, HttpStatus.CONFLICT, "notuploaded");
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Response(status, HttpStatus.OK,
				"File has been uploaded to:" + fileLocation + ", size: " + myFormat.format(file_size) + " bytes");
	}

	@POST
	@Path("/uploadResume/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response uploadResumes(@FormDataParam("file") InputStream fileInputString,
			@FormDataParam("file") FormDataContentDisposition fileInputDetails, @PathParam("id") long id) {

		String extension = null;
		if (fileInputDetails.getFileName().endsWith(".docx")) {
			extension = ".docx";
		} else if (fileInputDetails.getFileName().endsWith(".pdf")) {
			extension = ".pdf";
		} else {
			extension = fileInputDetails.getFileName();
		}

		String SAVE_FOLDER = resumesfolder + id + extension;
		String status = null;
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		OutputStream out = null;
		long file_size = 0;

		File temp = new File(SAVE_FOLDER);
		if (temp.exists()) {
			temp.delete();
		}

		// Save the file
		try {

			out = new FileOutputStream(new File(SAVE_FOLDER));
			byte[] buffer = new byte[1024];
			int bytes = 0;

			while ((bytes = fileInputString.read(buffer)) != -1) {
				out.write(buffer, 0, bytes);
				file_size += bytes;
			}

			out.flush();
			out.close();

			status = "SUCCESS";

		} catch (IOException ex) {
			logger.error("Unable to save file: " + SAVE_FOLDER);
			return new Response(status, HttpStatus.CONFLICT, "notuploaded");
		} catch (Exception e) {
			logger.error("Unable to convert file to docx: " + SAVE_FOLDER);
			return new Response(status, HttpStatus.CONFLICT, " file not converted");
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Response(status, HttpStatus.OK,
				"File has been uploaded to:" + SAVE_FOLDER + ", size: " + myFormat.format(file_size) + " bytes");

	}

	@GET
	@Path("/getResumes/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getResumes(@PathParam("id") long id) {

		String SAVE_FOLDER = resumesfolder + id + ".docx";
		byte[] fileContent = null;

		try {
			fileContent = Files.readAllBytes(Paths.get(SAVE_FOLDER));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new Response(ExceptionMessage.StatusSuccess, "", Base64Utils.encodeToString(fileContent));

	}

	@POST
	@Path("/upload/{type}/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response uploadFile(@FormDataParam("file") InputStream fileInputString,
			@FormDataParam("file") FormDataContentDisposition fileInputDetails, @PathParam("type") String type,
			@PathParam("id") long id) {

		System.out.println("Inside method");
		String SAVE_FOLDER = null;

		String name = fileInputDetails.getFileName();
		if (name.endsWith("pdf")) {
			SAVE_FOLDER = documentsfolder + type + "_" + id + ".pdf";
		} else if (name.endsWith("jpg")) {
			SAVE_FOLDER = documentsfolder + type + "_" + id + ".jpg";
		} else {
			SAVE_FOLDER = "notmatched";
		}
		if (!SAVE_FOLDER.equalsIgnoreCase("notmatched")) {
			System.out.println(SAVE_FOLDER);
			String fileLocation = SAVE_FOLDER;
			String status = null;
			NumberFormat myFormat = NumberFormat.getInstance();
			myFormat.setGroupingUsed(true);
			OutputStream out = null;
			long file_size = 0;
			// Save the file
			try {
				out = new FileOutputStream(new File(fileLocation));
				byte[] buffer = new byte[1024];
				int bytes = 0;

				while ((bytes = fileInputString.read(buffer)) != -1) {
					out.write(buffer, 0, bytes);
					file_size += bytes;
				}
				out.flush();
				out.close();

				logger.info(String.format("Inside uploadFile==> fileName: %s,  fileSize: %s",
						fileInputDetails.getFileName(), myFormat.format(file_size)));
				status = "SUCCESS";

			} catch (IOException ex) {
				logger.error("Unable to save file: " + fileLocation);
				return new Response(status, HttpStatus.CONFLICT, "notuploaded");
			} finally {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return new Response(status, HttpStatus.OK,
					"File has been uploaded to:" + fileLocation + ", size: " + myFormat.format(file_size) + " bytes");
		} else {
			return new Response("FAILED", HttpStatus.CONFLICT, "notuploaded");
		}
	}

	@GET
	@Path("/download/{filename}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public javax.ws.rs.core.Response downloadFilebyPath(@PathParam("filename") String fileName) {
		return download(fileName);
	}

	private javax.ws.rs.core.Response download(String fileName) {

		String FILE_FOLDER = documentsfolder;
		File folder = new File(FILE_FOLDER);
		File fileLocation = folder;
		// folder.mkdir();
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		String file_name = null;
		File[] files = fileLocation.listFiles();
		File file_folder = null;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			file_name = file.getName();
			if (file_name.contains(fileName)) {
				System.err.println("file_name" + file_name);
				file_folder = new File(FILE_FOLDER + file_name);
			}
		}

		// Retrieve the file

		javax.ws.rs.core.Response response;
		if (file_folder.exists()) {
			ResponseBuilder builder = javax.ws.rs.core.Response.ok(file_folder);
			builder.header("Content-Disposition", "attachment; filename=" + fileName);
			response = builder.build();

			long file_size = file_folder.length();
			logger.info(String.format("Inside downloadFile==> fileName: %s, fileSize: %s bytes", fileName,
					myFormat.format(file_size)));
		} else {
			logger.error(String.format("Inside downloadFile==> FILE NOT FOUND: fileName: %s", fileName));
			response = Response().entity("FILE NOT FOUND: " + file_folder).type("text/plain").build();
		}
		return response;
	}

	@GET
	@Path("/downloadResumes/{filename}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public javax.ws.rs.core.Response downloadResumePath(@PathParam("filename") String fileName) {
		return downloadResume(fileName);
	}

	private javax.ws.rs.core.Response downloadResume(String fileName) {

		String FILE_FOLDER = resumesfolder;
		File folder = new File(FILE_FOLDER);
		File fileLocation = folder; // folder.mkdir(); NumberFormat myFormat =
		NumberFormat.getInstance();
		// myFormat.setGroupingUsed(true);
		String file_name = null;
		File[] files = fileLocation.listFiles();
		File file_folder = null;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			file_name = file.getName();
			if (file_name.contains(fileName)) {
				System.err.println("file_name" + file_name);
				file_folder = new File(FILE_FOLDER + file_name);
			}
		}

		// Retrieve the file

		javax.ws.rs.core.Response response;
		if (file_folder.exists()) {
			ResponseBuilder builder = javax.ws.rs.core.Response.ok(file_folder);
			builder.header("Content-Disposition", "attachment; filename=" + fileName);
			response = builder.build();

			// long file_size = file_folder.length();
		} else {
			logger.error(String.format("Inside downloadFile==> FILE NOT FOUND: fileName: %s", fileName));
			response = Response().entity("FILE NOT FOUND: " + file_folder).type("text/plain").build();
		}
		return response;
	}

	@GET
	@Path("/downloadOfferLetter/{filename}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public javax.ws.rs.core.Response downloadOfferLetterPath(@PathParam("filename") String fileName) {
		return downloadOfferLetter(fileName);
	}

	private javax.ws.rs.core.Response downloadOfferLetter(String fileName) {

		String FILE_FOLDER = offerletterfolder;
		File folder = new File(FILE_FOLDER);
		File fileLocation = folder;
		// folder.mkdir();
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		String file_name = null;
		File[] files = fileLocation.listFiles();
		File file_folder = null;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			file_name = file.getName();
			if (file_name.contains(fileName)) {
				System.err.println("file_name" + file_name);
				file_folder = new File(FILE_FOLDER + file_name);
			}
		}

		// Retrieve the file

		javax.ws.rs.core.Response response;
		if (file_folder.exists()) {
			ResponseBuilder builder = javax.ws.rs.core.Response.ok(file_folder);
			builder.header("Content-Disposition", "attachment; filename=" + fileName);
			response = builder.build();

			long file_size = file_folder.length();
			logger.info(String.format("Inside downloadFile==> fileName: %s, fileSize: %s bytes", fileName,
					myFormat.format(file_size)));
		} else {
			logger.error(String.format("Inside downloadFile==> FILE NOT FOUND: fileName: %s", fileName));
			response = Response().entity("FILE NOT FOUND: " + file_folder).type("text/plain").build();
		}
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/candidateStatuslistByRecruiter/{status}/{id}")
	public @ResponseBody Response candidateStatuslistByRecruiter(@PathParam("status") String status,
			@PathParam("id") Long recutierId) throws IOException {
		this.logger.info("list()");

		List<Candidate> candidate = this.candidateDao.getCandiateByRecurtierIdByStatus(recutierId, status);

		if (candidate == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateListByRequirementId/{id}/{pageNo}/{pageSize}")
	public @ResponseBody Response getCandiateListByRequirementId(@PathParam("id") Long requiremnetId,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortOrder, @QueryParam("sortingField") String sortField,
			@QueryParam("searchText") String searchText, @QueryParam("searchField") String searchField)
			throws IOException {
		this.logger.info("list()");
		/*
		 * ObjectWriter viewWriter; if (this.isAdmin()) { viewWriter =
		 * this.mapper.writerWithView(JsonViews.Admin.class); } else { viewWriter =
		 * this.mapper.writerWithView(JsonViews.User.class); }
		 */

		/*
		 * List<Candidate> candidate =
		 * this.candidateDao.getCandiateByRequirementId(requiremnetId,pageNo, pageSize,
		 * sortOrder,sortField,searchText,searchField);
		 * 
		 * if (candidate == null) { // throw new WebApplicationException(404); return
		 * new Response(ExceptionMessage.DataIsEmpty); } else
		 * 
		 * if (candidate.size() > 0) {
		 * 
		 * ListIterator litr = candidate.listIterator(); SimpleDateFormat formatter =
		 * new SimpleDateFormat("dd MMMM yyyy");
		 * 
		 * while (litr.hasNext()) { Candidate req = (Candidate) litr.next(); if
		 * (req.getSubmittionDate() != null) {
		 * req.setProfileDate(formatter.format(req.getSubmittionDate()).toString ()); }
		 * 
		 * }
		 * 
		 * return new Response(ExceptionMessage.StatusSuccess, candidate); }
		 * 
		 * else {
		 * 
		 * return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION",
		 * "NULL"); }
		 */
		Response response = this.candidateDao.getCandiateByRequirementId(requiremnetId, pageNo, pageSize, sortOrder,
				sortField, searchText, searchField);
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getRequiremntListByCandidateId/{id}")
	public @ResponseBody Response getRequiremntListByCandidateId(@PathParam("id") Long candidateId) throws IOException {
		this.logger.info("list()");
		/*
		 * ObjectWriter viewWriter; if (this.isAdmin()) { viewWriter =
		 * this.mapper.writerWithView(JsonViews.Admin.class); } else { viewWriter =
		 * this.mapper.writerWithView(JsonViews.User.class); }
		 */

		List<BdmReq> candidate = this.candidateDao.getRequiremenByCandiateId(candidateId);

		if (candidate == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (candidate.size() > 0) {

			/*
			 * ListIterator litr = candidate.listIterator(); SimpleDateFormat formatter =
			 * new SimpleDateFormat("dd MMMM yyyy");
			 * 
			 * while (litr.hasNext()) { BdmReq req = (BdmReq) litr.next(); //
			 * req.setProfileDate(formatter.format(req.get).toString()); }
			 */

			return new Response(ExceptionMessage.StatusSuccess, candidate);
		}

		else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateListByRecurtierId/{id}/{status}")
	public @ResponseBody Response getCandiateListByRecurtierId(@PathParam("id") Long recutierId,
			@PathParam("status") String status) throws IOException {
		this.logger.info("list()");
		/*
		 * ObjectWriter viewWriter; if (this.isAdmin()) { viewWriter =
		 * this.mapper.writerWithView(JsonViews.Admin.class); } else { viewWriter =
		 * this.mapper.writerWithView(JsonViews.User.class); }
		 */

		// throw new WebApplicationException(404);
		return new Response(ExceptionMessage.DataIsEmpty);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandidateStatuCount/{status}")
	public @ResponseBody Response getCandidateStatuCount(@PathParam("status") String status) throws IOException {
		this.logger.info("list()");
		/*
		 * ObjectWriter viewWriter; if (this.isAdmin()) { viewWriter =
		 * this.mapper.writerWithView(JsonViews.Admin.class); } else { viewWriter =
		 * this.mapper.writerWithView(JsonViews.User.class); }
		 */

		Map<String, Integer> candidate = this.candidateDao.getCandidateStatuCount(status);

		if (candidate == null || candidate.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidate);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandidateStatusCountByRecruiter/{status}")
	public @ResponseBody Response getCandidateStatusCountByRecruiter(@PathParam("status") String status)
			throws IOException {
		this.logger.info("list()");
		/*
		 * ObjectWriter viewWriter; if (this.isAdmin()) { viewWriter =
		 * this.mapper.writerWithView(JsonViews.Admin.class); } else { viewWriter =
		 * this.mapper.writerWithView(JsonViews.User.class); }
		 */

		Map<String, Integer> candidate = this.candidateDao.getCandidateStatusCountByRecruiter(status);

		if (candidate == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidate);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandidateStatusCountByRecruiterId/{id}/{status}")
	public @ResponseBody Response getCandidateStatusCountByRecruiterId(@PathParam("id") Long recutierId,
			@PathParam("status") String status) throws IOException {
		this.logger.info("list()");
		/*
		 * ObjectWriter viewWriter; if (this.isAdmin()) { viewWriter =
		 * this.mapper.writerWithView(JsonViews.Admin.class); } else { viewWriter =
		 * this.mapper.writerWithView(JsonViews.User.class); }
		 */

		Map<String, Integer> candidate = this.candidateDao.getCandidateStatusCountByRecruiterId(recutierId, status);

		if (candidate == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidate);

	}

	@GET
	@Path("/convertFileContentToBlob/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response convertFileContentToBlob(@PathParam("id") Long id) throws IOException {
		CandidateData candidateData = new CandidateData();
		byte[] fileContent = null;
		// initialize string buffer to hold contents of file
		StringBuffer fileContentStr = new StringBuffer("");
		BufferedReader reader = null;
		try {
			// initialize buffered reader
			reader = new BufferedReader(new FileReader(resumesfolder + id + ".docx"));
			String line = null;
			// read lines of file
			while ((line = reader.readLine()) != null) {
				// append line to string buffer
				fileContentStr.append(line).append("\n");
			}
			// convert string to byte array
			fileContent = fileContentStr.toString().trim().getBytes();
			candidateData.setFile(fileContent);
			return new Response(ExceptionMessage.StatusSuccess, candidateData);

		} catch (IOException e) {
			throw new IOException("Unable to convert file to byte array. " + e.getMessage());
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	// pending

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/candidateListBySkillName/{skillName}")
	public @ResponseBody Response candidateListBySkillName(@PathParam("skillName") String skillName) {
		this.logger.info("read(skillName)");
		List<Candidate> candidateList = this.candidateDao.getCandiateBySkillName(skillName);
		if (candidateList == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidateList);
	}

	private ResponseBuilder Response() {
		return javax.ws.rs.core.Response.ok();
	}

	/*
	 * private boolean isAdmin() { Authentication authentication =
	 * SecurityContextHolder.getContext().getAuthentication(); Object principal =
	 * authentication.getPrincipal(); if ((principal instanceof String) && ((String)
	 * principal).equals("anonymousUser")) { return false; } UserDetails userDetails
	 * = (UserDetails) principal;
	 * 
	 * return userDetails.getAuthorities().contains(Role.ADMIN); }
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updatingstatusbylead") // /{candidateId}/{reqId}/{status}/{loginId}
	public @ResponseBody Response changeStatus(CandidateStatusDTO statusDto) {

		this.candidateDao.updateCandiate(statusDto);

		return new Response(ExceptionMessage.StatusSuccess, "200", "Success");

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updatingOfferStatus/{candidateId}/{reqId}/{offerStatus}/{userId}")
	public @ResponseBody Response updatingStatus(@PathParam("candidateId") Long candidateId,
			@PathParam("reqId") Long reqId, @PathParam("userId") Long userId,
			@PathParam("offerStatus") String offerStatus) {
		String status = null;
		if (offerStatus.equalsIgnoreCase("Offer Released")) {
			status = "On Board";
		} else {
			status = offerStatus;
		}

		int i = this.candidateDao.updatingStatus(candidateId, status, offerStatus, reqId, userId);
		if (i > 0) {
			return new Response(ExceptionMessage.StatusSuccess, "200", "Success");
		}
		return new Response(ExceptionMessage.Not_Found, "200", "Failed");

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updatingOnBoardStatus")
	public @ResponseBody Response updatingOnBoardStatus(@RequestBody ONBoardBean onBoardBean) {
		String status = null;
		if (onBoardBean.getStatus().equalsIgnoreCase("On Boarded")) {
			status = "Raise Invoice";
		} else {
			status = onBoardBean.getStatus();
		}

		int i = this.candidateDao.updatingOnBoardStatus(onBoardBean.getCandidateId(), status, onBoardBean.getStatus(),
				onBoardBean.getOnBoardingDate(), onBoardBean.getCtc(), onBoardBean.getReqId(), onBoardBean.getUserId());
		if (i > 0) {
			return new Response(ExceptionMessage.StatusSuccess, "200", "Success");
		}
		return new Response(ExceptionMessage.Not_Found, "200", "Failed");

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/confirmBoardStatus")
	public @ResponseBody Response confirmBoardStatus(@RequestBody ONBoardBean onBoardBean) {

		int i = this.candidateDao.confirmBoardStatus(onBoardBean.getCandidateId(), onBoardBean.getStatus(),
				onBoardBean.getOnBoardingDate(), onBoardBean.getReqId(), onBoardBean.getUserId());
		if (i > 0) {
			return new Response(ExceptionMessage.StatusSuccess, "200", "Success");
		}
		return new Response(ExceptionMessage.Not_Found, "200", "Failed");

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCtcDetails")
	public @ResponseBody Response getCtcDetails(@RequestBody Employee employee) {
		CtcDetails ctcDetails = null;
		try {
			EmployeeEarning employeeEarning = employeeService.getEmployeeEarnings(employee);
			Deduction deduction = employeeService.getEmployeeDeductions(employee, employeeEarning);
			ctcDetails = new CtcDetails();
			ctcDetails.setDeduction(deduction);
			ctcDetails.setEmployee(employee);
			ctcDetails.setEmployeeEarning(employeeEarning);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ctcDetails != null) {
			return new Response(ExceptionMessage.StatusSuccess, ctcDetails);
		}

		return new Response(ExceptionMessage.Not_Found, "500", "Failed");

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllCandidatesByAddedPerson/{logId}/{pageNo}/{pageSize}")
	public @ResponseBody Response getAllCandidatesByAddedPerson(@PathParam("logId") Long logId,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortOrder, @QueryParam("sortingField") String sortField,
			@QueryParam("searchText") String searchText, @QueryParam("searchField") String searchField) {

		return this.candidateDao.getAllCandidatesByAddedPerson(logId, pageNo, pageSize, sortOrder, sortField,
				searchText, searchField);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/candidateStausList/{status}")
	public @ResponseBody Response candidateListByStatus(@PathParam("status") String status) {

		List<CandidateStatus> list = new ArrayList<CandidateStatus>();
		if (status.equalsIgnoreCase("Accepted By Lead")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Submitted to Customer");
			list.add(candidateStatus);
			return new Response(ExceptionMessage.StatusSuccess, list);
		}

		if (status.equalsIgnoreCase("Submitted to Customer")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Customer Shortlisted");
			list.add(candidateStatus);

			CandidateStatus candidateStatus1 = new CandidateStatus();
			candidateStatus1.setStatus("Customer Rejected");
			list.add(candidateStatus1);
		}

		if (status.equalsIgnoreCase("Customer Shortlisted")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Process for Interview");
			list.add(candidateStatus);
		}
		if (status.equalsIgnoreCase("Process for Interview")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Waiting for Interview Feedback");
			list.add(candidateStatus);
		}

		if (status.equalsIgnoreCase("Waiting for Interview Feedback")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Customer feedback");
			list.add(candidateStatus);

		}

		if (status.equalsIgnoreCase("Customer feedback")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Shortlisted by Customer");
			list.add(candidateStatus);

		}
		if (status.equalsIgnoreCase("InterviewSchedule")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Process for Interview");
			list.add(candidateStatus);

			/*
			 * CandidateStatus candidateStatus1 = new CandidateStatus();
			 * candidateStatus.setStatus("Shortlisted by Customer");
			 * list.add(candidateStatus1);
			 */
		}

		if (status.equalsIgnoreCase("Offer Release")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Offer Release");
			list.add(candidateStatus);

			/*
			 * CandidateStatus candidateStatus1 = new CandidateStatus();
			 * candidateStatus.setStatus("Shortlisted by Customer");
			 * list.add(candidateStatus1);
			 */
		}
		if (status.equalsIgnoreCase("On Board")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("On Board");
			list.add(candidateStatus);

			/*
			 * CandidateStatus candidateStatus1 = new CandidateStatus();
			 * candidateStatus.setStatus("Shortlisted by Customer");
			 * list.add(candidateStatus1);
			 */
		}

		return new Response(ExceptionMessage.StatusSuccess, list);

	}

	@GET
	@Path("/downloadResume/{candidateId}")
	public javax.ws.rs.core.Response downloadApkFile(@PathParam("candidateId") Long id) {
		final Long cid = id;
		StreamingOutput fileStream = new StreamingOutput() {
			@Override
			public void write(java.io.OutputStream output) throws IOException, WebApplicationException {
				try {
					java.nio.file.Path path = Paths.get(resumesfolder + "/regeneratedResume/" + cid + ".pdf");
					byte[] data = Files.readAllBytes(path);
					output.write(data);
					output.flush();
				} catch (Exception e) {
					throw new WebApplicationException();
				}
			}
		};
		return javax.ws.rs.core.Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
				.header("content-disposition", "attachment; filename = " + cid + ".pdf").build();
	}

	@Path("/uploadZip/{candidateId}")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadZipOrRar(@PathParam("candidateId") Long candidateId,
			@FormDataParam("zipfile") InputStream fileData,
			@FormDataParam("zipfile") FormDataContentDisposition fileInfo) throws IOException {
		InputStream is = new ByteArrayInputStream(StreamUtils.copyToByteArray(fileData));
		ZipInputStream zis = new ZipInputStream(is);
		ZipEntry readEntry;

		FileOutputStream fout = new FileOutputStream(documentsfolder + candidateId + ".zip");
		ZipOutputStream zout = new ZipOutputStream(fout);

		Response response = null;

		try {
			while ((readEntry = zis.getNextEntry()) != null) {

				int size;
				byte[] buffer = new byte[2048];

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedOutputStream bos = new BufferedOutputStream(baos, buffer.length);

				while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
					bos.write(buffer, 0, size);
				}
				bos.flush();
				bos.close();

				ZipEntry writeEntry = new ZipEntry(readEntry.getName());
				zout.putNextEntry(writeEntry);
				zout.write(baos.toByteArray());

				response = new Response(ExceptionMessage.OK);

			}
		} catch (Exception e) {
			response = new Response(ExceptionMessage.valueOf("Zip File Uploading Failed " + e.getLocalizedMessage()));
			e.printStackTrace();
		}

		zis.close();
		zout.close();
		is.close();
		return response;
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	@POST
	@Path("/test")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response testName() throws IOException, XmlException, InvalidFormatException {
		return null;
	}

	@POST
	@Path("/updateCandidateInfo/{candidateId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response updateCandidateInfo(@PathParam("candidateId") Long id, Candidate candidate) {

		if (null != candidate && null != id && !id.equals("undefined")) {
			return this.candidateDao.updateCandidateInfo(id, candidate);
		} else {
			return new Response(ExceptionMessage.Bad_Request, "Invalid Input");
		}

	}

	@GET
	@Path("/getTrackerFields")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTrackerFields() {
		List<TrackerFileds> trackerFiledsArray = new ArrayList<TrackerFileds>();
		InputStream inputStream = null;
		InputStreamReader isr = null;
		try {

			inputStream = this.getClass().getClassLoader().getResourceAsStream("datatrackerfields.properties");
			isr = new InputStreamReader(inputStream);
			Properties p = new Properties();
			p.load(isr);
			Set<Entry<Object, Object>> properties = p.entrySet();
			for (Entry<Object, Object> entry : properties) {
				TrackerFileds trackerFileds = new TrackerFileds();
				trackerFileds.setColumnName(entry.getKey().toString());
				trackerFileds.setColumnLable(entry.getValue().toString());
				trackerFiledsArray.add(trackerFileds);
			}
		} catch (Exception e) {

		} finally {
			try {
				inputStream.close();
				isr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new Response(ExceptionMessage.StatusSuccess, trackerFiledsArray);
	}

	@POST
	@Path("/getTrackerAsExcel/{clientId}/{reqId}")
	@Consumes(MediaType.APPLICATION_JSON)
	// @Produces("application/vnd.ms-excel")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Candidatelistexcel(SearchTrackerList searchTrackerList, @PathParam("clientId") Long clientId,
			@PathParam("reqId") Long reqId) throws Exception {
		InputStream inputStream = null;
		InputStreamReader isr = null;
		Properties properties = null;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("datatrackerfields.properties");
			isr = new InputStreamReader(inputStream);
			properties = new Properties();
			searchTrackerList.getList().add("Vendor Name");
			searchTrackerList.getList().add("Education");
			properties.load(isr);
		} catch (Exception e) {

		} finally {

		}
		return candidateDao.getCandidatelistinExcel(searchTrackerList, clientId, reqId, properties);

	}

	@GET
	@Path("/getCandidatesMappingByStatus/{status}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public javax.ws.rs.core.Response getCandidatesMappingByStatus(SearchTrackerList searchTrackerList)
			throws Exception {

		return null;
	}



	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/summaryreport")
	public @ResponseBody Response SummaryReport(@RequestBody SummaryReport summeryReport) throws IOException {
		Long bdmReq_id = summeryReport.getId();
		this.logger.info("list()");
		return candidateDao.getSummaryReport(bdmReq_id);

	}


}
