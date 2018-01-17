<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<#assign key = primaryKey[0] />
<#assign head = "#{" />
<mapper namespace="${packagePath}.dao">

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

	<insert id="save${alias?cap_first}" parameterType="${packagePath}.entity.${alias?cap_first}" useGeneratedKeys="true" keyProperty="${key}">
		<selectKey keyProperty="${key}" resultType="Long" order="AFTER">
			select last_insert_id()
		</selectKey>
		insert into ${tableName} (<include refid="columns" />) 
		values (<include refid="vals" />)
	</insert>

	<delete id="delete${alias?cap_first}">
		delete from ${tableName}
		where ${key}=${head}${key}} <#if (drdsId ??)><include refid="drdsId" /></#if>
	</delete>

	<delete id="deleteByExample${alias?cap_first}">
		delete from ${tableName}
		where 1=1
		<include refid="conds" />
	</delete>

	<update id="update${alias?cap_first}">
		update ${tableName}
		<set>
		<#list fields as field>
			${field.fieldName}=${head}${field.fieldName}}<#if (field_index != (fields?size-1))>, </#if>
		</#list>	
		</set>
		where ${key}=${head}${key}} <#if (drdsId ??)><include refid="drdsId" /></#if>
	</update>

	<update id="updateIgnoreNull${alias?cap_first}">
		update ${tableName}
		<set>
		<#list fields as field>
		<#if field.fieldType == "Date">
		<if test="${field.fieldName} != null">
		<#else>
		<if test="${field.fieldName} != null and ${field.fieldName} != ''">
		</#if>		
			${field.fieldName}=${head}${field.fieldName}}<#if (field_index != (fields?size-1))>, </#if>
		</if>
		</#list>
		</set>
		where ${key}=${head}${key}} <#if (drdsId ??)><include refid="drdsId" /></#if>
	</update>

	<update id="updateByExample${alias?cap_first}">
		update ${tableName}
		<set>
			<#list fields as field>
			<#if field.fieldType == "Date">
			<if test="${field.fieldName} != null">
			<#else>
			<if test="${field.fieldName} != null and ${field.fieldName} != ''">
			</#if>
			${field.fieldName}=${head}${field.fieldName}}<#if (field_index != (fields?size-1))>, </#if>
			</if>
			</#list>
		</set>
		where 1=1
		<include refid="conds" />
	</update>

</mapper>