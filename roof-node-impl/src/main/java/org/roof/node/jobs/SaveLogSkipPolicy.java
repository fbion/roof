package org.roof.node.jobs;

import org.apache.log4j.Logger;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

/**
 * 记录日志异常跳过处理
 * 
 * @author liuxin
 * 
 */
public class SaveLogSkipPolicy implements SkipPolicy {

	private static final Logger LOGGER = Logger.getLogger(SaveLogSkipPolicy.class);

	@Override
	public boolean shouldSkip(Throwable t, int skipCount) throws SkipLimitExceededException {
		LOGGER.error(t.getMessage(), t);
		return true; // 返回true跳过继续执行下一次任务块, 返回false停止job
	}

}
