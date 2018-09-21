package com.org.yobox.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.org.yobox.common.AppConstants;
import com.org.yobox.model.Customer;

public class LoginInterceptor extends HandlerInterceptorAdapter implements
		AppConstants {

	private static Logger logger = (Logger) Logger.getInstance(LoginInterceptor.class);

	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			
			Customer customer = (Customer) request.getSession()
					.getAttribute(LOGGED_IN_CUSTOMER);

			if (customer == null) {
				response.sendRedirect("login.htm");
				return false;
			}

		} catch (Exception e) {
			response.sendRedirect("login.htm");
			return false;
		}

		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

}
