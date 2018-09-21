package com.org.yobox.model;

import java.math.BigDecimal;
import java.util.Date;

import com.org.yobox.model.base.AbstractModel;
import com.org.yobox.model.base.BaseModel;


/**
* @hibernate.class table="subscription_cycles"    
* 
@hibernate.query name="subs.listActiveSubscriptions" 
query="from SubscriptionCycles as subs  where subs.activeStatus=:activeStatus
"                
*/
public class SubscriptionCycles extends BaseModel {

	private Integer id;
	private String name;
	private String displayText;
	private int durationMonths;	
	private String description;	
	private BigDecimal price;	
	private boolean activeStatus;
	
	/* getters and setters*/
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayText() {
		return displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	public int getDurationMonths() {
		return durationMonths;
	}
	public void setDurationMonths(int durationMonths) {
		this.durationMonths = durationMonths;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
		
	
}
