package org.roof.node.api;

/**
 * 启动batch接口
 * 
 * @author liht
 *
 */
public interface JobStarter {
	/**
	 * 
	 * @param jobName
	 *            {job名称}
	 * @param parameters
	 *            {JOSN格式参数}
	 */
	void start(String jobName, String parameters);
}
