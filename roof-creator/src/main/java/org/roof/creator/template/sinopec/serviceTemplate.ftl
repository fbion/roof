package ${packagePath}.service.impl;

import java.io.Serializable;
import java.util.List;
import org.roof.roof.dataaccess.api.Page;
import ${packagePath}.dao.api.I${alias?cap_first}Dao;
import ${packagePath}.entity.${alias?cap_first};
import ${packagePath}.entity.${alias?cap_first}Vo;
import ${packagePath}.service.api.I${alias?cap_first}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ${alias?cap_first}Service implements I${alias?cap_first}Service {
	private I${alias?cap_first}Dao ${alias}Dao;

	public Serializable save(${alias?cap_first} ${alias}){
		return ${alias}Dao.save(${alias});
	}

	public void delete(${alias?cap_first} ${alias}){
		${alias}Dao.delete(${alias});
	}
	
	public void deleteByExample(${alias?cap_first} ${alias}){
		${alias}Dao.deleteByExample(${alias});
	}

	public void update(${alias?cap_first} ${alias}){
		${alias}Dao.update(${alias});
	}
	
	public void updateIgnoreNull(${alias?cap_first} ${alias}){
		${alias}Dao.updateIgnoreNull(${alias});
	}
		
	public void updateByExample(${alias?cap_first} ${alias}){
		${alias}Dao.update("updateByExample${alias?cap_first}", ${alias});
	}

	public ${alias?cap_first}Vo load(${alias?cap_first} ${alias}){
		return (${alias?cap_first}Vo)${alias}Dao.reload(${alias});
	}
	
	public List<${alias?cap_first}Vo> selectForList(${alias?cap_first} ${alias}){
		return (List<${alias?cap_first}Vo>)${alias}Dao.selectForList("select${alias?cap_first}",${alias});
	}
	
	public Page page(Page page, ${alias?cap_first} ${alias}) {
		return ${alias}Dao.page(page, ${alias});
	}

	@Autowired
	public void setI${alias?cap_first}Dao(
			@Qualifier("${alias}Dao") I${alias?cap_first}Dao  ${alias}Dao) {
		this.${alias}Dao = ${alias}Dao;
	}

}
