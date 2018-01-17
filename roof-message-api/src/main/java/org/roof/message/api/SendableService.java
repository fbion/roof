package org.roof.message.api;

/**
 * 信息管理
 * 
 * @author liuxin
 *
 * @param <T>
 *            信息
 */
public interface SendableService<T extends Sendable> {

	/**
	 * 保存并返回id
	 * 
	 * @param sendable
	 * @return
	 */
	public String save(T sendable);

	/**
	 * 更新
	 * 
	 * @param sendable
	 */
	public void update(T sendable);

	/**
	 * 发送返回发送状态
	 * 
	 * @param sendable
	 * 
	 * @throws Exception
	 */
	public int send(T sendable) throws Exception;

}
