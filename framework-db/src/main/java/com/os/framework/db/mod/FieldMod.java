package com.os.framework.db.mod;

/**
 * @author wangbo
 *
 */
public class FieldMod {

	private String fieldName = "";	//字段名称
	private String fieldType = "";	//字段类型
	private int fieldLength = 0;	//字段长度
	private int isNull = 0;			//是否可为空
	private String remark = "";		//字段描述

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the fieldType
	 */
	public String getFieldType() {
		return fieldType;
	}

	/**
	 * @param fieldType the fieldType to set
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * @return the fieldLength
	 */
	public int getFieldLength() {
		return fieldLength;
	}

	/**
	 * @param fieldLength the fieldLength to set
	 */
	public void setFieldLength(int fieldLength) {
		this.fieldLength = fieldLength;
	}

	/**
	 * @return the isNull
	 */
	public int getIsNull() {
		return isNull;
	}

	/**
	 * @param isNull the isNull to set
	 */
	public void setIsNull(int isNull) {
		this.isNull = isNull;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
