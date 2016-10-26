package com.iboxpay.cg.pojo;

/**
 * 
 * @author dumengchao
 */
public class ColumnInfo {

	private String fieldDbName;
	private String dbNameType;
	private String fieldName;
	private String filedComment;
	private String fieldType;
	private String myBatisType;
	private String insertUpdateMyBatisField;
	private String classType;
	private String optionType;
	private String charmaxLength;
	private String precision;
	private String scale;
	private String nullable;
	
	public ColumnInfo() {
		filedComment = "";
		fieldType = "";
		classType = "";
		dbNameType = "";
		optionType = "";
		charmaxLength = "";
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getPrecision() {
		return precision;
	}

	public String getScale() {
		return scale;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFiledComment() {
		return filedComment;
	}

	public void setFiledComment(String filedComment) {
		this.filedComment = filedComment;
	}

	public String getCharmaxLength() {
		if (charmaxLength == null || "0".equals(charmaxLength))
			return "";
		else
			return charmaxLength;
	}

	public void setCharmaxLength(String charmaxLength) {
		this.charmaxLength = charmaxLength;
	}

	public String getFieldDbName() {
		return fieldDbName;
	}

	public void setFieldDbName(String fieldDbName) {
		this.fieldDbName = fieldDbName;
	}

	public String getDbNameType() {
		return dbNameType;
	}

	public void setDbNameType(String dbNameType) {
		this.dbNameType = dbNameType;
	}

	public String getMyBatisType() {
		return myBatisType;
	}

	public void setMyBatisType(String myBatisType) {
		this.myBatisType = myBatisType;
	}

	public String getInsertUpdateMyBatisField() {
		return insertUpdateMyBatisField;
	}

	public void setInsertUpdateMyBatisField(String insertUpdateMyBatisField) {
		this.insertUpdateMyBatisField = insertUpdateMyBatisField;
	}
}
