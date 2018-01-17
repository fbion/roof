package ${packagePath}.dao;

import org.roof.dataaccess.RoofDaoSupport;
import ${packagePath}.entity.${alias?cap_first};
import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class ${alias?cap_first}Dao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page query${alias?cap_first}Page(${alias?cap_first} paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "${alias?cap_first}_exp_select_${alias}_list",
				"${alias?cap_first}_exp_select_${alias}_count");
		return pageQuery.findByMappedQuery(paramObj);
	}
}
