package org.roof.creator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class AutoLetvCreateUtilsMysqlTest extends AbstractJUnit4SpringContextTests {
	AutoLetvCreateUtilsMysql autoLetvCreateUtils;

	@Test
	public void testCreateCode() {
		// 包名
		//String packagePath = "com.letv.gcr.dictionary";
		//String packagePath = "com.letv.gcr.signing_basic";
		//String packagePath = "com.letv.gcr.contract";
		//String packagePath = "com.letv.gcr.purchase_contract";
		//String packagePath = "com.letv.gcr.distribution_contract";
		//String packagePath = "com.letv.gcr.child_contract";
		//String packagePath = "com.letv.gcr.contract_content";
		//String packagePath = "com.letv.gcr.contract_copyright_pro";
		//String packagePath = "com.letv.gcr.contract_history";
		//String packagePath = "com.letv.gcr.contract_product";
		//String packagePath = "com.letv.gcr.contract_rights";
		//String packagePath = "com.letv.gcr.distribution_purchase";
		//String packagePath = "com.letv.gcr.sign_information";
		//String packagePath = "com.letv.gcr.my_sign_information";
		//String packagePath = "com.letv.gcr.your_sign_information";
		//String packagePath = "com.letv.gcr.his_sign_information";
		//String packagePath = "com.letv.gcr.payment";
		//String packagePath = "com.letv.gcr.purchase_payment";
		//String packagePath = "com.letv.gcr.distribution_payment";
		//String packagePath = "com.letv.gcr.media_information";
		//String packagePath = "com.letv.gcr.media_information_copyright";
		String packagePath = "com.letv.gcr.finance";

		// 添加需要生成的表名
		List<String> sourcelist = new ArrayList<String>();
		//sourcelist.add("s_dictionary");
		//sourcelist.add("g_signing_basic");
		//sourcelist.add("g_contract");
		//sourcelist.add("g_purchase_contract");
		//sourcelist.add("g_distribution_contract");
		//sourcelist.add("g_child_contract");
		//sourcelist.add("g_contract_content");
		//sourcelist.add("g_contract_copyright_pro");
		//sourcelist.add("g_contract_history");
		//sourcelist.add("g_contract_product");
		//sourcelist.add("g_contract_rights");
		//sourcelist.add("g_distribution_purchase");
		//sourcelist.add("g_sign_information");
		//sourcelist.add("g_my_sign_information");
		//sourcelist.add("g_your_sign_information");
		//sourcelist.add("g_his_sign_information");
		//sourcelist.add("g_payment");
		//sourcelist.add("g_purchase_payment");
		//sourcelist.add("g_distribution_payment");
		//sourcelist.add("g_media_information");
		//sourcelist.add("g_media_information_copyright");
		sourcelist.add("g_finance");
		
		// 拆分键
		autoLetvCreateUtils.createCode(packagePath, sourcelist, "", false); // 不带拆分键，不带页签
		//autoLetvCreateUtils.createCode(packagePath, sourcelist, "", true); // 不带拆分键，带页签
		//autoLetvCreateUtils.createCode(packagePath, sourcelist, "username"); // 带拆分键（不填默认为false）
	}

	@Test
	public void testSql() {

	}

	@Autowired(required = true)
	public void setAutoLetvCreateUtils(@Qualifier("autoLetvCreateUtilsMysql") AutoLetvCreateUtilsMysql autoLetvCreateUtils) {
		this.autoLetvCreateUtils = autoLetvCreateUtils;
	}

}