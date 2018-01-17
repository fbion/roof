package ${packagePath}.dao.impl;

import java.util.Comparator;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.roof.dataaccess.PageQuery;
import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.IPageQuery;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ${packagePath}.dao.api.I${alias?cap_first}QueryDao;
import ${packagePath}.entity.${alias?cap_first};
<#assign key = primaryKey[0] />
@Service
public class ${alias?cap_first}QueryDao extends AbstractDao implements I${alias?cap_first}QueryDao {
	private PageQueryFactory<PageQuery> pageQueryFactory;
	
	public Page page(Page page, ${alias?cap_first} ${alias}) {
		<#if key != "id">
		FastPage fp = (FastPage) page;
		fp.setOrderByPropertyName("${key}");
		IPageQuery pageQuery = pageQueryFactory.getPageQuery(fp,"select${alias?cap_first}Page", "select${alias?cap_first}Count");
		<#else>
		IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"select${alias?cap_first}Page", "select${alias?cap_first}Count");
		</#if>
		//IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"select${alias?cap_first}Page", null);
		return pageQuery.select(${alias});
	}

	@Autowired
	public void setDaoSupport(
			@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}
	
	@Autowired
	public void setPageQueryFactory(
			@Qualifier("pageQueryFactory") PageQueryFactory<PageQuery> pageQueryFactory) {
		this.pageQueryFactory = pageQueryFactory;
	}

}