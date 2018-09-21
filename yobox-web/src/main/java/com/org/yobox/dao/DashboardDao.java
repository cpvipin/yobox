package com.org.yobox.dao;

import java.util.Collection;

import com.org.yobox.AppDTO;
import com.org.yobox.model.Customer;
import com.org.yobox.model.CustomerSubscription;
import com.org.yobox.model.State;
import com.org.yobox.model.SubscriptionCycles;

public interface DashboardDao extends BaseDao {
	
public Collection getCustomerSubscriptions(Customer customer);


}
