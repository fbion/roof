package org.roof.spring;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

/**
 * Spring 容器启动成功后，将容器设置为当前容器
 * 
 * @author liuxin
 *
 */
public class InjectionApplicationContextListener implements ApplicationListener<ContextStartedEvent> {
	private static final Logger LOG = Logger.getLogger(InjectionApplicationContextListener.class);

	@Override
	public void onApplicationEvent(ContextStartedEvent event) {
		CurrentSpringContext.setCurrentContext(event.getApplicationContext());
		if (LOG.isInfoEnabled()) {
			LOG.info("Spring 容器注入 CurrentSpringContext 成功");
		}

	}

}
