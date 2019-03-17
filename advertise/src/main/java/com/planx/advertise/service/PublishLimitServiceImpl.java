package com.planx.advertise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planx.advertise.model.PublishLimit;

@Service
public class PublishLimitServiceImpl implements PublishLimitService {

	@Autowired
	private PublishLimitManager publishLimitManager;

	private static final long PERIOD = 30 * 60 * 1000L; // 30 mins

	private static final int PUBLISH_LIMIT = 20;

	@Override
	public boolean publishOne(String userId) {
		long now = System.currentTimeMillis();
		PublishLimit publishLimit = publishLimitManager.getPublishLimit(userId);
		
		if (publishLimit == null) {
			publishLimit = new PublishLimit();
			publishLimit.setUserId(userId);
			publishLimit.setRecordTime(System.currentTimeMillis());
			publishLimit.setPublishCount(0);
		} else if (now - publishLimit.getRecordTime() > PERIOD) {
			publishLimit.setRecordTime(System.currentTimeMillis());
			publishLimit.setPublishCount(0);
		}

		if (publishLimit.getPublishCount() >= PUBLISH_LIMIT) {
			return false;
		}
		
		publishLimit.setPublishCount(publishLimit.getPublishCount() + 1);
		publishLimitManager.save(publishLimit);
		return true;
	}

}
