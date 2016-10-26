package com.iboxpay.cg.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.iboxpay.cg.pojo.ColumnInfo;

/**
 * 
 * @author dumengchao
 */
public class CodeStringUtils {

	public CodeStringUtils() {
	}

	public static String getStringSplit(String val[]) {
		StringBuffer sqlStr = new StringBuffer();
		String as[] = val;
		int i = 0;
		for (int j = as.length; i < j; i++) {
			String s = as[i];
			if (StringUtils.isNotBlank(s)) {
				sqlStr.append(",");
				sqlStr.append("'");
				sqlStr.append(s.trim());
				sqlStr.append("'");
			}
		}

		return sqlStr.toString().substring(1);
	}

	public static String getInitialSmall(String str) {
		if (StringUtils.isNotBlank(str))
			str = (new StringBuilder(String.valueOf(str.substring(0, 1).toLowerCase()))).append(
					str.substring(1)).toString();
		return str;
	}

	public static Integer getIntegerNotNull(Integer t) {
		if (t == null)
			return Integer.valueOf(0);
		else
			return t;
	}

	public static boolean isIn(String s, String as[]) {
		if (as == null || as.length == 0)
			return false;
		for (int i = 0; i < as.length; i++) {
			String s1 = as[i];
			if (s1.equals(s))
				return true;
		}

		return false;
	}

	public static boolean isIn(String s, List<String> list) {
		String as[] = new String[0];
		if (list != null)
			as = (String[]) list.toArray();
		if (as == null || as.length == 0)
			return false;
		for (int i = 0; i < as.length; i++) {
			String s1 = as[i];
			if (s1.equals(s))
				return true;
		}

		return false;
	}

	public static String getNullAble(String nullable) {
		if ("YES".equals(nullable) || "yes".equals(nullable) || "y".equals(nullable)
				|| "Y".equals(nullable) || "f".equals(nullable))
			return "Y";
		if ("NO".equals(nullable) || "N".equals(nullable) || "no".equals(nullable)
				|| "n".equals(nullable) || "t".equals(nullable))
			return "N";
		else
			return null;
	}

	public static String getNullString(String nullable) {
		if (StringUtils.isBlank(nullable))
			return "";
		else
			return nullable;
	}

	public static String getV(String s) {
		return (new StringBuilder("'")).append(s).append("'").toString();
	}

	// 字段类型
	public static void formatFieldClassType(ColumnInfo columnt) {
		String fieldType = columnt.getFieldType();
		String scale = columnt.getScale();
		columnt.setClassType("inputxt");
		if ("N".equals(columnt.getNullable()))
			columnt.setOptionType("*");
		if ("datetime".equals(fieldType) || fieldType.contains("time"))
			columnt.setClassType("easyui-datetimebox");
		else if ("date".equals(fieldType))
			columnt.setClassType("easyui-datebox");
		else if (fieldType.contains("int"))
			columnt.setOptionType("n");
		else if ("number".equals(fieldType)) {
			if (StringUtils.isNotBlank(scale) && Integer.parseInt(scale) > 0)
				columnt.setOptionType("d");
		} else if ("float".equals(fieldType) || "double".equals(fieldType)
				|| "decimal".equals(fieldType))
			columnt.setOptionType("d");
		else if ("numeric".equals(fieldType))
			columnt.setOptionType("d");
	}

	/**
	 * 格式化类型
	 * 
	 * @param dataType
	 * @param precision
	 * @param scale
	 * @return
	 */
	public static String formatDataType(String dataType, String precision, String scale) {
		if (dataType.contains("char"))
			dataType = "String";
		else if (dataType.contains("int"))
			dataType = "Integer";
		else if (dataType.contains("float"))
			dataType = "Float";
		else if (dataType.contains("double"))
			dataType = "Double";
		else if (dataType.contains("number")) {
			if (StringUtils.isNotBlank(scale) && Integer.parseInt(scale) > 0)
				dataType = "BigDecimal";
			else if (StringUtils.isNotBlank(precision) && Integer.parseInt(precision) > 10)
				dataType = "Long";
			else
				dataType = "Integer";
		} else if (dataType.contains("decimal"))
			dataType = "BigDecimal";
		else if (dataType.contains("date"))
			dataType = "Date";
		else if (dataType.contains("time"))
			dataType = "Date";
		else if (dataType.contains("clob"))
			dataType = "Clob";
		else if (dataType.contains("numeric"))
			dataType = "BigDecimal";
		else
			dataType = "Object";
		if ("java.lang.Object".equals(dataType)) {
			System.err.println("warn  datatype unkonwn....");
		}
		return dataType;
	}

	// 格式化列名 hello_world to helloWorld
	public static String formatField(String field) {
		String strs[] = field.split("_");
		field = "";
		int m = 0;
		for (int length = strs.length; m < length; m++)
			if (m > 0) {
				String tempStr = strs[m].toLowerCase();
				tempStr = (new StringBuilder(String.valueOf(tempStr.substring(0, 1).toUpperCase())))
						.append(tempStr.substring(1, tempStr.length())).toString();
				field = (new StringBuilder(String.valueOf(field))).append(tempStr).toString();
			} else {
				field = (new StringBuilder(String.valueOf(field))).append(strs[m].toLowerCase())
						.toString();
			}

		return field;
	}

	public static String formatFieldCapital(String field) {
		String strs[] = field.split("_");
		field = "";
		int m = 0;
		for (int length = strs.length; m < length; m++)
			if (m > 0) {
				String tempStr = strs[m].toLowerCase();
				tempStr = (new StringBuilder(String.valueOf(tempStr.substring(0, 1).toUpperCase())))
						.append(tempStr.substring(1, tempStr.length())).toString();
				field = (new StringBuilder(String.valueOf(field))).append(tempStr).toString();
			} else {
				field = (new StringBuilder(String.valueOf(field))).append(strs[m].toLowerCase())
						.toString();
			}

		field = (new StringBuilder(String.valueOf(field.substring(0, 1).toUpperCase()))).append(
				field.substring(1)).toString();
		return field;
	}

}
