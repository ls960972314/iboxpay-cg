package com.iboxpay.cg.pojo;

/**
 * @author dumengchao
 */
public class CreateFileProperty {

	private String dbType;
	private String IdStategy;
	private String entityPackage;
	private String entityName;
	private String tableName;
	private String ftlDescription;
	private int fieldRowNum;
	private String primaryKeyPolicy = "sequence";
	private String sequenceCode;
	private String tableId;
	private boolean actionFlag;
	private boolean serviceIFlag;
	private boolean entityFlag;
	private boolean voFlag;
	private boolean pageFlag;
	private boolean serviceImplFlag;
	private boolean daoFlag;
	private boolean daoImplFlag;
	private boolean jspFlag;
	private boolean mapperFlag;

	public CreateFileProperty() {
	}

	public CreateFileProperty(String dbType, String IdStategy,
			String entityPackage, String entityName, String tableName,
			String ftlDescription, int fieldRowNum, String primaryKeyPolicy,
			String sequenceCode, boolean actionFlag, boolean serviceIFlag,
			boolean entityFlag, boolean modelFlag, boolean pageFlag,
			boolean serviceImplFlag, boolean daoFlag, boolean daoImplFlag,
			boolean jspFlag, boolean mapperFlag) {
		super();
		this.dbType = dbType;
		this.IdStategy = IdStategy;
		this.entityPackage = entityPackage;
		this.entityName = entityName;
		this.tableName = tableName;
		this.ftlDescription = ftlDescription;
		this.fieldRowNum = fieldRowNum;
		this.primaryKeyPolicy = primaryKeyPolicy;
		this.sequenceCode = sequenceCode;
		this.actionFlag = actionFlag;
		this.serviceIFlag = serviceIFlag;
		this.entityFlag = entityFlag;
		this.voFlag = modelFlag;
		this.pageFlag = pageFlag;
		this.serviceImplFlag = serviceImplFlag;
		this.daoFlag = daoFlag;
		this.daoImplFlag = daoImplFlag;
		this.jspFlag = jspFlag;
		this.mapperFlag = mapperFlag;
	}

	public String getEntityPackage() {
		return entityPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFtlDescription() {
		return ftlDescription;
	}

	public void setFtlDescription(String ftlDescription) {
		this.ftlDescription = ftlDescription;
	}

	public int getFieldRowNum() {
		return fieldRowNum;
	}

	public void setFieldRowNum(int fieldRowNum) {
		this.fieldRowNum = fieldRowNum;
	}

	public String getPrimaryKeyPolicy() {
		return primaryKeyPolicy;
	}

	public void setPrimaryKeyPolicy(String primaryKeyPolicy) {
		this.primaryKeyPolicy = primaryKeyPolicy;
	}

	public String getSequenceCode() {
		return sequenceCode;
	}

	public void setSequenceCode(String sequenceCode) {
		this.sequenceCode = sequenceCode;
	}

	public boolean isActionFlag() {
		return actionFlag;
	}

	public boolean isServiceIFlag() {
		return serviceIFlag;
	}

	public boolean isEntityFlag() {
		return entityFlag;
	}

	public boolean isPageFlag() {
		return pageFlag;
	}

	public boolean isServiceImplFlag() {
		return serviceImplFlag;
	}

	public void setActionFlag(boolean actionFlag) {
		this.actionFlag = actionFlag;
	}

	public void setServiceIFlag(boolean serviceIFlag) {
		this.serviceIFlag = serviceIFlag;
	}

	public void setEntityFlag(boolean entityFlag) {
		this.entityFlag = entityFlag;
	}

	public void setPageFlag(boolean pageFlag) {
		this.pageFlag = pageFlag;
	}

	public void setServiceImplFlag(boolean serviceImplFlag) {
		this.serviceImplFlag = serviceImplFlag;
	}

	public boolean isJspFlag() {
		return jspFlag;
	}

	public void setJspFlag(boolean jspFlag) {
		this.jspFlag = jspFlag;
	}

	public boolean isVoFlag() {
		return voFlag;
	}

	public void setVoFlag(boolean voFlag) {
		this.voFlag = voFlag;
	}

	public boolean isDaoFlag() {
		return daoFlag;
	}

	public void setDaoFlag(boolean daoFlag) {
		this.daoFlag = daoFlag;
	}

	public boolean isDaoImplFlag() {
		return daoImplFlag;
	}

	public void setDaoImplFlag(boolean daoImplFlag) {
		this.daoImplFlag = daoImplFlag;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public boolean isMapperFlag() {
		return mapperFlag;
	}

	public void setMapperFlag(boolean mapperFlag) {
		this.mapperFlag = mapperFlag;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getIdStategy() {
		return IdStategy;
	}

	public void setIdStategy(String idStategy) {
		IdStategy = idStategy;
	}

	@Override
	public String toString() {
		return "CreateFileProperty [entityPackage=" + entityPackage
				+ ", entityName=" + entityName + ", tableName=" + tableName
				+ ", ftlDescription=" + ftlDescription + ", fieldRowNum="
				+ fieldRowNum + ", primaryKeyPolicy=" + primaryKeyPolicy
				+ ", sequenceCode=" + sequenceCode + ", tableId=" + tableId
				+ ", actionFlag=" + actionFlag + ", serviceIFlag="
				+ serviceIFlag + ", entityFlag=" + entityFlag + ", modelFlag="
				+ voFlag + ", pageFlag=" + pageFlag + ", serviceImplFlag="
				+ serviceImplFlag + ", daoFlag=" + daoFlag + ", daoImplFlag="
				+ daoImplFlag + ", jspFlag=" + jspFlag + "]";
	}
}
