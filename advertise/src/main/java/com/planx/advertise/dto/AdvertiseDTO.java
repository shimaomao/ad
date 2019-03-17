package com.planx.advertise.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import com.planx.advertise.model.Advertise;

public class AdvertiseDTO {

	@NotBlank(message = "Title cannot be empty")
	@Length(max = 100, message = "Title too long.")
	private String title;

	@NotBlank(message = "Content cannot be empty")
	@Length(max = 1000, message = "Content too long.")
	private String content;

	@Min(value = 21, message = "Age cannot be under 21.")
	private Integer age;

	@Length(max = 45, message = "Location too long.")
	private String location;

	private String videoId;

	@Size(max = 12, message = "Too many images.")
	private List<String> imgIds = new ArrayList<>();

	public Advertise convertToAdvertise(Advertise existAdvertise) {
		Advertise advertise = new Advertise();
		if (null != existAdvertise) {
			BeanUtils.copyProperties(existAdvertise, advertise);
		}
		advertise.setTitle(title);
		advertise.setContent(content);
		advertise.setAge(age);
		advertise.setLocation(location);
		return advertise;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public List<String> getImgIds() {
		return imgIds;
	}

	public void setImgIds(List<String> imgIds) {
		this.imgIds = imgIds;
	}

	@Override
	public String toString() {
		return "AdvertiseDTO [title=" + title + ", content=" + content + ", age=" + age
				+ ", location=" + location + ", videoId=" + videoId + ", imgIds=" + imgIds + "]";
	}

}
