package com.org.yobox.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.org.yobox.AppDTO;
import com.org.yobox.service.AuthenticateService;

@Controller
public class HomeController extends BaseController {
	
	private static Logger logger = (Logger) Logger
	.getInstance(HomeController.class);

	@Autowired
	private AuthenticateService authenticateService; 
	
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("index");
		try
		{
			String ipAddress=request.getRemoteAddr();
			
			logger.info("new user"+ipAddress);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
		return mv;
	}


	public AuthenticateService getAuthenticateService() {
		return authenticateService;
	}


	public void setAuthenticateService(AuthenticateService authenticateService) {
		this.authenticateService = authenticateService;
	}
	
	}
