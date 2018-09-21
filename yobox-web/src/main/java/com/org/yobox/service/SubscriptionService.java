package com.org.yobox.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.org.yobox.AppDTO;

public interface SubscriptionService extends BaseService {
	
	
	public AppDTO listSubscriptionCycles(AppDTO appDTO);
	
	
	public AppDTO populateAddressPrerequisites(AppDTO appDTO);
	
	public AppDTO addSubscription(AppDTO appDTO);
	
}
