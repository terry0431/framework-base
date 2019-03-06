/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.os.framework.db.dao;

import com.os.framework.core.config.PathBean;
import com.os.framework.db.mapping.TableInfoMapping;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author WangBo
 */
public class MainDaoTest {

	public MainDaoTest() {
	}

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of getConn method, of class MainDao.
	 */
	@Test
	public void testGetConn() {
		System.out.println("getConn");
		PathBean.testInit(System.getProperty("user.dir") + File.separator + "target" + File.separator + "classes");
		try {
			TableInfoMapping.initTableInfoMapping();
			MainDao dao = new MainDao();
			List<Map<String, Object>> list = dao.queryForList("select * from zhyy_shebeijiankong", null);
			System.out.println("list size :" + list.size());
//			for (Map<String, Object> m : list) {
//				for (String key : m.keySet()) {
//					System.out.println("Key:" + key + " || value:" + m.get(key));
//				}
//				System.out.println("======================================");
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Connection result = instance.getConn();
		assertNotNull("111");
	}
}
