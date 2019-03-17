package com.planx.advertise.system.log;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planx.advertise.model.SystemLog;
import com.planx.advertise.model.User;
import com.planx.advertise.repository.SystemLogRepository;
import com.planx.advertise.system.config.SecurityUserDetailsService;

@Aspect
@Component
@Order(1)
public class OperationAspect {
	
	@Autowired
	private SystemLogRepository systemLogRepository;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;
	
    @After("within(com.planx..*) && @annotation(operationLog)")
    public void afterReturning(JoinPoint joinPoint, OperationLog operationLog) {
    	SystemLog systemLog = genSystemLog(joinPoint, operationLog);
        systemLog.setResult(SystemLog.RESULT_SUCCESS);
        systemLogRepository.save(systemLog);
    }

    @AfterThrowing("within(com.planx..*) && @annotation(operationLog)")
    public void afterThrowing(JoinPoint joinPoint, OperationLog operationLog) {
    	SystemLog systemLog = genSystemLog(joinPoint, operationLog);
        systemLog.setResult(SystemLog.RESULT_ERROR);
        systemLogRepository.save(systemLog);
    }
    
    private SystemLog genSystemLog(JoinPoint joinPoint, OperationLog operationLog) {
    	long now = System.currentTimeMillis();
    	String className = joinPoint.getTarget().getClass().getName();
    	String methodName = joinPoint.getSignature().getName();
    	Object[] args = joinPoint.getArgs();
    	ObjectMapper mapper = new ObjectMapper();
        SystemLog systemLog = new SystemLog();
        systemLog.setCreateTime(now);
        systemLog.setDescription(operationLog.description());
        systemLog.setInvokeMethod(className + "." + methodName);
        if (null != request) {
        	systemLog.setIpAddress(getRemoteIp(request));
        }
        if (null != securityUserDetailsService) {
        	User user = securityUserDetailsService.currentUser();
        	if (null != user) {
        		systemLog.setUserId(user.getId());
        	}
        }
        try {
			systemLog.setInvokeParams(mapper.writeValueAsString(args));
		} catch (JsonProcessingException e) {
			systemLog.setInvokeParams("writeValueAsString failed");
		}
        return systemLog;
    }
    
    private String getRemoteIp(HttpServletRequest request) {
    	String remoteAddr = request.getHeader("X-Forwarded-For");
        if (null == remoteAddr || "".equals(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        }
    	return remoteAddr;
    }
}
