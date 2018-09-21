package com.org.yobox.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.org.yobox.AppDTO;

public interface BeautyQuizService extends BaseService {
	
	
	public AppDTO addBeautyPreference(AppDTO appDTO);
	
	
}
