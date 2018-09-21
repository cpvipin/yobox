package com.org.yobox.context;

public enum OrderStatus {
	
		PENDING("pending"),
		PROCESSING("processing"),
		PACKED("packed"),
		SHIPPED("shipped"),
		DELIVERED("delivered"),
		CANCELLED("cancelled")
		;
		
		
		private String status;
		
		OrderStatus(String status)
		{
			this.status=status;
		}
		public String getStatus()
		{
			return status;
		}
		
		
	

}
