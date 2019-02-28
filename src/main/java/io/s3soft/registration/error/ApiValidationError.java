package io.s3soft.registration.error;

public class ApiValidationError extends ApiSubError {
	private String object;
	private String property;
	private Object rejectedValue;
	private String message;
	
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Object getRejectedValue() {
		return rejectedValue;
	}
	public void setRejectedValue(String rejectedValue) {
		this.rejectedValue = rejectedValue;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ApiValidationError(String object, String property, Object rejectedValue, String message) {
		super();
		this.object = object;
		this.property = property;
		this.rejectedValue = rejectedValue;
		this.message = message;
	}
	public ApiValidationError() {
		super();
	}
	public void setRejectedValue(Object rejectedValue) {
		this.rejectedValue = rejectedValue;
	}
	@Override
	public String toString() {
		return "ApiValidationError [object=" + object + ", property=" + property + ", rejectedValue=" + rejectedValue
				+ ", message=" + message + "]";
	}
	
}
