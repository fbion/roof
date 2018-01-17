<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<#assign key = primaryKey[0] />
<#assign head = "#{" />
<mapper namespace="${packagePath}.querydao">

	<sql id="columns">
		<#list fields as field>${field.fieldName}<#if (field_index != (fields?size-1))>, </#if></#list>
	</sql>
	<sql id="vals">
		<#list fields as field>${head}${field.fieldName}}<#if (field_index != (fields?size-1))>, </#if></#list>
	</sql>
	<sql id="conds">
		<#list fields as field>
		<#if field.fieldType == "Date">
		<if test="${field.fieldName} != null">
		<#else>
		<if test="${field.fieldName} != null and ${field.fieldName} != ''">
		</#if>
			and ${field.fieldName} = ${head}${field.fieldName}}
		</if>
		</#list>
	</sql>
	<#if (drdsId ??)>
	<sql id="drdsId">
		and ${drdsId} = ${head}${drdsId}}
	</sql>
	</#if>

	<select id="load${alias?cap_first}" resultType="${packagePath}.entity.${alias?cap_first}Vo">
		select 
		<include refid="columns"/>
		from ${tableName}
		where ${key}=${head}${key}} <#if (drdsId ??)><include refid="drdsId" /></#if>
	</select>
	
	<select id="select${alias?cap_first}" resultType="${packagePath}.entity.${alias?cap_first}Vo">
		select 
		<include refid="columns"/>
		from ${tableName}
		where 1=1
		<include refid="conds" />
	</select>
</mapper>