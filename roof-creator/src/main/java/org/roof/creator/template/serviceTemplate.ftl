package ${packagePath}.service;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import ${packagePath}.entity.${alias?cap_first};
import ${packagePath}.dao.${alias?cap_first}Dao;
import org.roof.dataaccess.Page;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class ${alias?cap_first}Service {

	private ${alias?cap_first}Dao ${alias}Dao;

	/**
	 * 列表展示
	 */
	public Page query${alias?cap_first}Page(${alias?cap_first} paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new ${alias?cap_first}();
		}
		return ${alias}Dao.query${alias?cap_first}Page(paramObj, page);
	}
	
	/**
	 * 保存数据
	 */
	public ${alias?cap_first} save(${alias?cap_first} paramObj) throws Exception{
		${alias}Dao.save(paramObj);
		return paramObj;
	}
	
	/**
	 * 保存数据
	 */
	public ${alias?cap_first} saveOrUpdate(${alias?cap_first} paramObj) throws Exception{
		${alias}Dao.saveOrUpdate(paramObj);
		return paramObj;
	}
	
	/**
	 * 删除数据
	 */
	public ${alias?cap_first} delete(${alias?cap_first} paramObj) throws Exception{
		List<${alias?cap_first}> list = (List<${alias?cap_first}>) ${alias}Dao.findByMappedQuery("${alias?cap_first}_exp_select_${alias}_list", paramObj);
		for (${alias?cap_first} ${alias} : list) {
			${alias}Dao.delete(${alias});
		}
		return paramObj;
	}
	
	/**
	 * 查询数据
	 */
	public List<${alias?cap_first}> select(${alias?cap_first} paramObj) {
		List<${alias?cap_first}> list = (List<${alias?cap_first}>) ${alias}Dao.findByMappedQuery("${alias?cap_first}_exp_select_${alias}_list", paramObj);
		return list;
	}
	
	/**
	 * 查询数据
	 */
	public ${alias?cap_first} selectObject(${alias?cap_first} paramObj) {
		List<${alias?cap_first}> list = this.select(paramObj);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return new ${alias?cap_first}();
		}
	}
	
	/**
	 * 修改数据
	 */
	public ${alias?cap_first} update(${alias?cap_first} paramObj) throws Exception{
		${alias}Dao.update(paramObj);
		return paramObj;
	}
	
	/**
	 * 修改数据，忽略空值
	 */
	public ${alias?cap_first} updateIgnoreNull(${alias?cap_first} paramObj) throws Exception{
		${alias}Dao.updateIgnoreNull(paramObj);
		return paramObj;
	}
	
	/**
	 * 根据ID延迟加载持久化对象
	 */
	public ${alias?cap_first} load(Serializable id) throws Exception{
		${alias?cap_first} paramObj = (${alias?cap_first}) ${alias}Dao.load(${alias?cap_first}.class, id, false);
		return paramObj;
	}
	
	/**
	 * 加载全部数据
	 */
	public List<${alias?cap_first}> loadAll() throws Exception{
		return (List<${alias?cap_first}>) ${alias}Dao.loadAll(${alias?cap_first}.class);
	}

	@Resource
	public void set${alias?cap_first}Dao(${alias?cap_first}Dao ${alias}Dao) {
		this.${alias}Dao = ${alias}Dao;
	}

}
