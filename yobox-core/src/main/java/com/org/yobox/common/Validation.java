package com.org.yobox.common;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.org.yobox.util.CommonUtils;
/**
 * @author(name="vipin c p") 
 */
public class Validation {

 private static Pattern pattern;
 private static Matcher matcher;

 private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
   + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 private static final  String ID_PATTERN = "[0-9]+";
 private static final  String STRING_PATTERN = "[a-zA-Z]+";
 private static final  String MOBILE_PATTERN = "[0-9]{10}";
 
 
 public static boolean isValidEmail(String emailId)
 {
 pattern = Pattern.compile(EMAIL_PATTERN);
 matcher = pattern.matcher(emailId);
 if(CommonUtils.isEmpty(emailId))
 {
	 return false;
 }
 if (matcher.matches()) {
  return true;
 }
 return false;
 }
 
 public static boolean isValidMobile(String mobile)
 {
 pattern = Pattern.compile(MOBILE_PATTERN);
 matcher = pattern.matcher(mobile);
 if(CommonUtils.isEmpty(mobile))
 {
	 return false;
 }
 if (matcher.matches()) {
  return true;
 }
 return false;
 }
 
 public static boolean isValidPassword(String password)
 {
	 if(CommonUtils.isEmpty(password))
	 {
		 return false;
	 }
	 else if(password.length()<5)
	 {
		 return false;
	 }
	 return true;
 }




}
