package com.iboxpay.cg.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.iboxpay.cg.pojo.ColumnInfo;
import com.iboxpay.cg.pojo.CreateFileProperty;
import com.iboxpay.cg.pojo.TableConvert;

/**
 * 
 * @author dumengchao
 */
public class ReadDBUtil {

	private Connection conn = null;
	private Statement stmt = null;
	public static String checkTableSql = "";
	private ResultSet rs = null;
	private ColumnInfo column = null;

	public ReadDBUtil() {
	}

	public List<ColumnInfo> readTableColumn(String tableName) {
		List<ColumnInfo> columnList = new ArrayList<ColumnInfo>();
		try {
			getConnection();
//			getSql(tableName);
			stmt = conn.createStatement(1005, 1007);
			rs = stmt.executeQuery(checkTableSql);

			while (rs.next()) {
				column = new ColumnInfo();
				if (CodeResourceUtil.fieldHumpSet) {
					column.setFieldName(CodeStringUtils.formatField(rs.getString(1).toLowerCase()));
				} else {
					column.setFieldName(rs.getString(1).toLowerCase());
				}

				column.setFieldDbName(rs.getString(1).toUpperCase());

				if (column.getFieldDbName().toLowerCase()
						.equals(CodeResourceUtil.TABLE_ID.toLowerCase()))
					continue;
				column.setFieldType(CodeStringUtils.formatField(rs.getString(2).toLowerCase()));
				column.setPrecision(rs.getString(4));
				column.setScale(rs.getString(5));
				column.setCharmaxLength(rs.getString(6));
				column.setNullable(CodeStringUtils.getNullAble(rs.getString(7)));
				CodeStringUtils.formatFieldClassType(column);
				column.setFiledComment(StringUtils.isBlank(rs.getString(3)) ? column.getFieldName()
						: rs.getString(3));
				columnList.add(column);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}

		return columnList;
	}

	public Map<String, String> setTableIdToPublic(String tableName) {
		try {
			getConnection();
			String s = "select col.column_name, ut.DATA_TYPE from user_constraints con, user_cons_columns col ,user_tab_cols ut " +
					   " where con.constraint_name = col.constraint_name and col.COLUMN_NAME = ut.COLUMN_NAME  and con.constraint_type = 'P' and col.table_name = upper('"+ tableName + "')";
			PreparedStatement ps = conn.prepareStatement(s);
			rs = ps.executeQuery(s);
			Map<String, String> map = null;
			if(rs.next()) {
				map = new HashMap<String, String>();
				map.put("tableDBNameId", rs.getString(1).toUpperCase());
				map.put("tableNameId", CodeStringUtils.formatField(rs.getString(1).toLowerCase()));
				map.put("tableDBNameType", formatDBType(rs.getString(2).toUpperCase()));
			}
			return map;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	private String formatDBType(String upperCase) {
		if("NUMBER".equals(upperCase)) {
			return "DECIMAL";
		} else if("INTGER".equals(upperCase)) {
			return "DECIMAL";
		} else if("DOUBLE".equals(upperCase)) {
			return "DECIMAL";
		} else if("LONG".equals(upperCase)) {
			return "DECIMAL";
		} else if("VARCHAR2".equals(upperCase)) {
			return "VARCHAR";
		} else if(upperCase.startsWith("TIMESTAMP")) {
			return "DATE";
		}  
		return upperCase;
	}

	public List<ColumnInfo> readOriginalTableColumn(String tableName) {
		List<ColumnInfo> columnList = new ArrayList<ColumnInfo>();
		try {
			getConnection();
//			getSql(tableName);
			stmt = conn.createStatement(1005, 1007);
			rs = stmt.executeQuery(checkTableSql);
			while (rs.next()) {
				column = new ColumnInfo();
				if (CodeResourceUtil.fieldHumpSet) {
					column.setFieldName(CodeStringUtils.formatField(rs.getString(1).toLowerCase()));
				} else {
					column.setFieldName(rs.getString(1).toLowerCase());
				}
				column.setFieldDbName(rs.getString(1).toUpperCase());
				column.setDbNameType(rs.getString(2));
				column.setPrecision(CodeStringUtils.getNullString(rs.getString(4)));
				column.setScale(CodeStringUtils.getNullString(rs.getString(5)));
				column.setCharmaxLength(CodeStringUtils.getNullString(rs.getString(6)));
				column.setNullable(CodeStringUtils.getNullAble(rs.getString(7)));
				column.setFieldType(CodeStringUtils.formatDataType(rs.getString(2).toLowerCase(),
						column.getPrecision(), column.getScale()));
				column.setMyBatisType(formatDBType(rs.getString(2).toUpperCase()));
				column.setInsertUpdateMyBatisField("#{" + column.getFieldName() + ",jdbcType=" + column.getMyBatisType() + "}");
				CodeStringUtils.formatFieldClassType(column);
				column.setFiledComment(StringUtils.isBlank(rs.getString(3)) ? column.getFieldName()
						: rs.getString(3));
				columnList.add(column);
			}
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}
		return columnList;
	}

	public String getDbNameDescByComma(String tableName) throws SQLException, ClassNotFoundException {
		getConnection();
//		getSql(tableName);
		stmt = conn.createStatement(1005, 1007);
		rs = stmt.executeQuery(checkTableSql);
		StringBuffer sb = new StringBuffer();
		while (rs.next()) {
			sb.append(rs.getString(1).toUpperCase() + ",");
		}
		
		return sb.toString().substring(0, sb.length() - 1);
	}

	/**
	 * 
	 * @param tableName
	 * @return
	 */
	public boolean checkTableExist(CreateFileProperty fileProp) {
		try {
			int fieldNum;
//			getCheckTableExistSql(fileProp.getTableName());
			getConnection();
			stmt = conn.createStatement(1005, 1007);
			rs = stmt.executeQuery(checkTableSql);
			if(rs.next()) {
				fieldNum = rs.getRow();
				return fieldNum > 0;
			}
			return false;
		} catch (Exception e) {
			return false;
		} finally {
			close();
		}
	}
	
	private void getCheckTableExistSql(String tableName) {
		checkTableSql = "select count(*) from user_tables t where t.TABLE_NAME = upper('"+tableName+"')";
	}

	/**
	 * 
	 * @param tableName
	 * @return
	 */
	public boolean checkTablePKIsExist(String tableName) {
		try {
			int fieldNum;
			getConnection();
			stmt = conn.createStatement(1005, 1007);
			rs = stmt.executeQuery(checkTableSql);
			if(rs.next()) {
				fieldNum = rs.getInt(1);
				return fieldNum > 0;
			}
			return false;
		} catch (Exception e) {
			return false;
		} finally {
			close();
		}
	}
//
//	private void getCheckPKIsExistSql(String tableName) {
//		checkTableSql = "select count(*) from user_constraints con, user_cons_columns col"
//				+ " where con.constraint_name = col.constraint_name    and con.constraint_type = 'P'    and col.table_name = upper('"+tableName +"')";
//		
//	}

	private void getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(CodeResourceUtil.DIVER_NAME);
		conn = DriverManager.getConnection(CodeResourceUtil.URL, CodeResourceUtil.USERNAME,
				CodeResourceUtil.PASSWORD);
	}

//	/**
//	 * @param tableName
//	 * @throws ClassNotFoundException
//	 * @throws SQLException
//	 */
//	private void getSql(String tableName) {
//		if (CodeResourceUtil.DATABASE_TYPE.equals("oracle"))
//			checkTableSql = MessageFormat
//					.format(" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from "
//							+ " user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}",
//							new Object[] { TableConvert.getV(tableName.toUpperCase()) });
//	}

	private void close() {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
				System.gc();
			}
			if (conn != null) {
				conn.close();
				conn = null;
				System.gc();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void initSql(String item, String tableName) {
		if("mysql".equals(item)) {
			checkTableSql = MessageFormat.format("select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0}", new Object[] { TableConvert.getV(tableName.toUpperCase()) });
		} else if("oracle".equals(item)) {
			checkTableSql = MessageFormat.format(" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}", new Object[] { TableConvert.getV(tableName.toUpperCase()) });
		} else if("postgresql".equals(item)) {
			checkTableSql = MessageFormat.format("SELECT a.attname AS  field,t.typname AS type,col_description(a.attrelid,a.attnum) as comment,null as column_precision,null as column_scale,null as Char_Length,a.attnotnull  FROM pg_class c,pg_attribute  a,pg_type t  WHERE c.relname = {0} and a.attnum > 0  and a.attrelid = c.oid and a.atttypid = t.oid  ORDER BY a.attnum ", new Object[] { TableConvert.getV(tableName.toLowerCase()) });
		} else if("sqlserver".equals(item)) {
			checkTableSql = "SELECT  column_name=a.name, data_type=b.name,column_comment=isnull(g.[value],''),numeric_precision=COLUMNPROPERTY(a.id,a.name,'PRECISION'),numeric_scale=isnull(COLUMNPROPERTY(a.id,a.name,'Scale'),0),character_maximum_length=a.length,nullable=case   when   a.isnullable=1   then   'y' else   'n'   end,xxxx=isnull(e.text,'') " + " FROM   syscolumns   a left   join   systypes   b   on   a.xusertype=b.xusertype inner   join   sysobjects   d   on   a.id=d.id  " + "   and   d.xtype='U'   and     d.name<>'dtproperties' left   join   syscomments   e   on   a.cdefault=e.id " + " left   join   sys.extended_properties   g   on   a.id=g.major_id   and   a.colid=g.minor_id left   join  " + " sys.extended_properties   f   on   d.id=f.major_id   and   f.minor_id=0 where   d.name=" + TableConvert.getV(tableName.toLowerCase()) + "     order   by   a.id,a.colorder ";
		}
	}

}
