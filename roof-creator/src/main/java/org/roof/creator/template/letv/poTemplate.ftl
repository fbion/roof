package ${packagePath}.entity;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;

<#assign key = primaryKey[0] />
/**
 * @author 模版生成 <br/>
 *         表名： ${tableName} <br/>
 *         描述：${tableDisplay} <br/>
 */
public class ${alias?cap_first} implements Serializable {
	// 需要手动添加非默认的serialVersionUID
<#list fields as field>
	<#if field.dbType == "datetime">
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	<#elseif field.dbType == "date">
	@DateTimeFormat(pattern="yyyy-MM-dd")
	</#if>
	protected ${field.fieldType} ${field.fieldName};// ${field.fieldDisplay}
</#list>

	public ${alias?cap_first}() {
		super();
	}

	public ${alias?cap_first}(Long ${key}) {
		super();
		this.${key} = ${key};
	}
	
<#list fields as field>
	<#if (field.fieldName == key)>@Id// 主键</#if>
	public ${field.fieldType} get${field.fieldName?cap_first}() {
		return ${field.fieldName};
	}
	public void set${field.fieldName?cap_first}(${field.fieldType} ${field.fieldName}) {
		this.${field.fieldName} = ${field.fieldName};
	}
</#list>
}
