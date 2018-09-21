package com.org.yobox.dao;

import java.util.Collection;

import com.org.yobox.AppDTO;
import com.org.yobox.model.Customer;
import com.org.yobox.model.CustomerSubscription;
import com.org.yobox.model.OrdersProcessed;
import com.org.yobox.model.State;
import com.org.yobox.model.SubscriptionCycles;

public interface SubscriptionDao extends BaseDao {
	
public Collection getAllActiveSubscriptionCycles();

public SubscriptionCycles getSubscriptionCycleById(int id);

public State getStateById(int id);

public void updateCustomerSubscription(CustomerSubscription cusSub);

public void generateOrdeForSubscription(OrdersProcessed orderObj);

}
