package ${bussiPackage}.${entityPackage}.service;
import ${bussiPackage}.${entityPackage}.entity.${entityName};

/**   
 *
 * @Description: ${ftl_description}服务类
 * @date ${createTime}
 * @version V1.0   
 *
 */
public interface ${entityName}Service {

	/**
	 * 查找列表
	 * 
	 * @param model 中间类
	 * @param pageHelper 分页对象
	 */
	String find${entityName}s();

	/**
	 * 更新对象
	 * 
	 * @param model
	 */
	boolean update${entityName}(${entityName} ${entityName?uncap_first});

	/**
	 * 保存对象
	 * 
	 * @param model
	 */
	void save${entityName}(${entityName} ${entityName?uncap_first});
	
	/**
	 * 删除对象
	 * 
	 * @param id
	 * @return
	 */
	boolean delete${entityName}ById(${pkType} ${tableId?cap_first});
}
