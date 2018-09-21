package com.org.yobox.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.org.yobox.AppDTO;

public interface PaymentService  extends BaseService {
	
	public AppDTO initializePayment(AppDTO appDTO);
	
	public AppDTO finalizePayment(AppDTO appDTO);
}
