package com.org.yobox.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.org.yobox.AppDTO;

public interface DashboardService extends BaseService {
	
	
	public AppDTO populateDashboardElements(AppDTO appDTO);
	
	public AppDTO updateCustomerProfile(AppDTO appDTO);
	
	public AppDTO updateProfilePassword(AppDTO appDTO);
}
