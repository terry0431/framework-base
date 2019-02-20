package com.os.framework.db.mod;

import java.util.Map;

/**
 *
 * @author wangbo
 */
public class TableMod {

	private String tableName = "";	//表名
	private String PKName = "";		//主键字段名
	private String PKType = "";		//主键类型

	public String getPKType() {
		return PKType;
	}

	public void setPKType(String pKType) {
		PKType = pKType;
	}

	private Map<String, FieldMod> fieldMods = null;

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the fieldObj
	 */
	public Map<String, FieldMod> getFieldMods() {
		return fieldMods;
	}


	public void setFieldMods(Map<String, FieldMod> fieldMods) {
		this.fieldMods = fieldMods;
	}

	/**
	 * @return the PKName
	 */
	public String getPKName() {
		return PKName;
	}

	/**
	 * @param PKName the PKName to set
	 */
	public void setPKName(String PKName) {
		this.PKName = PKName;
	}

}
