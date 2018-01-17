package ${packagePath}.entity;

import java.util.List;

<#assign key = primaryKey[0] />
/**
 * @author 模版生成 <br/>
 *         表名： ${tableName} <br/>
 *         描述：${tableDisplay} <br/>
 */
public class ${alias?cap_first}Vo extends ${alias?cap_first} {

	private List<${alias?cap_first}Vo> ${alias}List;

	public ${alias?cap_first}Vo() {
		super();
	}

	public ${alias?cap_first}Vo(Long ${key}) {
		super();
		this.${key} = ${key};
	}

	public List<${alias?cap_first}Vo> get${alias?cap_first}List() {
		return ${alias}List;
	}

	public void set${alias?cap_first}List(List<${alias?cap_first}Vo> ${alias}List) {
		this.${alias}List = ${alias}List;
	}

}
