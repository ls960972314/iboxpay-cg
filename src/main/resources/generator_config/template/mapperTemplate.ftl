<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.iboxpay.${entityPackage}.sqlMap.${entityName}Mapper" >

  <!-- 字段映射  -->
  <resultMap id="BaseResultMap" type="${bussiPackage}.${entityPackage}.entity.${entityName}" >
  
 <#list entityColumns as col>
 	<#if col.fieldName == tableId>
   	<id column="${col.fieldDbName}" property="${col.fieldName}" jdbcType="${col.myBatisType}" /> 
    </#if>
    <#if col.fieldName != tableId>
    <result column="${col.fieldDbName}" property="${col.fieldName}" jdbcType="${col.myBatisType}" />
    </#if>
 </#list>
  
  </resultMap>
  
  <!-- 共用sql -->
  <sql id="Base_Column_List" >
     ${dbNameListByComma}
  </sql>

  <!-- 查询 -->  
  <select id="find${entityName}By${tableId?cap_first}" resultMap="BaseResultMap" parameterType="${mybatisType}" >
   	select 
    <include refid="Base_Column_List" />
    from ${tableName}
    where ${selectByPKWhereClause}
  </select>
  
  
  <!-- 查询 列表 -->  
  <!--<select id="find${entityName}sBy${tableId?cap_first}" resultMap="BaseResultMap" parameterType="${mybatisType}" >
    select 
    <include refid="Base_Column_List" />
     from ${tableName}
    where ${selectByPKWhereClause}
    
  </select>
   	
  -->
  <!-- 删除 -->
  
  <delete id="deleteBy${tableId?cap_first}" parameterType="${mybatisType}" >
    delete from ${tableName}
    where ${selectByPKWhereClause}
  </delete>
  
  <!-- 插入操作 -->
  <insert id="save${entityName}" parameterType="${bussiPackage}.${entityPackage}.entity.${entityName}" useGeneratedKeys="true"
    keyProperty="${tableId}">
    insert into ${tableName} (${dbNameListByComma})
    values (
    <#list entityColumns as col>
		 <#if entityColumns?size - 1 == col_index>
			${col.insertUpdateMyBatisField}
		 <#else>
	 		${col.insertUpdateMyBatisField},
	     </#if>
    </#list>
     )
  </insert>
  
 
  <!-- 更新 -->
  <update id="update${entityName}By${tableId?cap_first}" parameterType="${bussiPackage}.${entityPackage}.entity.${entityName}" >
   <![CDATA[
    update ${tableName}
    set
    <#list entityColumns as col>
     <#if entityColumns?size - 1 == col_index>
   		${col.fieldDbName} = ${col.insertUpdateMyBatisField}
   	 <#else>
     	 ${col.fieldDbName} = ${col.insertUpdateMyBatisField},
     </#if>
    </#list>
    where ${selectByPKWhereClause}
    ]]> 
  </update>
</mapper>