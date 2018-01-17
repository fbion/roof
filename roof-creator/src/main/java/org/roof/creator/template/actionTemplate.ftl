package ${packagePath}.action;

import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.roof.exceptions.ApplicationException;
import org.roof.dataaccess.Page;
import org.roof.security.BaseUserContext;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.struts2.WebUtils;
import org.roof.web.dictionary.service.DictionaryService;
import org.roof.web.dictionary.entity.Dictionary;
import ${packagePath}.entity.${alias?cap_first};
import ${packagePath}.service.${alias?cap_first}Service;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 自动生成模版
 */
@Component("${actionName}Action")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class ${alias?cap_first}Action extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private ${alias?cap_first}Service ${alias}Service;

	private ${alias?cap_first} ${alias};

	private List<${alias?cap_first}> ${alias}List;
	
	private DictionaryService dictionaryService;
	
	/**
	 * 加载公共数据
	 */
	private void loadCommonData() {
		// (Staff)BaseUserContext.getCurrentUser();// 得到当前用户
		// List<Dictionary> demoList = dictionaryService.findByType("字典表类型");
		// super.addParameter("demoList", demoList);
	}

	/**
	 * 查询列表实例,分页显示
	 * 
	 * @return
	 */
	public String list() {
		Page page = super.createPage();
		page = ${alias}Service.query${alias?cap_first}Page(${alias}, page);
		super.addParameter("page", page);
		loadCommonData();
		result = "/${sysName}/${packagePath?substring(packagePath?last_index_of(".")+1)}/${alias}_list.jsp";
		return JSP;
	}

	/**
	 * 前往新增页面
	 * 
	 * @return
	 */
	public String create_page() {
		${alias} = new ${alias?cap_first}();
		super.addParameter("${alias}_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/${sysName}/${packagePath?substring(packagePath?last_index_of(".")+1)}/${alias}_create.jsp";
		return JSP;
	}

	/**
	 * 前往修改页面
	 * <#assign key = primaryKey[0] />
	 * @return
	 */
	public String update_page() throws Exception {
		${alias} = ${alias}Service.load(${alias}.get${key?cap_first}());
		super.addParameter("${alias}", ${alias});
		super.addParameter("${alias}_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/${sysName}/${packagePath?substring(packagePath?last_index_of(".")+1)}/${alias}_update.jsp";
		return JSP;
	}

	/**
	 * 前往查看页面
	 * 
	 * @return
	 */
	public String detail_page() throws Exception {
		${alias} = ${alias}Service.load(${alias}.get${key?cap_first}());
		super.addParameter("${alias}", ${alias});
		super.addParameter("${alias}_paramString", super.getParamString(WebUtils.getRequest()));
		loadCommonData();
		result = "/${sysName}/${packagePath?substring(packagePath?last_index_of(".")+1)}/${alias}_detail.jsp";
		return JSP;
	}

	/**
	 * 加载单条数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		// 防止递归，造成JSON数据过大
		result = ${alias}Service.load(${alias}.get${key?cap_first}());
		return JSON;
	}

	/**
	 * 删除实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String delete() throws ApplicationException {
		if (CollectionUtils.isEmpty(${alias}List)) {
			result = new Result("删除成功!");
			return JSON;
		}
		try {
			for (int i = 0; i < ${alias}List.size(); i++) {
				${alias?cap_first} tempDomain = ${alias}List.get(i);
				${alias}Service.delete(tempDomain);
			}
			result = new Result("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00002");
		}
		return JSON;
	}

	/**
	 * 保存实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String create() throws ApplicationException {
		try {
			if (${alias} == null){
				${alias} = new ${alias?cap_first}();
			}
			if (${alias}.getId() == null) {
				${alias}Service.save(${alias});
				result = new Result("新增成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	/**
	 * 修改实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String update() throws ApplicationException {
		try {
			if (${alias} == null){
				${alias} = new ${alias?cap_first}();
			}
			${alias}Service.updateIgnoreNull(${alias});
			result = new Result("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	@Resource
	public void set${alias?cap_first}Service(${alias?cap_first}Service ${alias}Service) {
		this.${alias}Service = ${alias}Service;
	}
	
	public ${alias?cap_first}Service get${alias?cap_first}Service() {
		return ${alias}Service;
	}

	public ${alias?cap_first} get${alias?cap_first}() {
		return ${alias};
	}

	public void set${alias?cap_first}(${alias?cap_first} ${alias}) {
		this.${alias} = ${alias};
	}

	public List<${alias?cap_first}> get${alias?cap_first}List() {
		return ${alias}List;
	}

	public void set${alias?cap_first}List(List<${alias?cap_first}> ${alias}List) {
		this.${alias}List = ${alias}List;
	}
	
	@Resource
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

}