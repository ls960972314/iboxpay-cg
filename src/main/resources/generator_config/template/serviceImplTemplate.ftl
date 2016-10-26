package ${bussiPackage}.${entityPackage}.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iboxpay.frmw.dao.BaseDao;

import ${bussiPackage}.${entityPackage}.entity.${entityName};
import ${bussiPackage}.${entityPackage}.service.${entityName}Service;


/**   
 *
 * @Description: ${ftl_description}服务实现类
 * @date ${createTime}
 * @version V1.0   
 *
 */
@Transactional
@Service("${entityName?uncap_first}Service")
public class ${entityName}ServiceImpl implements ${entityName}Service {
	
	private final Log logger = LogFactory.getLog(${entityName}ServiceImpl.class);
	
	@Resource
	private BaseDao baseDao;

	/**
	 * 分页查询数据列表
	 * 
	 * @param model
	 * @param pageHelper
	 * @return DataGrid
	 */
	@Override
	public String find${entityName}s() {
		return "find${entityName}s";
	}

	/**
	 * 更新信息
	 */
	@Override
	public boolean update${entityName}(${entityName} ${entityName?uncap_first}) {
		return false;
	}

	/**
	 * 新增信息
	 */
	@Override
	public void save${entityName}(${entityName}  ${entityName?uncap_first}) {
	}
	
	/**
	 * 删除信息
	 */
	@Override
	public boolean delete${entityName}ById(${pkType} ${tableId?cap_first}) {
		return false;
	}
}