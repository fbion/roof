package org.roof.node.jobs.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.roof.commons.RoofDateUtils;
import org.roof.roof.dataaccess.api.Page;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

public class JdbcItemProcessor implements ItemProcessor<Page, Page> {
	private StepExecution stepExecution;

	@Override
	public Page process(Page page) throws Exception {
		Collection<?> collection = page.getDataList();
		JobParameters jobParameters = stepExecution.getJobParameters();// 获得job启动参数
		String collection_code = jobParameters.getString("collection_code");// 采集编码
		String index_code = jobParameters.getString("index_code");// 指标编码
		String dev_code = jobParameters.getString("dev_code");// 设备编码

		for (Object object : collection) {
			if (object instanceof Map) {
				Map values = (Map) object;
				values.put("collection_code", collection_code);
				values.put("index_code", index_code);
				values.put("dev_code", dev_code);
				values.put("collection_time", new Date());
			}
		}
		return page;
	}

	@BeforeStep
	// spring自动注入job执行上下文对象用于获取job的参数
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

}
