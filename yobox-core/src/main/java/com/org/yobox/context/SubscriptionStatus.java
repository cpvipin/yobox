package com.org.yobox.context;

public enum SubscriptionStatus {
	
		VOID(2),
		FAILURE(3),
		ACTIVE(1),
		INACTIVE(0);
		
		
		private int status;
		
		SubscriptionStatus(int status)
		{
			this.status=status;
		}
		public int getStatus()
		{
			return status;
		}
		
		
	

}
