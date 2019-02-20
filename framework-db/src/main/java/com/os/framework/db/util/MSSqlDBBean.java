package com.os.framework.db.util;

import java.sql.PreparedStatement;
import java.util.Map;

import com.os.framework.db.dao.Dao;
import com.os.framework.db.mod.FieldMod;
import com.os.framework.db.mod.TableMod;

public class MSSqlDBBean extends DBBean {

	@Override
	public PreparedStatement getPageListSql(String sql, Object[] values, Integer pnum, Integer maxsize, Dao dao) {
		return null;
	}

	@Override
	public String getDBTime(Dao dao) {
		return "";
	}

	@Override
	public int getUsageConut(Dao dao) {
		return 0;
	}

	@Override
	public PreparedStatement insertSQL(String tableName, Map<String, Object> fieldvalue, Dao dao) {
		return null;
	}

	@Override
	public String getViewValue(String dbtype, Object value) {

		return "";

	}

	@Override
	public Map<String, TableMod> getTables(String dataName) {
		return null;
	}

	@Override
	public int getTableCount(Dao dao, String tname) {
		return 0;
	}

	@Override
	public boolean createTable(Dao dao, TableMod tmod) {
		return false;

	}
}
