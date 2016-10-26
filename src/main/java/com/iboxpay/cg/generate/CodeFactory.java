package com.iboxpay.cg.generate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.iboxpay.cg.util.CodeResourceUtil;
import com.iboxpay.cg.util.MainFieldDef;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * @author dumengchao
 */
public class CodeFactory {
	private ICallBack callBack;

	public CodeFactory() {
	}

	@SuppressWarnings("rawtypes")
	public void invoke(String templateFileName, String type) {
		Map data = new HashMap();
		data = callBack.execute();
		System.out.println("type= " + type + ", data = " + data);
		// 生成文件
		generateFile(templateFileName, type, data);
	}

	// finally产生文件
	private void generateFile(String templateFileName, String type, Map<?, ?> data) {
		Writer out = null;
		try {
			String entityPackage = data.get(MainFieldDef.ENTITY_PACKAGE).toString();
			String entityName = data.get(MainFieldDef.ENTITY_NAME).toString();
			String fileNamePath = "";
			if (type == "mapper") {
				fileNamePath = getProjectPath() + CodeResourceUtil.Mapper_PATH + entityName + "Mapper.xml";
			} else {
				fileNamePath = getCodePath(type, entityName);
			}
			String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
			Template template = getConfiguration().getTemplate(templateFileName);
			FileUtils.forceMkdir(new File((new StringBuilder(String.valueOf(fileDir))).append("/")
					.toString()));
			out = new OutputStreamWriter(new FileOutputStream(fileNamePath),
					CodeResourceUtil.SYSTEM_ENCODING);
			template.process(data, out);
			out.close();
		} catch (Exception e) {
			System.out.println("----------" + e.getMessage());
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				System.out.println("-----------" + e.getMessage());
			}
		}
	}

	// 获取当前项目目录
	private static String getProjectPath() {
		 String currentProjectPath = new StringBuilder(String.valueOf(System.getProperty("user.dir").replace("\\", "/"))).toString();
		 String rootProjectPath  = currentProjectPath.substring(0, currentProjectPath.lastIndexOf('/'));
		 return currentProjectPath + "/源代码位置/";
	}

	public static void main(String args[]) {
		 System.out.println(getProjectPath());
	}

	// 获取模板路径
	private String getTemplatePath() {
		return (new StringBuilder(String.valueOf(getClassPath()))).append(
				CodeResourceUtil.TEMPLATE_PATH).toString();
	}

	// 获取classpath路径
	private String getClassPath() {
		return Thread.currentThread().getContextClassLoader().getResource("./").getPath();
	}

	// 获取代码路径
	private String getCodePath(String type, String entityName) {
		String path = getProjectPath();
		StringBuilder str = new StringBuilder();
		if (StringUtils.isNotBlank(type)) {
			String codeType = CodeType.valueOf(type).getValue();
			str.append(path);
			str.append(CodeResourceUtil.CODE_PATH);
			// 修正包名顺序不正确bug
			str.append(StringUtils.lowerCase(getBusnissPack()));
			str.append("/");

			if ("Controller".equalsIgnoreCase(codeType))
				str.append(StringUtils.lowerCase("controller"));
			else if ("ServiceImpl".equalsIgnoreCase(codeType))
				str.append(StringUtils.lowerCase("service/impl"));
			else if ("Service".equalsIgnoreCase(codeType))
				str.append(StringUtils.lowerCase("service"));
			else if ("entity".equalsIgnoreCase(codeType)) {
				str.append(StringUtils.lowerCase("entity"));
			} else if ("VO".equalsIgnoreCase(codeType)) {
				str.append(StringUtils.lowerCase("vo"));
			} else if ("Dao".equalsIgnoreCase(codeType)) {
				str.append(StringUtils.lowerCase("dao"));
			} else if ("DaoImpl".equalsIgnoreCase(codeType)) {
				str.append(StringUtils.lowerCase("dao/impl"));
			}

			str.append("/");
			if ("service".equals(StringUtils.lowerCase(codeType))
					|| "dao".equals(StringUtils.lowerCase(codeType))) {
				str.append(StringUtils.capitalize(entityName) + codeType);
			} else {
				str.append(StringUtils.capitalize(entityName));
				String temp = "entity".equals(StringUtils.lowerCase(codeType)) ? "" : codeType;
				str.append(temp);
			}
			str.append(".java");
		} else {
			throw new IllegalArgumentException("type is null");
		}
		return str.toString();
	}

	public static String getBusnissPack() {
		if("almighty-core".equals(CodeResourceUtil.MODULE_NAME)) {
			return "iboxpay";
		}
		return "iboxpay";
	}

	private Configuration getConfiguration() throws IOException {
		Configuration cfg = new Configuration();
		String path = getTemplatePath();
		File templateDirFile = new File(path);
		cfg.setDirectoryForTemplateLoading(templateDirFile);
		cfg.setLocale(Locale.CHINA);
		cfg.setDefaultEncoding("UTF-8");
		return cfg;
	}

	public enum CodeType {
		entity("entity", 0, "entity"), vo("vo", 1, "VO"), dao("dao", 2, "Dao"), daoImpl(
				"daoImpl", 3, "DaoImpl"), serviceImpl("serviceImpl", 4, "ServiceImpl"), service(
				"service", 5, "Service"), controller("controller", 6, "Controller");
		private final String type;

		private CodeType(String s, int i, String type) {
			this.type = type;
		}

		public String getValue() {
			return type;
		}
	}

	public ICallBack getCallBack() {
		return callBack;
	}

	public void setCallBack(ICallBack callBack) {
		this.callBack = callBack;
	}
}
