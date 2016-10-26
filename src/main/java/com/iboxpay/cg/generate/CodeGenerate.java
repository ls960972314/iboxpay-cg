package com.iboxpay.cg.generate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.iboxpay.cg.pojo.ColumnInfo;
import com.iboxpay.cg.pojo.CreateFileProperty;
import com.iboxpay.cg.util.CodeResourceUtil;
import com.iboxpay.cg.util.DateUtils;
import com.iboxpay.cg.util.MainFieldDef;
import com.iboxpay.cg.util.ReadDBUtil;

/**
 * 
 * @author dumengchao
 */
public class CodeGenerate implements ICallBack {

	private static String[] foreignKeys;
	private static CreateFileProperty createFileProperty;
	private List<ColumnInfo> entityColumns;
	private List<ColumnInfo> jspColumns;
	private ReadDBUtil readDBUtil;

	public CodeGenerate() {
		super();
	}

	public CodeGenerate(CreateFileProperty createFile) {
		createFileProperty = createFile;
		this.entityColumns = new ArrayList<ColumnInfo>();
		this.jspColumns = new ArrayList<ColumnInfo>();
		this.readDBUtil = new ReadDBUtil();
	}

	/**
	 * 生成文件
	 */
	public void generateToFile() {
		System.out.println((new StringBuilder("----start[生成:")).append(createFileProperty.getTableName()).append("]------- ").toString());

		CodeFactory codeFactory = new CodeFactory();
		codeFactory.setCallBack(this);
		if (createFileProperty.isEntityFlag())
			codeFactory.invoke("entityTemplate.ftl", "entity");
		if (createFileProperty.isVoFlag())
			codeFactory.invoke("entityVOTemplate.ftl", "vo");
		if (createFileProperty.isServiceImplFlag())
			codeFactory.invoke("serviceImplTemplate.ftl", "serviceImpl");
		if (createFileProperty.isServiceIFlag())
			codeFactory.invoke("serviceTemplate.ftl", "service");
		if (createFileProperty.isActionFlag())
			codeFactory.invoke("controllerTemplate.ftl", "controller");
		if (createFileProperty.isDaoFlag())
			codeFactory.invoke("daoTemplate.ftl", "dao");
		if (createFileProperty.isDaoImplFlag())
			codeFactory.invoke("daoImplTemplate.ftl", "daoImpl");
		if (createFileProperty.isMapperFlag()) {
			codeFactory.invoke("mapperTemplate.ftl", "mapper");
		}
		// // 保存资源
		// readDBUtil.saveResource(createFileProperty, 0);
		// readDBUtil.saveResource(createFileProperty, 1);
		System.out.println("-----------------------------生成成功---------------------------------");
	}

	/**
	 * 执行回调
	 */
	public Map<String, Object> execute() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(MainFieldDef.BUSSI_PACKAGE, CodeResourceUtil.bussiPackage);
		data.put(MainFieldDef.ENTITY_PACKAGE, CodeFactory.getBusnissPack());
		data.put(MainFieldDef.ENTITY_NAME, createFileProperty.getEntityName());
		data.put(MainFieldDef.TABLE_NAME, createFileProperty.getTableName().toUpperCase());
		data.put(MainFieldDef.PRIMARY_KEY_POLICY, createFileProperty.getPrimaryKeyPolicy());
		if(createFileProperty.getSequenceCode() != null) {
			data.put(MainFieldDef.SEQUENCE_CODE, createFileProperty.getSequenceCode().toUpperCase());
		}
		data.put(MainFieldDef.FTL_DESCRIPTION, createFileProperty.getFtlDescription());
		data.put(MainFieldDef.FOREIGN_KEYS, foreignKeys);
		data.put(MainFieldDef.ID_GENERATE_STRATEGY, createFileProperty.getIdStategy());

		data.put(MainFieldDef.CREAT_TIME, DateUtils.dateToString(new Date()));
		try {
			Map<String, String> pkData = readDBUtil.setTableIdToPublic(createFileProperty.getTableName());

			CodeResourceUtil.TABLE_ID = pkData.get("tableNameId");
			data.put(MainFieldDef.TABLE_DB_ID, pkData.get("tableDBNameId"));
			data.put(MainFieldDef.TABLE_ID, CodeResourceUtil.TABLE_ID);
			data.put(MainFieldDef.SELECT_BY_PK_WHERE_CLAUSE, CodeResourceUtil.TABLE_ID + "= #{" + CodeResourceUtil.TABLE_ID +"}");
			String dbNameListByComma = readDBUtil.getDbNameDescByComma(createFileProperty.getTableName());
			data.put(MainFieldDef.DBNAME_LIST_BY_COMMA, dbNameListByComma);
			entityColumns = readDBUtil.readOriginalTableColumn(createFileProperty.getTableName());
			data.put(MainFieldDef.ENTITY_COLUMNS, entityColumns);

			// 找到主键类型并设置
			Iterator<ColumnInfo> iterator = entityColumns.iterator();
			while (iterator.hasNext()) {
				ColumnInfo info = (ColumnInfo) iterator.next();
				if (info.getFieldName().toLowerCase().equals(CodeResourceUtil.TABLE_ID.toLowerCase())) {
					data.put(MainFieldDef.PRIMARY_KEY_TYPE, info.getFieldType());
					break;
				}
			}

			data.put(MainFieldDef.MYBATIS_TYPE, getMyBatisType(String.valueOf(data.get(MainFieldDef.PRIMARY_KEY_TYPE))));
		} catch (Exception e) {
			System.exit(-1);
		}
		data.put("serialVersionUID", String.valueOf(1));
		return data;
	}

	private Object getMyBatisType(String primaryKeyType) {
		if ("Long".equals(primaryKeyType)) {
			return "long";
		} else if ("Integer".equals(primaryKeyType)) {
			return "int";
		} else if ("String".equals(primaryKeyType)) {
			return "string";
		}
		return primaryKeyType;
	}
}
