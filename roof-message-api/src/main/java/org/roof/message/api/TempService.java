package org.roof.message.api;

import java.util.Map;

/**
 * 消息模板加载
 * 
 * @author liuxin
 *
 */
public interface TempService {

	/**
	 * 保存一个模板
	 * 
	 * @return
	 */
	Long save(Temp temp);

	/**
	 * 更新一个模板
	 * 
	 * @return
	 */
	int update(Temp temp);

	/**
	 * 删除一个策略
	 * 
	 * @param id
	 * @return
	 */
	int delete(Long id);

	/**
	 * 加载一个模板
	 * 
	 * @param id
	 * @return
	 */
	Temp load(Long id);

	/**
	 * 更具主题和类型加载一个模板
	 * 
	 * @param topic
	 * @param type
	 * @return
	 */
	Temp loadByTopicAndType(String topic, String type);

	/**
	 * 渲染一个模板
	 * 
	 * @param topic
	 *            主题
	 * @param type
	 *            类型
	 * @param params
	 *            参数
	 * @return
	 */
	String render(String topic, String type, Map<String, Object> params);

}
