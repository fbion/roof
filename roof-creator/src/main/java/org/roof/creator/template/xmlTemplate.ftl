<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE sqlMap PUBLIC "-//iBATIS.com//DTD SQL Map 2.0//EN" "http://www.ibatis.com/dtd/sql-map-2.dtd">
<sqlMap namespace="${alias?cap_first}_exp">
	<select id="${alias?cap_first}_exp_select_${alias}_list" parameterClass="${packagePath}.entity.${alias?cap_first}"
		resultClass="${packagePath}.entity.${alias?cap_first}">
		<![CDATA[ 
		from ${alias?cap_first} WHERE 1=1
		]]>
		<#list fields as field>
		<#assign isForeign = false />
		<#list relations as relation>
		<#if (relation.foreignCol == field.fieldName)>
		<#assign isForeign = true />
		<isNotEmpty prepend="and" property="${relation.alias}.${relation.primaryCol}"> 
		<![CDATA[ 
		${relation.alias}.${relation.primaryCol} = #${relation.alias}.${relation.primaryCol}#
		]]> 
		</isNotEmpty>
		</#if>
		</#list>
		<#if (!(isForeign))>
		<isNotEmpty prepend="and" property="${field.fieldName}"> 
		<![CDATA[ 
		<#if (field.dbType == "NUMBER")>
		${field.fieldName} = #${field.fieldName}#
		<#elseif (field.dbType == "DATE")>
		to_char(${field.fieldName},'YYYY-MM-DD') = to_char(#${field.fieldName}#,'YYYY-MM-DD')
		<#else>
		${field.fieldName} like '%' || #${field.fieldName}# || '%'
		</#if>
		]]> 
		</isNotEmpty>
		</#if>
		</#list>
		<![CDATA[
		order by <#list primaryKey as key>${key}<#if (key_index != (primaryKey?size-1))>, </#if></#list> desc 
		]]>
	</select>
	
	<select id="${alias?cap_first}_exp_select_${alias}_count" parameterClass="${packagePath}.entity.${alias?cap_first}"
		resultClass="java.lang.Long">
		<![CDATA[ 
		select count(*) from ${alias?cap_first} WHERE 1 = 1
		]]> 
		<#list fields as field>
		<#assign isForeign = false />
		<#list relations as relation>
		<#if (relation.foreignCol == field.fieldName)>
		<#assign isForeign = true />
		<isNotEmpty prepend="and" property="${relation.alias}.${relation.primaryCol}"> 
		<![CDATA[ 
		${relation.alias}.${relation.primaryCol} = #${relation.alias}.${relation.primaryCol}#
		]]> 
		</isNotEmpty>
		</#if>
		</#list>
		<#if (!(isForeign))>
		<isNotEmpty prepend="and" property="${field.fieldName}"> 
		<![CDATA[ 
		<#if (field.dbType == "NUMBER")>
		${field.fieldName} = #${field.fieldName}#
		<#elseif (field.dbType == "DATE")>
		to_char(${field.fieldName},'YYYY-MM-DD') = to_char(#${field.fieldName}#,'YYYY-MM-DD')
		<#else>
		${field.fieldName} like '%' || #${field.fieldName}# || '%'
		</#if>
		]]> 
		</isNotEmpty>
		</#if>
		</#list>
	</select>

</sqlMap>