package org.roof.node.jobs.reader;

import java.net.URLDecoder;
import java.util.Collection;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.roof.node.jobs.datasource.DataSourceContext;
import org.roof.roof.dataaccess.api.Page;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;

/**
 * jdbc分页读取数据并返回
 * <p>
 * job启动参数:<br>
 * dataSourceName 数据源名称 必填<br>
 * dbType 数据库类型 默认mysql<br>
 * sql 查询sql 必填<br>
 * args 查询参数Map json<br>
 * page 分页对象json<br>
 * 
 * </p>
 * 
 * @author liuxin
 *
 */
public class JdbcItemReader implements ItemReader<Page> {

	private StepExecution stepExecution;
	private DataSourceContext dataSourceContext;

	@Override
	public Page read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		int readCount = stepExecution.getReadCount(); // 获取本次job执行,当前读取的参数
		JobParameters jobParameters = stepExecution.getJobParameters();// 获得job启动参数
		String dataSourceName = jobParameters.getString("dataSourceName"); // 从job启动参数中获取数据源名称
		Assert.notNull(dataSourceName, "数据源名称不能为空");
		String dbType = jobParameters.getString("dbType");// 从job启动参数中获取数据源类型(支持mysql,
															// db2, oracle)
		if (StringUtils.isEmpty(dbType)) { // 默认数据库类型为 mysql
			dbType = "mysql";
		}
		String sql = URLDecoder.decode(jobParameters.getString("sql"), "utf-8"); // 从job启动参数中获取需要执行的sql(不需要增加分页参数)
		Assert.notNull(sql, "查询语句不能为空");
		String pageJson = jobParameters.getString("page"); // 从job启动参数中获取Page对象的json串(该参数为开始的分页)
		Collection<Object> args = CollectionUtils.EMPTY_COLLECTION; // 默认的sql执行参数为空
		String argsJson = jobParameters.getString("args");// 从job启动参数中获取sql执行参数的json串
		if (StringUtils.isNotEmpty(argsJson)) {
			args = JSON.parseArray(argsJson, Object.class);
		}
		Page page = null;
		if (pageJson == null) {
			page = new Page();
		} else {
			page = JSON.parseObject(pageJson, Page.class); // 将Page对象的json转换为Page对象
		}
		page.setCurrentPage(page.getCurrentPage() + readCount);// 初始分页+读取次数就为当前需要读取的分页数据
		DataSource dataSource = getDataSource(dataSourceName); // 从数据源context中获取当前数据源
		Assert.notNull(dataSource, "未找到name=[" + dataSourceName + "]的jdbc数据源");
		if (dataSource == null) {
			return null;
		}
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); // 创建jdbcTemplate
		JdbcPageQuery jdbcPageQuery = new JdbcPageQuery(jdbcTemplate, dbType); // 创建分页查询对象
		page = jdbcPageQuery.select(sql, page, args.toArray());// 执行分页查询
		if (page == null || CollectionUtils.isEmpty(page.getDataList())) {// 如果分页查不到数据
															// 返回空,代表本次数据读取job正常结束
			return null;
		}
		return page;
	}

	private DataSource getDataSource(String dataSourceName) {
		Object dataSource = dataSourceContext.get(dataSourceName);
		if (dataSource instanceof DataSource) {
			DataSource result = (DataSource) dataSource;
			return result;
		}
		return null;
	}

	@BeforeStep // spring自动注入job执行上下文对象用于获取job的参数
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public DataSourceContext getDataSourceContext() {
		return dataSourceContext;
	}

	public void setDataSourceContext(DataSourceContext dataSourceContext) {
		this.dataSourceContext = dataSourceContext;
	}

}
