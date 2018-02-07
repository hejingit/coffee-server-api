package com.coffee.api.core.bean;

import com.coffee.api.util.ToStringBuilder;

public class CoffeeResponse<T> {
	
	public static final String  SUCCESS="000";
	public static final String  ERROR="001";
	
	
	 private T content;
	 
	 private String result="";
	 private String errorMsg="";

	    public T getContent() {
	        return content;
	    }

	    public void setContent(T content) {
	        this.content = content;
	    }

		/**
		 * @return the result
		 */
		public String getResult() {
			return result;
		}

		/**
		 * @param result the result to set
		 */
		public void setResult(String result) {
			this.result = result;
		}

		/**
		 * @return the errorMsg
		 */
		public String getErrorMsg() {
			return errorMsg;
		}

		/**
		 * @param errorMsg the errorMsg to set
		 */
		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}

		
	
	
		  public String toString() {
		        return ToStringBuilder.toString(this);
		    }

}
