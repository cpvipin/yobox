package com.org.yobox.dao.impl;


import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.org.yobox.AppDTO;
import com.org.yobox.context.ApplicationContext;
import com.org.yobox.context.BeanConstants;
import com.org.yobox.dao.CustomerDao;
import com.org.yobox.dao.DashboardDao;
import com.org.yobox.model.Customer;
import com.org.yobox.model.CustomerSubscription;
import com.org.yobox.model.OrdersProcessed;
import com.org.yobox.model.PasswordRequestHistory;
import com.org.yobox.util.QueryUtils;
import com.org.yobox.util.UniqueResultHibCallback;

public class DashboardDaoImpl extends BaseDaoImpl implements DashboardDao {

	public Collection getCustomerSubscriptions(Customer customer)
	{
		Collection subsColl=Collections.EMPTY_LIST;
		
		subsColl=getHibernateTemplate().findByNamedQueryAndNamedParam("cs.getCustomerOrdersList",
						new String[]{"customerId"},
						new Object[]{ customer.getId()  });
		
		return subsColl;
		
	}
}
