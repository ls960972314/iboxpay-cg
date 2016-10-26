package ${bussiPackage}.${entityPackage}.vo;
import java.util.Date;

/**   
 *
 * @Description: ${ftl_description}
 * @date ${createTime}
 * @version V1.0   
 *
 */

public class ${entityName} {

    
	<#list entityColumns as col>
	/** ${col.filedComment}*/
	private ${col.fieldType} ${col.fieldName};
	</#list>
	
	<#list entityColumns as col>

	public ${col.fieldType} get${col.fieldName?cap_first}(){
		return ${col.fieldName};
	}

	public void set${col.fieldName?cap_first}(${col.fieldType} ${col.fieldName}){
		this.${col.fieldName} = ${col.fieldName};
	}
	</#list>
}
