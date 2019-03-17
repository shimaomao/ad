package com.planx.advertise.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "advertise")
public class Advertise extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final int STATE_AD_INIT = 0;
	
	public static final int STATE_AD_ACTIVE = 1;
	
	public static final int STATE_AD_DISABLE_BY_ADMIN = -1;
	
	public static final int STATE_AD_DISABLE_BY_USER = -2;
	
	@NotNull(message = "user cannot be null")
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany
	@JoinTable(name = "ref_region_advertise", joinColumns = @JoinColumn(name = "advertise_id"), inverseJoinColumns = @JoinColumn(name = "region_id"))
	private List<Region> regions = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToMany
	@JoinTable(name = "ref_advertise_file", joinColumns = @JoinColumn(name = "advertise_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
	private List<FileStorage> medias = new ArrayList<>();

	private String title;

	private String content;

	private Integer age;

	private String location;
	
	private Long expireTime;

	private Long refreshTime;
	
	private String timeZone;

	private Boolean hasImage;
	
	private Boolean hasVideo;
	
	private Integer state;

	public Integer getAge() {
		return age;
	}

	public String getContent() {
		return content;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public String getLocation() {
		return location;
	}

	public List<FileStorage> getMedias() {
		return medias;
	}

	public Long getRefreshTime() {
		return refreshTime;
	}

	public Integer getState() {
		return state;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public String getTitle() {
		return title;
	}

	public User getUser() {
		return user;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setMedias(List<FileStorage> medias) {
		this.medias = medias;
	}

	public void setRefreshTime(Long refreshTime) {
		this.refreshTime = refreshTime;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Boolean getHasImage() {
		return hasImage;
	}

	public void setHasImage(Boolean hasImage) {
		this.hasImage = hasImage;
	}

	public Boolean getHasVideo() {
		return hasVideo;
	}

	public void setHasVideo(Boolean hasVideo) {
		this.hasVideo = hasVideo;
	}

	@Override
	public String toString() {
		return "Advertise [user=" + user + ", regions=" + regions + ", category=" + category + ", medias=" + medias
				+ ", title=" + title + ", content=" + content + ", age=" + age + ", location="
				+ location + ", expireTime=" + expireTime + ", refreshTime=" + refreshTime + ", timeZone=" + timeZone
				+ ", hasImage=" + hasImage + ", hasVideo=" + hasVideo + ", state=" + state + "]";
	}
	
}
