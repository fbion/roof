package org.roof.druid.monitor;

import java.util.Map;

import com.alibaba.druid.stat.DruidStatService;
import com.alibaba.druid.stat.DruidStatServiceMBean;

public class RedisDruidStatService implements DruidStatServiceMBean {

	private DruidStatService statService = DruidStatService.getInstance();

	private RedisDruidStatManagerFacade redisDruidStatManagerFacade;

	public RedisDruidStatService(RedisDruidStatManagerFacade redisDruidStatManagerFacade) {
		super();
		this.redisDruidStatManagerFacade = redisDruidStatManagerFacade;
	}

	@Override
	public String service(String url) {
		Map<String, String> parameters = DruidStatService.getParameters(url);

		if (url.equals("/datasource.json")) {
			return DruidStatService.returnJSONResult(DruidStatService.RESULT_CODE_SUCCESS,
					redisDruidStatManagerFacade.getDataSourceStatDataList());
		}
		return DruidStatService.returnJSONResult(DruidStatService.RESULT_CODE_ERROR,
				"Do not support this request, please contact with administrator.");
	}

}
