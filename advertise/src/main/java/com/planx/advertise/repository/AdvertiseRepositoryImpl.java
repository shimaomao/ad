package com.planx.advertise.repository;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.planx.advertise.dto.AdvertiseQueryDTO;
import com.planx.advertise.model.Advertise;

@Component
public class AdvertiseRepositoryImpl implements AdvertiseCustomerRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<Advertise> findAdvertise(AdvertiseQueryDTO advertiseQueryDTO, Pageable pageable) {
		StringBuilder jpql = new StringBuilder();
		StringBuilder countJpql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		jpql.append("select distinct advertise from Advertise advertise "
				+ "left join fetch advertise.user user "
				+ "left join fetch advertise.category category "
				+ "left join advertise.regions region ");
		countJpql.append("select count(distinct advertise) from Advertise advertise "
				+ "left join advertise.user user "
				+ "left join advertise.category category "
				+ "left join advertise.regions region ");
		StringBuilder condition = new StringBuilder("where user.state = 1 ");
		if (StringUtils.hasLength(advertiseQueryDTO.getCategoryId())) {
			condition.append("and category.id = :categoryId ");
			params.put("categoryId", advertiseQueryDTO.getCategoryId());
		}
		if (StringUtils.hasLength(advertiseQueryDTO.getRegionId())) {
			condition.append("and region.id = :regionId ");
			params.put("regionId", advertiseQueryDTO.getRegionId());
		}
		if (null != advertiseQueryDTO.getHasImage()) {
			condition.append("and advertise.hasImage = :hasImage ");
			params.put("hasImage", advertiseQueryDTO.getHasImage());
		}
		if (null != advertiseQueryDTO.getHasVideo()) {
			condition.append("and advertise.hasVideo = :hasVideo ");
			params.put("hasVideo", advertiseQueryDTO.getHasVideo());
		}
		if (null != advertiseQueryDTO.getAdStatus()) {
			condition.append("and advertise.state = :adState ");
			params.put("adState", advertiseQueryDTO.getAdStatus());
		}
		if (StringUtils.hasLength(advertiseQueryDTO.getUserId())) {
			condition.append("and user.id = :userId ");
			params.put("userId", advertiseQueryDTO.getUserId());
		}
		jpql = jpql.append(condition).append("order by advertise.refreshTime desc ");
		countJpql = countJpql.append(condition);
		TypedQuery<Advertise> query = em.createQuery(jpql.toString(), Advertise.class);
		TypedQuery<Long> countQuery = em.createQuery(countJpql.toString(), Long.class);
		params.forEach((key, value) -> {
			query.setParameter(key, value);
			countQuery.setParameter(key, value);
		});
		if (pageable.isPaged()) {
			query.setFirstResult((int) pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}
		return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> countQuery.getSingleResult());
	}

}
