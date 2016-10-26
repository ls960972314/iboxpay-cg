package com.iboxpay.cg.util;

import java.util.ResourceBundle;

import com.iboxpay.cg.pojo.CreateFileProperty;

/**
 * @author dumengchao
 */
public class CodeResourceUtil {

	private static final ResourceBundle bundle = ResourceBundle
			.getBundle("generator_config/cg_database");
	private static final ResourceBundle bundlePath = ResourceBundle
			.getBundle("generator_config/cg_config");
	public static String DIVER_NAME;
	public static String URL;
	public static String USERNAME;
	public static String PASSWORD;
	public static String DATABASE_NAME;
	public static String DATABASE_TYPE;
	public static String ID_GENERATE_STRATEGY;
	public static String webRootPackage;
	public static String sourceRootPackage;
	public static String bussiPackage;
	public static String entityPackage;
	public static boolean fieldHumpSet = true;
	public static String CODE_PATH;
	public static String ENTITY_URL;
	public static String TEMPLATE_PATH = getTEMPLATE_PATH();
	public static String Mapper_PATH;
	public static String SYSTEM_ENCODING = getSYSTEM_ENCODING();
	public static String TABLE_ID;
	public static String MODULE_NAME;
	

	public CodeResourceUtil() {
	}

	public static final String getDIVER_NAME() {
		return bundle.getString("diver_name").trim();
	}

	public static final String getURL() {
		return bundle.getString("url").trim();
	}

	public static final String getUSERNAME() {
		return bundle.getString("username").trim();
	}

	public static final String getPASSWORD() {
		return bundle.getString("password").trim();
	}

	public static final String getDATABASE_NAME() {
		return bundle.getString("database_name").trim();
	}

	public static final boolean getFieldHumpSet() {
		String s = bundlePath.getString("field_hump_set").trim();
		return !"false".equals(s);
	}

	public static String getBussiPackage() {
		return bundlePath.getString("bussi_package").trim();
	}

	public static final String getEntityPackage() {
		return bundlePath.getString("entity_package").trim();
	}

	public static final String getTEMPLATE_PATH() {
		return bundlePath.getString("template_path").trim();
	}

	public static final String getSourceRootPackage() {
		return bundlePath.getString("source_root_package").trim();
	}

	public static final String getMapperPackage() {
		return bundlePath.getString("mapper_package").trim();
	}

	public static final String getSYSTEM_ENCODING() {
		return bundlePath.getString("system_encoding").trim();
	}

	static {
		TABLE_ID = "id";
		MODULE_NAME = "core";
		entityPackage = getEntityPackage();
		fieldHumpSet = getFieldHumpSet();
		sourceRootPackage = getSourceRootPackage();
		bussiPackage = getBussiPackage();
		sourceRootPackage = sourceRootPackage.replace(".", "/");
		String bussiPackageUrl = bussiPackage.replace(".", "/");
		ENTITY_URL = (new StringBuilder(String.valueOf(sourceRootPackage))).append("/")
				.append(bussiPackageUrl).append("/").append(entityPackage).append("/").toString();
		CODE_PATH = (new StringBuilder(String.valueOf(sourceRootPackage))).append("/")
				.append(bussiPackageUrl).append("/").toString();
		Mapper_PATH = new StringBuilder(getMapperPackage().replace(".", "/")).append("/").toString();
	}

	public static void main(String[] args) {
		System.out.println(getProjectPath());
	}

	private static String getProjectPath() {
		String path = (new StringBuilder(String.valueOf(System.getProperty("user.dir").replace(
				"\\", "/")))).toString();
		path = path.substring(0, path.lastIndexOf('/')) + "/";
		return path;
	}

	public static void initDBParams(CreateFileProperty fileProp) {
		String item = fileProp.getDbType();
		String tableName = fileProp.getTableName();
		DATABASE_TYPE = fileProp.getDbType();
		CodeResourceUtil.MODULE_NAME = fileProp.getEntityPackage();
		DIVER_NAME = bundle.getString(item + "_driverClassName");
		URL = bundle.getString(item + "_url");
		USERNAME = bundle.getString(item + "_username");
		PASSWORD = bundle.getString(item + "_password");
		ReadDBUtil.initSql(item, tableName); 
	}
}
