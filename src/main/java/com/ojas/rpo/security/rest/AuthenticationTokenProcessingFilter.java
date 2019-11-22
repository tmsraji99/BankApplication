package com.ojas.rpo.security.rest;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.service.UserService;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {
	
	String restUrl;
	
	
	private final UserService userService;
	// DaoUserService daoUserService =new DaoUserService(null, null);
	
	public AuthenticationTokenProcessingFilter(UserService userService) {
		this.userService = userService;
	}

	public boolean isRestCall(String url) {
		if (restUrl.contains("/rest")) {
			return true;
		}
		return false;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = this.getAsHttpRequest(request);
		String requestURI = httpRequest.getRequestURI();
		System.out.println("queryString" + httpRequest.getQueryString());
		restUrl = requestURI;
		String xyz = requestURI;
		System.out.println("call" + requestURI);
		if (isRestCall(requestURI)) {
			if (requestURI.contains("/authenticate") || requestURI.contains("/user")) {
				
				System.out.println("-->>;;;;;;;;;;;;;;;;;;;;;>preHandle");
				chain.doFilter(request, response);

			} else {
				System.out.println("unauthoriZed url of rest API  :" + requestURI);
			}

		} else {
			System.out.println("not a rest call   :" + xyz);
			chain.doFilter(request, response);
		}
		// HttpServletRequest httpRequest = this.getAsHttpRequest(request);
		String accessToken = this.extractAuthTokenFromRequest(httpRequest);
		System.out.println("TOKEN   :" + accessToken);

		if (null != accessToken) {
			User user = this.userService.findUserByAccessToken(accessToken);
			System.out.println("TOKEN RELATED USER   :" + user);

			if (null != user) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				System.out.println("authentication   :" + authentication);

				Date currentDate = new Date();
				// long currentTime_miliSec= currentDate.getTime();
				// DaoUserService.reNewExpiry(user, currentDate);

				chain.doFilter(request, response);
			} else {

			}
		}

		// chain.doFilter(request, response);
	}

	
	private HttpServletRequest getAsHttpRequest(ServletRequest request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}

		return (HttpServletRequest) request;
	}

	private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
		/* Get token from header */
		String authToken = httpRequest.getHeader("X-Access-Token");
		
		/* If token not found get it from request parameter */
		if (authToken == null) {
			authToken = httpRequest.getParameter("token");
		}

		return authToken;
	}
}
