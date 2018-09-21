package com.org.yobox.model;

import java.util.Date;

import com.org.yobox.model.base.AbstractModel;
import com.org.yobox.model.base.BaseModel;



/**
* @hibernate.class table="orders_processed"    
   
*/

public class OrdersProcessed extends AbstractModel {

	private Integer id;
	private String subscriptionName;
	private Customer customer;
	private CustomerAddress customerAddress;
	private CustomerSubscription customerSubscription;
	private String orderStatus;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSubscriptionName() {
		return subscriptionName;
	}
	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public CustomerAddress getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(CustomerAddress customerAddress) {
		this.customerAddress = customerAddress;
	}
	public CustomerSubscription getCustomerSubscription() {
		return customerSubscription;
	}
	public void setCustomerSubscription(CustomerSubscription customerSubscription) {
		this.customerSubscription = customerSubscription;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
	

		
	
}
