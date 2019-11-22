package com.ojas.rpo.security.entity;

import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ojas.rpo.security.JsonViews;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Table(name="user")
@javax.persistence.Entity
public class User implements Entity, UserDetails
{
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 80, nullable = false)
    private String password;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private Long contactNumber;
    @Column
    private String email;
    @Column
    private String role;
    @Column
    private String question;
    @Column
    private String answer;
    @Column
	private String status;
    @Column
    private Date date;
    
    private String dob1;
    private String doj1;
    
    @ManyToOne
    private User reportingId;
    
    
    public String getDob1() {
		return dob1;
	}

	public void setDob1(String dob1) {
		this.dob1 = dob1;
	}

	public String getDoj1() {
		return doj1;
	}

	public void setDoj1(String doj1) {
		this.doj1 = doj1;
	}

	@Column
    private Integer extension;
    @Column
    private String designation;
    @Column
    private Date dob;
    @Column
    private Date doj;
    @Column
    private String newPassword;
    
    
    @Column
    private Long salary;
    @Column
    private Long variablepay;
    @Column
    private Long ctc;
    @Column
    private Long mintarget;
    @Column
    private Long maxtarget;
    @Column
    private String targetduration;

    
    public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Integer getExtension() {
		return extension;
	}

	public void setExtension(Integer extension) {
		this.extension = extension;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	@ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<Role>();

    public User()
    {
    	this.date = new Date();
    }

    public User(String name, String passwordHash)
    {
        this.name = name;
        this.password = passwordHash;
       
        
    }
    @JsonView(JsonViews.User.class)
    public Date getDate()
    {
        return this.date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
    @JsonView(JsonViews.Admin.class)
    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
    @JsonView(JsonViews.User.class)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    
    @JsonView(JsonViews.User.class)
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@JsonView(JsonViews.User.class)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

	public Long getVariablepay() {
		return variablepay;
	}

	public void setVariablepay(Long variablepay) {
		this.variablepay = variablepay;
	}

	public Long getCtc() {
		return ctc;
	}

	public void setCtc(Long ctc) {
		this.ctc = ctc;
	}

	public Long getMintarget() {
		return mintarget;
	}

	public void setMintarget(Long mintarget) {
		this.mintarget = mintarget;
	}

	public Long getMaxtarget() {
		return maxtarget;
	}

	public void setMaxtarget(Long maxtarget) {
		this.maxtarget = maxtarget;
	}

	public String getTargetduration() {
		return targetduration;
	}

	public void setTargetduration(String targetduration) {
		this.targetduration = targetduration;
	}

	@JsonView(JsonViews.User.class)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@JsonView(JsonViews.User.class)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	@JsonView(JsonViews.User.class)
	public String getStatus() {
		return status;
	}
	
	@JsonView(JsonViews.User.class)
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	@JsonView(JsonViews.User.class)
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Role> getRoles()
    {
        return this.roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    public void addRole(Role role)
    {
        this.roles.add(role);
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return this.getRoles();
    }

    @Override
    public String getUsername()
    {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

	public User( String name, String password, String firstName, String lastName, Long contactNumber,
			String email, String role, String question, String answer, String status, int extension, String designation, 
			Date dob, Date doj, String newPassword,Long salary, Long variablepay,Long ctc,Long mintarget, Long maxtarget,String targetduration,User reportingId) {
		super();
		this.name = name;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNumber = contactNumber;
		this.email = email;
		this.role = role;
		this.question = question;
		this.answer = answer;
		this.status = "Active";
		this.extension=extension;
		this.designation=designation;
		this.dob=dob;
		this.doj=doj;
		this.newPassword=newPassword;
		this.salary=salary;
		this.variablepay=variablepay;
		this.ctc=ctc;
		this.mintarget=mintarget;
		this.maxtarget=maxtarget;
		this.targetduration=targetduration;
		this.reportingId=reportingId;

	}

	public User getReportingId() {
		return reportingId;
	}

	public void setReportingId(User reportingId) {
		this.reportingId = reportingId;
	}
}
