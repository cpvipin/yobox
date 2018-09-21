package com.org.yobox.context;

public enum QuestionType {
	
		MULTIOPTION("multioption"),
		YESORNO("yesorno");
		
		
		private String type;
		
		QuestionType(String type)
		{
			this.type=type;
		}
		public String getType()
		{
			return type;
		}
		
		
	

}
