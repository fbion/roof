package org.roof.druid.monitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceStatLogger;
import com.alibaba.druid.pool.DruidDataSourceStatValue;
import com.alibaba.druid.stat.JdbcSqlStat;
import com.alibaba.druid.stat.JdbcSqlStatValue;

/**
 * <p>
 * 重新方法,记录监控信息后,不清空当前监控信息
 * </p>
 * 
 * @author liuxin
 *
 */
public class StatDruidDataSource extends DruidDataSource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8816802390606881857L;

	private ReentrantReadWriteLock writeLock = new ReentrantReadWriteLock();

	@Override
	public void logStats() {
		final DruidDataSourceStatLogger statLogger = this.statLogger;
		if (statLogger == null) {
			return;
		}

		DruidDataSourceStatValue statValue = getStatValue();

		statLogger.log(statValue);
	}

	public DruidDataSourceStatValue getStatValue() {
		DruidDataSourceStatValue value = new DruidDataSourceStatValue();

		lock.lock();
		try {
			value.setPoolingCount(this.getPoolingCount());
			value.setPoolingPeak(this.getPoolingPeak());
			value.setPoolingPeakTime(this.getPoolingPeakTime().getTime());

			value.setActiveCount(this.getActiveCount());
			value.setActivePeak(this.getActivePeak());
			value.setActivePeakTime(this.getActivePeakTime().getTime());

			value.setConnectCount(this.getConnectCount());
			value.setCloseCount(this.getCloseCount());
			value.setWaitThreadCount(lock.getWaitQueueLength(notEmpty));
			value.setNotEmptyWaitCount(this.getNotEmptyWaitCount());
			value.setNotEmptyWaitNanos(this.getNotEmptyWaitNanos());
		} finally {
			lock.unlock();
		}

		value.setName(this.getName());
		value.setDbType(this.getDbType());
		value.setDriverClassName(this.getDriverClassName());

		value.setUrl(this.getUrl());
		value.setUserName(this.getUsername());
		value.setFilterClassNames(this.getFilterClassNames());

		value.setInitialSize(this.getInitialSize());
		value.setMinIdle(this.getMinIdle());
		value.setMaxActive(this.getMaxActive());

		value.setQueryTimeout(this.getQueryTimeout());
		value.setTransactionQueryTimeout(this.getTransactionQueryTimeout());
		value.setLoginTimeout(this.getLoginTimeout());
		value.setValidConnectionCheckerClassName(this.getValidConnectionCheckerClassName());
		value.setExceptionSorterClassName(this.getExceptionSorterClassName());

		value.setTestOnBorrow(this.isTestOnBorrow());
		value.setTestOnReturn(this.isTestOnReturn());
		value.setTestWhileIdle(this.isTestWhileIdle());

		value.setDefaultAutoCommit(this.isDefaultAutoCommit());

		if (defaultReadOnly != null) {
			value.setDefaultReadOnly(defaultReadOnly);
		}
		value.setDefaultTransactionIsolation(this.getDefaultTransactionIsolation());

		value.setLogicConnectErrorCount(this.getConnectErrorCount());

		value.setPhysicalConnectCount(createCount.get());
		value.setPhysicalCloseCount(destroyCount.get());
		value.setPhysicalConnectErrorCount(createErrorCount.get());

		value.setExecuteCount(this.executeCount.get());
		value.setErrorCount(errorCount.get());
		value.setCommitCount(commitCount.get());
		value.setRollbackCount(rollbackCount.get());

		value.setPstmtCacheHitCount(cachedPreparedStatementHitCount.get());
		value.setPstmtCacheMissCount(cachedPreparedStatementMissCount.get());

		value.setStartTransactionCount(startTransactionCount.get());
		value.setTransactionHistogram(this.getTransactionHistogram().toArray());

		value.setConnectionHoldTimeHistogram(this.getDataSourceStat().getConnectionHoldHistogram().toArray());
		value.setRemoveAbandoned(this.isRemoveAbandoned());
		value.setClobOpenCount(this.getDataSourceStat().getClobOpenCount());
		value.setBlobOpenCount(this.getDataSourceStat().getBlobOpenCount());

		value.setSqlSkipCount(this.getDataSourceStat().getSkipSqlCount());
		value.setSqlList(getSqlStatMapAndReset());

		return value;
	}

	public List<JdbcSqlStatValue> getSqlStatMapAndReset() {
		List<JdbcSqlStat> stats = new ArrayList<JdbcSqlStat>(getSqlStatMap().size());
		writeLock.writeLock().lock();
		try {
			Iterator<Map.Entry<String, JdbcSqlStat>> iter = getSqlStatMap().entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, JdbcSqlStat> entry = iter.next();
				JdbcSqlStat stat = entry.getValue();
				if (stat.getExecuteCount() == 0 && stat.getRunningCount() == 0) {
					stat.setRemoved(true);
					iter.remove();
				} else {
					stats.add(entry.getValue());
				}
			}
		} finally {
			writeLock.writeLock().unlock();
		}

		List<JdbcSqlStatValue> values = new ArrayList<JdbcSqlStatValue>(stats.size());
		for (JdbcSqlStat stat : stats) {
			JdbcSqlStatValue value = stat.getValue(false);
			if (value.getExecuteCount() == 0 && value.getRunningCount() == 0) {
				continue;
			}
			values.add(value);
		}
		return values;
	}

}
