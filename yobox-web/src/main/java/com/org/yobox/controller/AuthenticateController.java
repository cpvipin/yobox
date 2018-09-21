package com.org.yobox.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.org.yobox.AppDTO;
import com.org.yobox.common.ProcessingException;
import com.org.yobox.common.ResponseStatus;
import com.org.yobox.common.Validation;
import com.org.yobox.model.Customer;
import com.org.yobox.model.PasswordRequestHistory;
import com.org.yobox.service.AuthenticateService;
import com.org.yobox.util.CommonUtils;
import com.org.yobox.util.PasswordEncryptor;


@Controller
public class AuthenticateController extends BaseController {
	
	@Autowired
	private AuthenticateService authenticateService;

	
	@RequestMapping(value="/newsletter")
	public String newsletter(HttpServletRequest request)
	{
		
	try{
		
		String email=request.getParameter("email");
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		dataMap.put("email",email);
		appDTO.setDataMap(dataMap);
		appDTO=authenticateService.subscribeNewsLetter(appDTO);
		if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
		{
			setInfo_message(appDTO.getInfoMessage(),true);
		}
			
	}
		catch(Exception ex)
		{
			ex.printStackTrace();
			//Exception comes when newsletter already subscribed by this email id. This can be ignored.
			setInfo_message(getResourceMessage(NEWSLETTER_SUBSCRIBED), true);
		}
		
		return "redirect:/index.htm";
	}	
	/*
	 * If user manually typed signup url they should be redirected to quiz when not logged in ,
	 *  if logged in redirect to subscription. 
	 */
	@RequestMapping(value="/signup")
	public String signup(HttpServletRequest request)
	{
		try{
	String beautyPref=(String)getHttpSession().getAttribute(BEAUTY_PREFERENCE_SESS);
	Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
	
	if(customer!=null)
	{
		return "redirect:/chooseSubscription.htm";
	}
	if(CommonUtils.isEmpty(beautyPref))
	{
		setInfo_message(getResourceMessage(COMPLETE_QUIZ), true);
		return "redirect:/quiz.htm";
	}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
		return "signup";
		
	}
	
	
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request)
	{
		try{
		Customer customer=(Customer)getHttpSession().
		getAttribute(LOGGED_IN_CUSTOMER);

		if(customer!=null)
		{
			return "redirect:/chooseSubscription.htm";
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}

		return "login";
		
	}
	
	
		/*
	 * Let the user logged into the app by using the credentials passed.
	 */
	@RequestMapping(value="/dologin", method = RequestMethod.POST)
	public ModelAndView signIn(HttpServletRequest request)
	{
			AppDTO appDTO=new AppDTO();
			Map dataMap=new HashMap();
			ModelAndView mv=new ModelAndView("redirect:/login.htm");
			try{
			String userId=request.getParameter("userId");
			String password= request.getParameter("password");
			if((!CommonUtils.isEmpty(userId)&&!CommonUtils.isEmpty(password)))
			{
			
				dataMap.put("USERID",userId);
				dataMap.put("PASSWORD",PasswordEncryptor.encrypt(password));
				appDTO.setDataMap(dataMap);
				appDTO=authenticateService.signin(appDTO);
				
				if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
				{
					mv.setViewName("redirect:/chooseSubscription.htm");  
					
				}
				else
				{
					setError_message(appDTO.getErrorMessage(), true);
				}
				
			}
			else
			{
			setError_message(getResourceMessage(ALL_FIELDS_REQUIRED), true);
			}
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
				setError_message(getResourceMessage(something_wrong),true);
			}
			return mv;
	}
	
	/*
	 * user sign up using details
	 */
	@RequestMapping(value="/doSignup", method = RequestMethod.POST)
	public ModelAndView doSignUp(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView("redirect:/signup.htm");
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		try{
			String age=request.getParameter("age");
			String userId=request.getParameter("userId");
			String password= request.getParameter("password");
			
			if(CommonUtils.isEmpty(age)||CommonUtils.isEmpty(userId)||CommonUtils.isEmpty(password))
			{
				setError_message(getResourceMessage(ALL_FIELDS_REQUIRED), true);
				
			}
			else if(!Validation.isValidPassword(password))
			{
				setError_message(getResourceMessage(WEAK_PASSWORD), true);
				
			}
			else if(!(Validation.isValidEmail(userId)||Validation.isValidMobile(userId)))
			{
				setError_message(getResourceMessage(ENTER_VALID_EMAIL_OR_MOBILE), true);
				
			}
			else
			{
				dataMap.put("USERID",userId);
				dataMap.put("PASSWORD",PasswordEncryptor.encrypt(password));
				appDTO.setDataMap(dataMap);
				appDTO=authenticateService.signup(appDTO);
				if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
				{
					mv.setViewName("redirect:/chooseSubscription.htm");
				}
				else if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
					
				{
					setError_message(getResourceMessage(USER_ALREADY_EXIST), true);
				}
			}
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			setError_message(getResourceMessage(something_wrong), true);
		}
		return mv;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response) {
		
		ModelAndView mv=new ModelAndView("redirect:/index.htm");
		getHttpSession().setAttribute(LOGGED_IN_CUSTOMER, null);
		getHttpSession().invalidate();
		return mv;
		}
	
	

	
	@RequestMapping(value="/forgotPassword")
	public String forgotPassword(HttpServletRequest request) {
		try{
			Customer customer=(Customer) getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
			String userId=request.getParameter("userId");
			
			if(customer!=null)
			{
				return "redirect:/dashboard.htm";
			}
			if(!CommonUtils.isEmpty(userId))
			{
			AppDTO appDTO=new AppDTO();
			Map dataMap=new HashMap();
			dataMap.put("userId",userId);
			appDTO.setDataMap(dataMap);
			appDTO=authenticateService.generatePasswordLink(appDTO);
			if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
			{
			setInfo_message(appDTO.getInfoMessage(), false);
			}
			else
			{
				setError_message(appDTO.getErrorMessage(), false);
			}
			}
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			setError_message(getResourceMessage(something_wrong),false);
		}
		return "forgotPassword";
	}
	
	@RequestMapping(value="/resetPassword", method = RequestMethod.GET)
	public ModelAndView resetPassword(@ModelAttribute PasswordRequestHistory passwordRequestHistory,HttpServletRequest request) {
		
		ModelAndView mv=new ModelAndView("resetPassword");
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		
		
		try{
		if((CommonUtils.isEmpty(passwordRequestHistory.getRequestedUserId()) ||  CommonUtils.isEmpty(passwordRequestHistory.getToken())) )
		{
			mv.setViewName("redirect:/forgotPassword.htm");
			setError_message(getResourceMessage(PASSWORD_LINK_EXPIRED), true);
			return mv;
		}
		dataMap.put("token",passwordRequestHistory.getToken());
		dataMap.put("userId",passwordRequestHistory.getRequestedUserId());
		appDTO.setDataMap(dataMap);
		appDTO=authenticateService.validatePasswordToken(appDTO);
		if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
		{
			setError_message(appDTO.getErrorMessage(), true);
			mv.setViewName("redirect:/forgotPassword.htm");
			
		}
		else
		{
			mv.addAllObjects(dataMap);
			setInfo_message(appDTO.getInfoMessage(),false);
		}
		}
		catch(Exception e)
		{
			mv.setViewName("redirect:/forgotPassword.htm");
			setError_message(getResourceMessage(PASSWORD_LINK_EXPIRED), true);
			e.printStackTrace();
		}
		
		return mv;
		
	}
	
	
	/*
	 * This method is only for updating password via forgot password. 
	 * Updating the password from user profile is
	 * in dashboard controller
	 */
	@RequestMapping(value="/updatePassword",method = RequestMethod.POST)
	public ModelAndView updatePassword(@ModelAttribute PasswordRequestHistory passwordRequestHistory,HttpServletRequest request) {
		ModelAndView mv=new ModelAndView("resetPassword");
		try{
	
			AppDTO appDTO=new AppDTO();
			Map dataMap=new HashMap();
			String password=request.getParameter("password");
			String confirmPassword=request.getParameter("confirmPassword");
			
			dataMap.put("passwordRequestHistory", passwordRequestHistory);
			dataMap.put("password", password);
			if(!password.equals(confirmPassword))
			{
			setError_message(getResourceMessage(PASSWORD_DONOT_MATCH), false);
			return mv;
			}
			else if(!Validation.isValidPassword(password))
			{
				setError_message(getResourceMessage(WEAK_PASSWORD), true);
			return mv;	
			}
			appDTO.setDataMap(dataMap);
			appDTO=authenticateService.updatePasswordByToken(appDTO);
			if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
			{
				setInfo_message(appDTO.getInfoMessage(), true);
				mv.setViewName("redirect:/login.htm");
			}
			else
			{
				setError_message(appDTO.getErrorMessage(), false);
			}
			
		}
		catch(Exception e)
		{
			setError_message(getResourceMessage(something_wrong), false);
			e.printStackTrace();
		}
		
		return mv;
	}
	
	

	/* getters and setters */

	
	
	public AuthenticateService getAuthenticateService() {
		return authenticateService;
	}


	public void setAuthenticateService(AuthenticateService authenticateService) {
		this.authenticateService = authenticateService;
	}
	
		
	/* methods */
	
	
	
}
