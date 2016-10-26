package ${bussiPackage}.${entityPackage}.controller;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.iboxpay.frmw.util.StringUtil;

import ${bussiPackage}.${entityPackage}.entity.${entityName};
import ${bussiPackage}.${entityPackage}.service.${entityName}Service;

/**   
 * @Title: Controller
 * @Description: ${ftl_description}
 * @date ${createTime}
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/${entityName?uncap_first}")
public class ${entityName}Controller {
	/**
	 * Logger for this class
	 */
	 private static final Logger logger = LoggerFactory.getLogger(${entityName}Controller.class);

	@Autowired
	private ${entityName}Service ${entityName?uncap_first}Service;

	@RequestMapping(value = "/find${entityName}s.htm")
	@ResponseBody
	public String find${entityName}List(HttpServletRequest request) {
		return ${entityName?uncap_first}Service.find${entityName}s();
	}
	
	@RequestMapping(value = "/addOrUpdate${entityName}.htm")
	@ResponseBody
	public String addOrUpdate${entityName}(${entityName} ${entityName?uncap_first}, HttpServletRequest request) {
		if (StringUtil.isNotEmpty(${entityName?uncap_first}.get${tableId?cap_first}())) {
			${entityName?uncap_first}Service.update${entityName}(${entityName?uncap_first});
		} else {
			${entityName?uncap_first}Service.save${entityName}(${entityName?uncap_first});
		}
		return "succ";
	}

	@RequestMapping(value = "/del${entityName}.htm")
	@ResponseBody
	public String del${entityName}(${entityName} ${entityName?uncap_first}, HttpServletRequest request) {
		if (StringUtil.isNotEmpty(${entityName?uncap_first}.get${tableId?cap_first}())) {
			${entityName?uncap_first}Service.delete${entityName}ById(${entityName?uncap_first}.get${tableId?cap_first}());
			return "succ";
		} else {
			return "fail";
		}
		
	}
	
}
