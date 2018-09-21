package com.org.yobox.model;

import java.util.Date;

import com.org.yobox.model.base.AbstractModel;


public class NewsLetter extends AbstractModel {

	private Integer id;
	private String email;	
	private boolean activeStatus;
	
	/*getters and setters*/
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	

	
	
}
