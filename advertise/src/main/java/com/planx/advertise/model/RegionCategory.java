package com.planx.advertise.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "ref_region_category")
public class RegionCategory extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "region_id")
	private Region region;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "category_id")
	private Category category;
	
	private BigDecimal toTopFee;
	
	private BigDecimal sponsorFee;
	
	private BigDecimal minFee;

	public RegionCategory() {
		super();
	}

	public RegionCategory(Region region, Category category) {
		this();
		this.region = region;
		this.category = category;
	}
	
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public BigDecimal getToTopFee() {
		return toTopFee;
	}

	public void setToTopFee(BigDecimal toTopFee) {
		this.toTopFee = toTopFee;
	}

	public BigDecimal getSponsorFee() {
		return sponsorFee;
	}

	public void setSponsorFee(BigDecimal sponsorFee) {
		this.sponsorFee = sponsorFee;
	}

	public BigDecimal getMinFee() {
		return minFee;
	}

	public void setMinFee(BigDecimal minFee) {
		this.minFee = minFee;
	}

}
