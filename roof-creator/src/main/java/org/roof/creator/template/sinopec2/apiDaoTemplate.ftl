package ${packagePath}.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import ${packagePath}.entity.${alias?cap_first};

public interface I${alias?cap_first}Dao extends IDaoSupport {
	Page page(Page page, ${alias?cap_first} ${alias});
}