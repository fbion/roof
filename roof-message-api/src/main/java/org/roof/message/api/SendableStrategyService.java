package org.roof.message.api;

/**
 * 消息发送策略服务类
 * 
 * @author liuxin
 *
 */
public interface SendableStrategyService {
	/**
	 * 保存一个策略
	 * 
	 * @param sendableStrategy
	 * @return
	 */
	Long save(SendableStrategy sendableStrategy);

	/**
	 * 更新一个策略
	 * 
	 * @param sendableStrategy
	 * @return
	 */
	int update(SendableStrategy sendableStrategy);

	/**
	 * 删除一个策略
	 * 
	 * @param id
	 * @return
	 */
	int delete(Long id);

	/**
	 * 加载一个策略
	 * 
	 * @param id
	 * @return
	 */
	SendableStrategy load(Long id);

	/**
	 * 更具主题和类型加载一个策略
	 * 
	 * @param topic
	 * @param type
	 * @return
	 */
	SendableStrategy loadByTopicAndType(String topic, String type);

}
