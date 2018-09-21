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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.org.yobox.AppDTO;
import com.org.yobox.common.ResponseStatus;
import com.org.yobox.model.Customer;
import com.org.yobox.service.BeautyQuizService;
import com.org.yobox.util.CommonUtils;

@Controller
public class BeautyQuizController extends BaseController {
	

	private static Logger logger = (Logger) Logger
			.getInstance(BeautyQuizController.class);
	
	@Autowired
	private BeautyQuizService beautyQuizService;
	
	
	/*
	 * If user already signed in and is not editing then redirect to subscription
	 * if beauty preference edit then redirect to quiz
	 * if quiz already done and reach here again that means from login page 
	 * sign up is clicked after completing quiz , then redirect to sign up
	 */
	@RequestMapping("/quiz")
	public ModelAndView quiz(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("beautyQuiz");
				
		String beautyPref=(String)getHttpSession().getAttribute(BEAUTY_PREFERENCE_SESS);
		Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
		String edit = request.getParameter("edit");
		if(customer!=null && CommonUtils.isEmpty(edit))
		{
			mv.setViewName("redirect:/chooseSubscription.htm");
		}
		else if(!CommonUtils.isEmpty(beautyPref))
		{
			mv.setViewName("redirect:/signup.htm");
		}
		
		return mv;
	}
	
/*
 * check if user is updating the beauty preference then redirect to 
 * dashboard or subscription instead of sign up
 * if the customer already logged in means the user is editing the values
 */
	@RequestMapping(value="/addBeautyPreference", method = RequestMethod.POST)
	public ModelAndView addBeautyPreference(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("redirect:/signup.htm");
		Map dataMap=new HashMap();
		AppDTO appDTO=new AppDTO();
		try{
		String skinColour=request.getParameter("skinColour");
		String hairColour=request.getParameter("hairColour");
		String[] skinConcerns=request.getParameterValues("skinConcern");
		String[] hairTypes=request.getParameterValues("hairType");
		
		
		dataMap.put("skinColour", skinColour);
		dataMap.put("hairColour", hairColour);
		dataMap.put("skinConcerns", skinConcerns);
		dataMap.put("hairTypes", hairTypes);
		appDTO.setDataMap(dataMap);
		appDTO=beautyQuizService.addBeautyPreference(appDTO);
		

		
		if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
		{
			Customer customer=(Customer)getHttpSession().
			getAttribute(LOGGED_IN_CUSTOMER);
			setInfo_message(appDTO.getInfoMessage(), true);
            if(customer!=null)
			mv.setViewName("redirect:/dashboard.htm#my_pref");
		}
		else
		{
			mv.setViewName("redirect:/quiz.htm");
			setError_message(appDTO.getErrorMessage(), true);
		}
		
		}
		catch(Exception e)
		{

			mv.setViewName("redirect:/quiz.htm");	
			e.printStackTrace();
			logger.debug(e.getMessage());
			appDTO.setErrorMessage(getResourceMessage(something_wrong));
		}
	
		return mv;
	}

	
	
	/* getters and setters*/
	
	public BeautyQuizService getBeautyQuizService() {
		return beautyQuizService;
	}


	public void setBeautyQuizService(BeautyQuizService beautyQuizService) {
		this.beautyQuizService = beautyQuizService;
	}
	
	}
