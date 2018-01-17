package org.roof.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * 获得当前的SpringContext工具<br />
 * web项目中会获得当前线程的SpringConext<br />
 * 或者将Bean实现{@link org.springframework.context.ApplicationContextAware}
 * 接口来获得当前线程的 ApplicationContext
 * 
 * @author liuxin
 * 
 * @see org.seek.roof.spring.WebCurrentSpringContextListener
 * 
 * 
 */
public class CurrentSpringContext {

	private static ApplicationContext CONTEXT;

	public static ApplicationContext getCurrentContext() {
		return CONTEXT;
	}

	public static void setCurrentContext(ApplicationContext context) {
		CONTEXT = context;
	}

	private static void initContext() {
		if (CONTEXT == null) {
			CONTEXT = ContextLoader.getCurrentWebApplicationContext();
		}
		if (CONTEXT == null) {
			throw new RuntimeException(
					"容器没有启动!如果你在使用AbstractJUnit4SpringContextTests测试, 请调用org.roof.spring.CurrentSpringContext.setCurrentContext(ApplicationContext context)方法设置 ApplicationContext");
		}
	}

	/**
	 * 得到当前容器内的Bean(带有泛型)
	 * 
	 * @param <T>
	 *            Bean 的Class类型
	 * @param name
	 *            Bean的名称
	 * @param cls
	 *            Bean的Class
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> cls) {
		initContext();
		return CONTEXT.getBean(name, cls);
	}

	/**
	 * 得到当前容器内的Bean
	 * 
	 * @param name
	 *            Bean的名称
	 * @return
	 */
	public static Object getBean(String name) {
		initContext();
		return CONTEXT.getBean(name);
	}

}
