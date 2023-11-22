package com.example.springboot.components;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ControllerLogger {
	private static final Logger LOG = LoggerFactory.getLogger(ControllerLogger.class);

	@Pointcut("execution(public * com.example.springboot..*(..))")
	private void allControllerMethods() {}

	@Around("allControllerMethods()")
	public Object logTimeTaken(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		LOG.info(pjp.getSignature().toShortString() + ": Called (" + Arrays.toString(pjp.getArgs()) + ") ");
		try {
			Object result = pjp.proceed();
			long dt = System.currentTimeMillis() - startTime;
			LOG.info(pjp.getSignature().toShortString() + ": Returned {} ({}ms)", result, dt);
			return result;
		} catch (Throwable e) {
			long dt = System.currentTimeMillis() - startTime;
			LOG.warn(pjp.getSignature().toShortString() + ": Exception from {} ({}ms)", e.getMessage(), dt);
			throw e;
		}
	}
}
