package com.org.yobox.dao.impl;


import java.util.Collection;

import com.org.yobox.context.TransactionManager;
import com.org.yobox.dao.SubscriptionDao;
import com.org.yobox.model.CustomerSubscription;
import com.org.yobox.model.OrdersProcessed;
import com.org.yobox.model.State;
import com.org.yobox.model.SubscriptionCycles;

public class SubscriptionDaoImpl extends BaseDaoImpl implements SubscriptionDao {
	

	public Collection getAllActiveSubscriptionCycles()
	{
		
		Collection subsColl=getHibernateTemplate().findByNamedQueryAndNamedParam(
				"subs.getActiveSubscriptionCycles",
				new String[] { "activeStatus" },
				new Object[] { true });
		
		return subsColl;
		
	}
	
	
	public SubscriptionCycles getSubscriptionCycleById(int id)
	{
		
		return getHibernateTemplate().get(SubscriptionCycles.class, id);
	}
	
	public State getStateById(int id)
	{

		return getHibernateTemplate().get(State.class, id);
	}
	
	
	public void updateCustomerSubscription(CustomerSubscription cusSub)
	{
		TransactionManager.getInstance().txUpdate(cusSub);
	}
	
	public void generateOrdeForSubscription(OrdersProcessed orderObj)
	{
		TransactionManager.getInstance().txCreate(orderObj);
	}
}
