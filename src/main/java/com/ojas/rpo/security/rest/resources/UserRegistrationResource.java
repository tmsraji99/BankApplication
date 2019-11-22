package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.OutlookMailSender;
import com.ojas.rpo.security.util.ReadPropertiesFile;

@Component
@Path("/Reg")
public class UserRegistrationResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDao userDao;

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	OutlookMailSender mailSender;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		User user = this.userDao.find(id);

		if (user != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
			if (user.getDob() != null) {
				user.setDob1(formatter.format(user.getDob()).toString());
			} else {
				user.setDob1("NoDate");
			}
			if (user.getDoj() != null) {
				user.setDoj1(formatter.format(user.getDoj()).toString());
			} else {
				user.setDoj1("NoDate");
			}

		}

		if (user == null) {

			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.UnRegistrationUser);
		} else
			return new Response(ExceptionMessage.StatusSuccess, user);
	}

	@GET
	@Path("/assign/{role}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> list1(@PathParam("role") String role) throws IOException {

		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		return this.userDao.findByRole(role);
	}

	@GET
	@Path("/listBDMandLead")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> listBDMandLead() throws IOException {

		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		return this.userDao.findByOnlyBdmleadRole();
	}

	@GET
	@Path("/listAM")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> listAm() throws IOException {

		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		return this.userDao.findByOnlyAMRole();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response list() throws IOException {
		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		// System.out.println(this.userDao.findAll());
		List<User> user = this.userDao.findAll();
		if (user == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		Date date = new Date();
		ListIterator litr = user.listIterator();
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

		while (litr.hasNext()) {
			User user1 = (User) litr.next();
			user1.setDob1(formatter.format(user1.getDob() != null ? user1.getDob() : date));
			user1.setDoj1(formatter.format(user1.getDoj() != null ? user1.getDoj() : date));
		}

		return new Response(ExceptionMessage.StatusSuccess, user);
	}

	/*
	 * @GET
	 * 
	 * @Path("/getUserByReportingId/{id}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public @ResponseBody Response
	 * listByReporting(@PathParam("id") Long id) throws IOException {
	 * this.logger.info("list()");
	 * 
	 * ObjectWriter viewWriter; if (this.isAdmin()) { viewWriter =
	 * this.mapper.writerWithView(JsonViews.Admin.class); } else { viewWriter =
	 * this.mapper.writerWithView(JsonViews.User.class); } //
	 * System.out.println(this.userDao.findAll()); List<User> user =
	 * this.userDao.findByReportingId(id); if (user == null) { return new
	 * Response(ExceptionMessage.DataIsEmpty); } Date date = new Date();
	 * ListIterator litr = user.listIterator(); SimpleDateFormat formatter = new
	 * SimpleDateFormat("dd MMMM yyyy");
	 * 
	 * while (litr.hasNext()) { User user1 = (User) litr.next();
	 * user1.setDob1(formatter.format(user1.getDob() != null ? user1.getDob() :
	 * date)); user1.setDoj1(formatter.format(user1.getDoj() != null ?
	 * user1.getDoj() : date)); }
	 * 
	 * return new Response(ExceptionMessage.StatusSuccess, user); }
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(User user, @Context HttpServletRequest request) {
		User userObject = new User(user.getUsername(), this.passwordEncoder.encode(user.getPassword()),
				user.getFirstName(), user.getLastName(), user.getContactNumber(), user.getEmail(), user.getRole(),
				user.getQuestion(), user.getAnswer(), user.getStatus(), user.getExtension(), user.getDesignation(),
				user.getDob(), user.getDoj(), user.getNewPassword(), user.getSalary(), user.getVariablepay(),
				user.getCtc(), user.getMintarget(), user.getMaxtarget(), user.getTargetduration(),
				user.getReportingId());

		try {
			EmailEntity emailEntity = ReadPropertiesFile.readConfig();// to read
																		// config
																		// file
			emailEntity.setMessagesubject(" User Registration Successful");// add
																			// subject
			emailEntity.setTo(user.getEmail());
			if (null != user.getReportingId() && null != user.getReportingId().getId()) {
				// emailEntity.setCc(this.userDao.find(user.getReportingId().getId()).getEmail());
			}

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

			String mes = "<i>Welcome</i><B>" + "  " + user.getFirstName() + ",</B><br><br>"
					+ "You can use the following credentials to access the <B>" + "RPO" + "</B> portal<br>" + "<a href="
					+ getAppUrl(request) + "/#!/login>Click Here to Login</a><br><br>" + "Your username is :"
					+ user.getName() + "<br>" + "Your password is :" + user.getPassword() + "<br><br>"
					+ "<b>Thanks & Regards</b><br>" + "<img src= \"cid:image\" alt=" + "Logo" + " width=" + "160" + " "
					+ "height=" + " " + "80>";
			emailEntity.setLogoImagePath(getAppUrl(request) + "/images/ojas-logo.png");
			emailEntity.setMessageBody(mes);

			if (user.getRole().equals("user")) {
				userObject.addRole(Role.USER);
			}
			if (user.getRole().equals("admin")) {
				userObject.addRole(Role.ADMIN);
			}
			if (user.getRole().equals("bdm")) {
				userObject.addRole(Role.BDM);
			}
			if (user.getRole().equals("recruiter")) {
				userObject.addRole(Role.RECRUITER);
			}

			if (user.getRole().equals("AM")) {
				userObject.addRole(Role.AM);
			}
			if (user.getRole().equals("Lead")) {
				userObject.addRole(Role.TEAMLEAD);
			}
			if (user.getRole().equals("Lead")) {
				userObject.addRole(Role.TEAMLEAD);
			}

			if (user.getRole().equals("FinanceLead")) {
				userObject.addRole(Role.FINANCELEAD);
			}
			if (user.getRole().equals("FinanceExecutive")) {
				userObject.addRole(Role.FINANCEEXECUTIVE);
			}
			if (user.getRole().equals("Management")) {
				userObject.addRole(Role.MANAGEMENT);
			}
			if (user.getRole().equals("MIS")) {
				userObject.addRole(Role.MIS);
			}
			
			

			// return this.userDao.save(adminUser);

			if (this.userDao.save(userObject) == null) {
				return new Response(ExceptionMessage.DataIsNotSaved);

			} else {
				mailSender.sendMail(emailEntity);
				System.out.println("Email Sent Succesfully...");
				return new Response(ExceptionMessage.StatusSuccess, userObject);

			}
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					"Unable to Register User. Enter Unique name or email or mobile.");
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					"Unable to Register User due to follwing Error : " + e.getMessage());
		}

	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	/*
	 * @POST
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Path("{id}") public @ResponseBody Response update(@PathParam("id") Long
	 * id, User list) {
	 * System.out.println("==========inside post method====RPO");
	 * list.setId(id); this.logger.info("update(): " + list);
	 * 
	 * if (this.userDao.save(list) == null) { return new
	 * Response(ExceptionMessage.DataIsNotSaved); } else return new
	 * Response(ExceptionMessage.StatusSuccess, list); }
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, User userList) {
		System.out.println("==========inside post method====RPO");
		userList.setId(id);
		this.logger.info("update(): " + userList);

		if (this.userDao.updateUserById(id, userList) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, userList);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/changepassword/{id}")
	public @ResponseBody Response changePassword(@PathParam("id") Long id, User user1) {
		System.out.println("==========inside post method====RPO");

		User user = this.userDao.find(id);

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(this.passwordEncoder.encode(user1.getPassword()));
		System.out.println(user.getPassword());

		String UP = this.passwordEncoder.encode(user1.getPassword());
		String DP = user.getPassword();

		System.out.println(UP == DP);

		System.out.println("-----------------------");

		System.out.println(UP.equals(DP));

		// System.out.println("true or false>>>>>>>>>>>>>>>>>>>>> " +
		// user.getPassword()==this.passwordEncoder.encode(user1.getPassword()));
		if (user != null) {

			user.setId(id);
			this.logger.info("update(): " + user);
			System.out.println("true or false      " + (user.getPassword()) == (user1.getPassword()));

			if (this.passwordEncoder.matches(user1.getPassword(), user.getPassword()))

			{
				System.out.println(user.getPassword() + "=================" + user1.getPassword());
				user.setPassword(this.passwordEncoder.encode(user1.getNewPassword()));
				user.setNewPassword(null);
				this.userDao.save(user);
				return new Response(ExceptionMessage.StatusSuccess, user);

			} else {
				System.out.println(user.getPassword() + "not equals=================" + user1.getPassword());
				return new Response(ExceptionMessage.DataIsNotSaved);
			}

			// throw new WebApplicationException(404);

		} else {
			System.out.println(user.getPassword() + "no user=================" + user1.getPassword());
			return new Response(ExceptionMessage.DataIsNotSaved);
		}

	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public User active(@PathParam("id") Long id) {

		this.logger.info("delete(id)");
		User list = this.userDao.find(id);
		list.setStatus("inactive");
		return this.userDao.save(list);
	}

	private boolean isAdmin() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		return userDetails.getAuthorities().contains(Role.ADMIN);
	}

	@GET
	@Path("{id}/{role}")
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getList(@PathParam("id") Long id, @PathParam("role") String role) throws IOException {

		List<User> user = this.userDao.findByRoleAndId(id, role);

		if (user == null) {
			return new Response(ExceptionMessage.DataIsEmpty, user);
		}

		return new Response(ExceptionMessage.StatusSuccess, user);

	}

	@Path("usersList/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response usersList(@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortingOrder, @QueryParam("sortingField") String sortingField,
			@QueryParam("searchField") String searchField, @QueryParam("searchInput") String searchInput) {

		Response res = this.userDao.findAllUsers(pageNo, pageSize, sortingOrder, sortingField, searchField,
				searchInput);

		return res;

	}

	@Path("getReportingManagersList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getReportingManagersList() {

		return this.userDao.getAllReportingManagers();

	}

	@Path("getUserNamesByRole")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserNamesByRole(@QueryParam("role") String role) {

		return this.userDao.getUserNamesByRole(role);

	}

	@Path("getRecruitersByReportingId/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecruitersByReportingId(@PathParam("id") Long id) {
		return this.userDao.getRecruitersByReportingId(id);
	}
}
