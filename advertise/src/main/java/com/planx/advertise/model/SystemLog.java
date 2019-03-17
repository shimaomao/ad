package com.planx.advertise.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "system_log")
public class SystemLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final int RESULT_SUCCESS = 1;
	
	public static final int RESULT_ERROR = 0;
	
    private String description;

    private String invokeMethod;

    private String invokeParams;

    private Integer result;
    
    private String userId;

    private String ipAddress;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInvokeMethod() {
		return invokeMethod;
	}

	public void setInvokeMethod(String invokeMethod) {
		this.invokeMethod = invokeMethod;
	}

	public String getInvokeParams() {
		return invokeParams;
	}

	public void setInvokeParams(String invokeParams) {
		this.invokeParams = invokeParams;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
    
}
