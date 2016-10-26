package ${bussiPackage}.${entityPackage}.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**   
 *
 * @Description: ${ftl_description}
 * @date ${createTime}
 * @version V1.0   
 *
 */

public class ${entityName}  {

	<#list entityColumns as col>
	/** ${col.filedComment}*/
	private ${col.fieldType} ${col.fieldName};
	</#list>
	
	<#list entityColumns as col>
	
	/**
	 *方法: ${col.fieldType}
	 *@return: ${col.fieldType}  ${col.filedComment}
	 */
	<#if col.fieldName == tableId>
	
	<#if idStategy == 'uuid'>
	@Id
	@GeneratedValue(generator = "uuidGenerator")
	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
	</#if>
	<#if idStategy == 'identity'>
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	</#if>
	<#if idStategy == 'sequence'>
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="sequence")
	@SequenceGenerator(name="sequence",sequenceName="${sequenceCode}",allocationSize=1)
	</#if>
	</#if>
	@Column(name ="${col.fieldDbName}",nullable=<#if col.nullable == 'Y'>true<#else>false</#if><#if col.precision != ''>,precision=${col.precision}</#if><#if col.scale != ''>,scale=${col.scale}</#if><#if col.charmaxLength != ''>,length=${col.charmaxLength}</#if>)
	public ${col.fieldType} get${col.fieldName?cap_first}(){
		return ${col.fieldName};
	}

	public void set${col.fieldName?cap_first}(${col.fieldType} ${col.fieldName}){
		this.${col.fieldName} = ${col.fieldName};
	}
	</#list>
}
