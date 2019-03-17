package com.planx.advertise.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "sponsor")
public class Sponsor extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final int TYPE_HTML_TAG = 1;

	@NotBlank(message = "advertise cannot be null")
	private String advertiseId;

	private Integer term;

	private Integer htmlTag;

	private Long expireTime;

	public String getAdvertiseId() {
		return advertiseId;
	}

	public void setAdvertiseId(String advertiseId) {
		this.advertiseId = advertiseId;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getHmlTag() {
		return htmlTag;
	}

	public void setHtmlTag(Integer htmlTag) {
		this.htmlTag = htmlTag;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public String toString() {
		return "Sponsor [advertiseId=" + advertiseId + ", term=" + term + ", htmlTag=" + htmlTag + ", expireTime="
				+ expireTime + "]";
	}

}
