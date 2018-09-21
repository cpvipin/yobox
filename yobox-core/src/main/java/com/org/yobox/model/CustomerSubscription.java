package com.org.yobox.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.org.yobox.model.base.AbstractModel;

/**
* @hibernate.class table="orders_processed"    
* 
@hibernate.query name="op.getCustomerOrdersList" 
query="select cs.id from CustomerSubscription as cs inner join  cs.ordersProcessed as op where cs.customer.id=:customerId
"    
*/

public class CustomerSubscription extends AbstractModel {

	private Integer id;
	private Customer customer;
	private CustomerAddress customerAddress;
	private SubscriptionCycles subscriptionCycle;
	private String paymentStatus;
	private String pgResponse;
	private int subscriptionStatus;
	private BigDecimal totalAmount;	
	private Date startDate;
	private Date endDate;
	
	
	private Set ordersProcessed;

	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public SubscriptionCycles getSubscriptionCycle() {
		return subscriptionCycle;
	}
	public void setSubscriptionCycle(SubscriptionCycles subscriptionCycle) {
		this.subscriptionCycle = subscriptionCycle;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPgResponse() {
		return pgResponse;
	}
	public void setPgResponse(String pgResponse) {
		this.pgResponse = pgResponse;
	}
	public int getSubscriptionStatus() {
		return subscriptionStatus;
	}
	public void setSubscriptionStatus(int subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Set getOrdersProcessed() {
		return ordersProcessed;
	}
	public void setOrdersProcessed(Set ordersProcessed) {
		this.ordersProcessed = ordersProcessed;
	}
	
	
}