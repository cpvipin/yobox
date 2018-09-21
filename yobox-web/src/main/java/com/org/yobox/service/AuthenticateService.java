package com.org.yobox.service;


import com.org.yobox.AppDTO;


public interface AuthenticateService  extends BaseService {
	
	public AppDTO signin(AppDTO appDTO);
	
	public AppDTO signup(AppDTO appDTO);
	
	public AppDTO generatePasswordLink(AppDTO appDTO);
	
	public AppDTO validatePasswordToken(AppDTO appDTO);
	
	public AppDTO updatePasswordByToken(AppDTO appDTO);
	
	
	public AppDTO subscribeNewsLetter(AppDTO appDTO);
		
}
