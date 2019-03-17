package com.planx.advertise.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "user_token")
public class UserToken extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final int TYPE_REGISTER = 1;
	
	public static final int TYPE_RESET_PASSWORD = 2;
	
	public static final int STATE_NOT_USED = 0;
	
	public static final int STATE_USED = 1;

	private String userId;
	
	private String token;
	
	private Integer type;
	
	private Integer state;
	
	private Long expireTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public String toString() {
		return "UserToken [userId=" + userId + ", token=" + token + ", type=" + type + ", state=" + state
				+ ", expireTime=" + expireTime + "]";
	}
	
}
