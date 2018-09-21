package com.org.yobox.dao.impl;


import java.util.Map;

import org.hibernate.Session;

import com.org.yobox.AppDTO;
import com.org.yobox.dao.CustomerDao;
import com.org.yobox.model.Customer;
import com.org.yobox.model.PasswordRequestHistory;
import com.org.yobox.util.UniqueResultHibCallback;

public class CustomerDaoImpl extends BaseDaoImpl implements CustomerDao {

	public void resetAllPasswordToken(Customer customer)
	{
		Session session=getSession();
			session.createSQLQuery(
					"update  password_request_history set active_status=0 where customer_id="
							+ customer.getId()).executeUpdate();
		
		
	}
	
	public PasswordRequestHistory getPasswordRequestHistoryByToken(String token)
	{
		
		PasswordRequestHistory passwordRequestHistory=(PasswordRequestHistory)getHibernateTemplate().execute(
				new UniqueResultHibCallback(
						"passHist.getpasswordRequestHistoryByToken", new String[] {
								"token" },
						new Object[] {token }));
		
		return passwordRequestHistory;
		
		
	}
	
	public AppDTO customerSignIn(AppDTO appDTO)
	{
		Map dataMap=appDTO.getDataMap();
		String userId=(String) dataMap.get("USERID");
		String password=(String) dataMap.get("PASSWORD");
		
		Customer customer=(Customer)getHibernateTemplate().execute(
				new UniqueResultHibCallback(
						"cust.authenticateByUserIdPassword", new String[] {
								"userId", "password" },
						new Object[] { userId, password }));
		
			dataMap.put("CUSTOMER", customer);
			appDTO.setDataMap(dataMap);
			
			return appDTO;
	
	}
	

	public Customer getCustomerByUserId(String userId)
	{
		Customer customer=(Customer)getHibernateTemplate().execute(
				new UniqueResultHibCallback(
						"cust.getCustomerByUserId", new String[] {
								"userId"},
						new Object[] { userId }));
		
		return customer;
	}

	
	public Customer getCustomerById(int id)
	{
		return getHibernateTemplate().get(Customer.class, id);
	}
	
	
	public void addPasswordResetToken(PasswordRequestHistory passwordRequestHistory)
	{
		super.add(passwordRequestHistory);
	}
	
	
	

	public boolean isTokenExists(String token)
	{
		
		Long count=(Long)getHibernateTemplate()
		.execute(
			new UniqueResultHibCallback(
				"passHist.isTokenExists",
				new String[] { "token" },
				new Object[] { token }));
		
		if(count.intValue()==0)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean isEmailIdExistWithOtherAccount(String email,int customerId)
	{
		Long count=(Long)getHibernateTemplate()
		.execute(
			new UniqueResultHibCallback(
				"cust.isEmailExistOtherWithAccount",
				new String[] { "email","customerId" },
				new Object[] { email ,customerId}));
		
		if(count.intValue()==0)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean isPhoneExistWithOtherAccount(String phone,int customerId)
	{
		Long count=(Long)getHibernateTemplate()
		.execute(
			new UniqueResultHibCallback(
				"cust.isPhoneExistWithOtherAccount",
				new String[] { "phone" ,"customerId" },
				new Object[] { phone,customerId }));
		
		if(count.intValue()==0)
		{
			return false;
		}
		
		return true;
	}
	
	
	
	
}
