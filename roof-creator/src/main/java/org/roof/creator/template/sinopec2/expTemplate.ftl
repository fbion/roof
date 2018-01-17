<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<#assign key = primaryKey[0] />
<#assign head = "#{" />
<mapper namespace="${packagePath}.dao">
	<sql id="columnsAs">
		<#list fields as field>t1.${field.fieldName}<#if (field_index != (fields?size-1))>, </#if></#list>
	</sql>

	<select id="select${alias?cap_first}Page" resultType="${packagePath}.entity.${alias?cap_first}Vo">
		select
		<include refid="columnsAs"/>
		from
		${tableName} t1
		join
		(SELECT
		${key}
		from
		${tableName}
		where 1=1
		
		<#if (drdsId ??)>
		<!-- 分库键 -->
		<if test="${drdsId} != null and ${drdsId} != ''">
			and ${drdsId} = ${head}${drdsId}}
		</if>
		</#if>
		<include refid="conds" />
		order by ${key} desc
		limit ${head}firstrownum}, ${head}limit}) t2
		where t1.${key} = t2.${key}
	</select>
	
	<select id="select${alias?cap_first}Count" resultType="java.lang.Long">
		select
		count(${key})
		from ${tableName}
		where 1=1
		<include refid="conds" />
	</select>

</mapper>