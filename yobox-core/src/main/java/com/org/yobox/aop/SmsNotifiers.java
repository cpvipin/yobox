package com.org.yobox.aop;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;

import com.org.yobox.common.AppConstants;
import com.org.yobox.common.Validation;
import com.org.yobox.controller.BaseController;
import com.org.yobox.model.CustomerSubscription;
import com.org.yobox.model.PasswordRequestHistory;
import com.org.yobox.notify.SMSSender;
import com.org.yobox.util.CommonUtils;


public class SmsNotifiers implements AfterReturningAdvice, AppConstants {

	private Collection pointCuts;

	private static Logger logger = (Logger) Logger
			.getInstance(EmailNotifier.class);

	/**
	 * This constructor is invoked when the singleton for this class is created.
	 * advices.xml *
	 * 
	 * @param methodNames
	 */
	public SmsNotifiers(List pointCuts) {
		this.pointCuts = pointCuts;
	}

	public SmsNotifiers() {

	}

	
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		if (this.pointCuts.contains(method.getName())) {
			if (CommonUtils.isNormalizeStringEquals(method.getName(),"updateCustomerSubscription")) 
			{
				CustomerSubscription customerSubs=(CustomerSubscription)args[0];
				if(!CommonUtils.isEmpty(customerSubs.getCustomer().getPhone()))
				{
					sendOrderConfirmSms(customerSubs);
					
				}
			}
			 if(CommonUtils.isNormalizeStringEquals(method.getName(),"addPasswordResetToken"))
				{
					PasswordRequestHistory passHis=(PasswordRequestHistory)args[0];
					if(Validation.isValidMobile(passHis.getRequestedUserId()))
					{
					sendPasswordResetSms(passHis);
					}
				}
			
			}
		}
	public void sendPasswordResetSms(PasswordRequestHistory passHis) {


		String mobileNum = passHis.getRequestedUserId();
		if (!CommonUtils.isEmpty(mobileNum)) {
			StringBuffer message = new StringBuffer();
			message.append("Dear Customer , Please click below link to reset your password "
					+ BaseController.getAppSystemProperties(AppConstants.APP_BASE_URL)+"/resetPassword.htm?token="+passHis.getToken()+"&requestedUserId="+mobileNum);
			sendSMS(message.toString(), mobileNum);
		}

	}

	public void sendOrderConfirmSms(CustomerSubscription customerSubs) {

		String mobileNum = customerSubs.getCustomer().getPhone();
		if (!CommonUtils.isEmpty(mobileNum)) {
			StringBuffer message = new StringBuffer();
			message.append("Hi, Thank you for subscribing our beauty box. Your Order is confirmed with order id "
					+ customerSubs.getId());
			sendSMS(message.toString(), mobileNum);
		}
	}

	public void sendSMS(String message, String mobileNum) {
		if (message.length() <= 250) {
			SMSSender.getSoleInstance().sendMessage(mobileNum, message);
		} else {
			int rem = message.length() % 250;
			if (rem > 0) {
				sendSMS(message.substring(0, 250), mobileNum);
				sendSMS(message.substring(251, message.length()), mobileNum);
			} else {
				SMSSender.getSoleInstance().sendMessage(mobileNum, message);
			}
		}

	}

}
