package com.org.yobox.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ccavenue.security.AesCryptUtil;
import com.org.yobox.AppDTO;
import com.org.yobox.common.ResponseStatus;
import com.org.yobox.context.OrderStatus;
import com.org.yobox.context.PaymentConstants;
import com.org.yobox.context.PaymentStatus;
import com.org.yobox.context.SubscriptionStatus;
import com.org.yobox.context.TransactionManager;
import com.org.yobox.dao.SubscriptionDao;
import com.org.yobox.model.Customer;
import com.org.yobox.model.CustomerSubscription;
import com.org.yobox.model.OrdersProcessed;
import com.org.yobox.service.PaymentService;
import com.org.yobox.util.AppUtils;
import com.org.yobox.util.CommonUtils;
import com.org.yobox.util.DateUtils;

public class PaymentServiceImpl extends BaseServiceImpl implements
PaymentService,PaymentConstants {
	
	
	private SubscriptionDao subscriptionDao;
	
	
	public AppDTO initializePayment(AppDTO appDTO)
	{
		Map dataMap=appDTO.getDataMap();
		try{
		CustomerSubscription subsObj=(CustomerSubscription)getHttpSession().getAttribute(SUBSCRIPTION_IN_SESSION);
		Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
		
		
		if(subsObj!=null)
		{
			StringBuffer pgUrl=new StringBuffer();
	        pgUrl.append("&tid=100&merchant_id="+PG_MERCHANT_ID);
	        pgUrl.append("&order_id="+subsObj.getId());
	        pgUrl.append("&currency="+PG_CURRENCY);
	        pgUrl.append("&amount="+subsObj.getSubscriptionCycle().getPrice());
	        pgUrl.append("&redirect_url="+PG_REDIRECT_URL);
	        pgUrl.append("&cancel_url="+PG_CANCEL_URL);
	        pgUrl.append("&language="+PG_LANGUAGE);
	        pgUrl.append("&billing_name="+subsObj.getCustomerAddress().getFullName());
	        pgUrl.append("&billing_address="+subsObj.getCustomerAddress().getAddress());
	        pgUrl.append("&billing_tel="+subsObj.getCustomerAddress().getMobile());
	        pgUrl.append("&billing_city="+subsObj.getCustomerAddress().getLocality());
	        pgUrl.append("&billing_state="+subsObj.getCustomerAddress().getState().getName());
	        pgUrl.append("&billing_zip="+subsObj.getCustomerAddress().getPinCode());
	        pgUrl.append("&billing_country=India");
	        pgUrl.append("&billing_email="+subsObj.getCustomerAddress().getCustomer().getEmail());
	        AesCryptUtil aesUtil = new AesCryptUtil(PG_WORKING_KEY);
	        String encRequest = aesUtil.encrypt(pgUrl.toString());
	        String url=PG_URL+encRequest+"&access_code="+PG_ACCESS_CODE;
	        dataMap.put("pgUrl",PG_URL);
	        dataMap.put("encRequest",encRequest);
	        dataMap.put("accessCode",PG_ACCESS_CODE);
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			TransactionManager.rollback();
			appDTO.setResponseStatus(ResponseStatus.ERROR);
		
		}
		

		return appDTO;
	}
	/*
	 * (non-Javadoc)
	 * @see com.org.yobox.service.PaymentService#finalizePayment(com.org.yobox.AppDTO)
	 * 
	 * If the payment is success then create an order with the status pending on it.
	 * Also update the subscription status. There can be multiple orders for one subscription since
	 * subscription can be done for more than one month
	 */
	public AppDTO finalizePayment(AppDTO appDTO)
	{
		Map dataMap=appDTO.getDataMap();
		try{
		
			String encResp=(String)dataMap.get("encResp");
			AesCryptUtil aesUtil=new AesCryptUtil(PG_WORKING_KEY);
			String decResp = aesUtil.decrypt(encResp);
			StringTokenizer tokenizer = new StringTokenizer(decResp, "&");
			Hashtable hs=new Hashtable();
			String pair=null, pname=null, pvalue=null;
			while (tokenizer.hasMoreTokens()) {
				pair = (String)tokenizer.nextToken();
				if(pair!=null) {
					StringTokenizer strTok=new StringTokenizer(pair, "=");
					pname=""; pvalue="";
					if(strTok.hasMoreTokens()) {
						pname=(String)strTok.nextToken();
						if(strTok.hasMoreTokens())
							pvalue=(String)strTok.nextToken();
						hs.put(pname, pvalue);
					}
				}
			}
			
			
			String paymentStatus=(String)hs.get("order_status");
			int orderId=Integer.parseInt((String)hs.get("order_id"));
			
			CustomerSubscription subsObj=(CustomerSubscription)getHttpSession().getAttribute(SUBSCRIPTION_IN_SESSION);
			Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
		
			TransactionManager.begin();
			if(paymentStatus.equals(PaymentStatus.SUCCESS.getStatus()))
			{
				Date today=DateUtils.getCurrentDate();
				subsObj.setSubscriptionStatus(SubscriptionStatus.ACTIVE.getStatus());
				subsObj.setPaymentStatus(paymentStatus);
				
				String subsName=AppUtils.getSubscriptionName()+" Box";
				OrdersProcessed orderObj=new OrdersProcessed();
				orderObj.setSubscriptionName(subsName);
				orderObj.setOrderStatus(OrderStatus.PENDING.getStatus());
				orderObj.setCustomer(customer);
				orderObj.setCustomerSubscription(subsObj);
				// address can be changed for order from the address indexed at the time of subs
				orderObj.setCustomerAddress(subsObj.getCustomerAddress());
				orderObj.setDateAdded(today);
				orderObj.setDateModified(today);
				subscriptionDao.generateOrdeForSubscription(orderObj);
				
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);
				appDTO.setInfoMessage(getResourceMessage(SUBSCRIPTION_SUCCESS));
			}
			else
			{
				subsObj.setSubscriptionStatus(SubscriptionStatus.VOID.getStatus());
				subsObj.setPaymentStatus(paymentStatus);
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage(PAYMENT_INCOMPLETE));
			}
			
			subsObj.setPgResponse(decResp);
			subscriptionDao.updateCustomerSubscription(subsObj);
			getHttpSession().removeAttribute(SUBSCRIPTION_IN_SESSION);
			TransactionManager.commit();
		}
		catch(Exception e)
		{
			TransactionManager.rollback();
			appDTO.setErrorMessage(getResourceMessage(PAYMENT_INCOMPLETE));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			e.printStackTrace();
		}

		return appDTO;
	}

	public SubscriptionDao getSubscriptionDao() {
		return subscriptionDao;
	}

	public void setSubscriptionDao(SubscriptionDao subscriptionDao) {
		this.subscriptionDao = subscriptionDao;
	}
	
	
	
	
	

}
