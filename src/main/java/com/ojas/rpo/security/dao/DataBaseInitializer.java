package com.ojas.rpo.security.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ojas.rpo.security.dao.blogpost.BlogPostDao;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.User;

/**
 * Initialize the database with some test entries.
 *
 * @author Nagajyothi Rachakonda <jyothi.rachakonda@ojas-it.com>
 */
public class DataBaseInitializer {

	private UserDao userDao;
	private BlogPostDao blogPostDao;
    @Autowired
	private PasswordEncoder passwordEncoder;

	

	protected DataBaseInitializer() {

	}

	public DataBaseInitializer(UserDao userDao) {

		this.userDao = userDao;
		

	}

public void initDataBase() {

	/*
	User userUser = new User("user", this.passwordEncoder.encode("user"));
	userUser.addRole(Role.ADMIN);
	userUser.setRole("Admin");
	List<User> list = new ArrayList<User>();
		list = userDao.findAll();
	if (null == list || list.isEmpty()) {
			userUser.setStatus("Active");
		this.userDao.save(userUser);

	}
    */
	}
}
