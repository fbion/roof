package ${packagePath}.dao.impl;

import org.roof.dataaccess.FastPageQuery;
import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import ${packagePath}.entity.${alias?cap_first};
import ${packagePath}.dao.api.I${alias?cap_first}Dao;
import org.roof.roof.dataaccess.api.FastPage;
import org.roof.roof.dataaccess.api.IPageQuery;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
<#assign key = primaryKey[0] />
@Service
public class ${alias?cap_first}Dao extends AbstractDao implements I${alias?cap_first}Dao {
	private PageQueryFactory<FastPageQuery> pageQueryFactory;
	
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
			@Qualifier("fastPageQueryFactory") PageQueryFactory<FastPageQuery> pageQueryFactory) {
		this.pageQueryFactory = pageQueryFactory;
	}

}