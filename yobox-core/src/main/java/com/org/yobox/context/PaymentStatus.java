package com.org.yobox.context;

public enum PaymentStatus {
	
		SUCCESS("Success"),
		FAILURE("Failure"),
		ABORTED("Aborted"),
		INVALID("Invalid");
		
		
		private String status;
		
		PaymentStatus(String status)
		{
			this.status=status;
		}
		public String getStatus()
		{
			return status;
		}
		
		
	

}
