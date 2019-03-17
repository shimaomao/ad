package com.planx.advertise.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.planx.advertise.model.PublishLimit;
import com.planx.advertise.system.exception.ApplicationException;

@Service
public class PublishLimitManager {

	private Map<String, PublishLimit> publishLimits = new ConcurrentHashMap<>();
	
	public PublishLimit getPublishLimit(String userId) {
		return publishLimits.get(userId);	
	}
	
	public void save(PublishLimit publishLimit) {
		if (publishLimit == null) {
			throw new ApplicationException("publishLimit cannot be null");
		}
		publishLimits.put(publishLimit.getUserId(), publishLimit);
	}
			
}
