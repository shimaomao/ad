package com.planx.advertise.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "file_storage")
public class FileStorage extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public final static int STATE_NOT_USED = 0;
	
	public final static int STATE_USED = 1;
	
	@NotBlank(message = "user cannot be null")
	private String userId;

	@NotBlank(message = "uri cannot be empty")
	private String uri;
	
	@JsonIgnore
	private String path;

	private String fileName;
	
	private String contentType;
	
	private Long length;
	
	private String metadata;

	private Integer state;

	public String getContentType() {
		return contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public Long getLength() {
		return length;
	}

	public String getMetadata() {
		return metadata;
	}

	public Integer getState() {
		return state;
	}

	public String getUri() {
		return uri;
	}

	public String getUserId() {
		return userId;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isVideo() {
		return null != contentType && contentType.startsWith("video");
	}
	
	public boolean isImage() {
		return null != contentType && contentType.startsWith("image");
	}
	
	@Override
	public String toString() {
		return "FileStorage [userId=" + userId + ", uri=" + uri + ", fileName=" + fileName + ", contentType=" + contentType
				+ ", length=" + length + ", metadata=" + metadata + ", state=" + state + "]";
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
