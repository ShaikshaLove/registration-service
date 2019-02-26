package io.s3soft.registration.error;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author shaiksha
 *
 */
public class ApiError {

	private HttpStatus  httpStatus;
	private String message;
	private List<ApiSubError> errors;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<ApiSubError> getErrors() {
		return errors;
	}
	public void setErrors(List<ApiSubError> errors) {
		this.errors = errors;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "ApiError [httpStatus=" + httpStatus + ", message=" + message + ", errors=" + errors + ", timestamp="
				+ timestamp + "]";
	}
	public ApiError(HttpStatus httpStatus, String message, List<ApiSubError> errors) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
		this.errors = errors;
		timestamp=LocalDateTime.now();

	}
	public ApiError() {
		super();
		timestamp=LocalDateTime.now();
	}
}
