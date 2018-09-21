package com.org.yobox.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handleError405(HttpServletRequest request, Exception e)   {
            ModelAndView mav = new ModelAndView("404");
            mav.addObject("exception", e);  
            //mav.addObject("errorcode", "405");
            return mav;
      }
    
}