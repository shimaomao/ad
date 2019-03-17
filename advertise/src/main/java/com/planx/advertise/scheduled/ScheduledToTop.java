package com.planx.advertise.scheduled;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.planx.advertise.model.ToTop;
import com.planx.advertise.service.ToTopService;

@Component
public class ScheduledToTop {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ToTopService toTopService;
	
	private Lock lock = new ReentrantLock();
	
	@Scheduled(fixedRate = 300000, initialDelay = 100000)
	public void bumpToTop() {
		log.debug("bump to top start");
		long currentTime = System.currentTimeMillis();
		if (!lock.tryLock()) {
			log.debug("bump to top get lock failed");
			return;
		}
		try {
			List<ToTop> toTopList = toTopService.listValidToTop(currentTime);
			toTopService.bumpToTop(toTopList);
		} catch (Exception e) {
			log.error("bump to top error", e);
		} finally {
			lock.unlock();
		}
    	log.debug("bump to top end");
	}
	
}
