package org.roof.message.service.impl;

import org.roof.message.api.Sendable;
import org.roof.message.api.SendableService;
import org.roof.roof.dataaccess.api.IDaoSupport;

/**
 * 消息操作
 * 
 * @author liuxin
 *
 * @param <T>
 */
public abstract class AbstractSendableService<T extends Sendable> implements SendableService<T> {

	private IDaoSupport daoSupport;

	@Override
	public String save(T sendable) {
		daoSupport.save(sendable);
		return sendable.getId();
	}

	@Override
	public void update(T sendable) {
		daoSupport.update(sendable);
	}

	public IDaoSupport getDaoSupport() {
		return daoSupport;
	}

	public void setDaoSupport(IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

}
