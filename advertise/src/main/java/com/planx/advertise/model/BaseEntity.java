package com.planx.advertise.model;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "system-uuid")
	@Id
	private String id;

	@CreatedDate
	private Long createTime;

	@LastModifiedDate
	private Long updateTime;

	private Long deleteTime = 0L;

	public Long getCreateTime() {
		return createTime;
	}

	public Long getDeleteTime() {
		return deleteTime;
	}

	public String getId() {
		return id;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public void setDeleteTime(Long deleteTime) {
		this.deleteTime = deleteTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

}
