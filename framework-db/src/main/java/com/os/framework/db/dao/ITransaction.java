package com.os.framework.db.dao;

public interface ITransaction {
	
	public int execute(MainDao dao)throws Exception;
}
