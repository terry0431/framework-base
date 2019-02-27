package com.os.framework.web.bean;
import core.config.ConfigBean;
import core.exception.DBException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.os.framework.db.dao.MainDao;
import com.os.framework.web.mod.system.User;



public class PermissionBean {
	
	/**
	 * 根据用户id获取全部角色
	 * @param userid	用户id
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getRolelistByUserid(String userid){
		MainDao dao = new MainDao();
		List list = null;
		try {
			list = dao.queryForList("SELECT t1.* FROM sys_role t1,sys_role_user t2 WHERE t2.user_id = ? AND t1.id=t2.role_id", new Object[]{userid});
		} catch (Exception e) {
			throw new DBException(e);
		}
		return list;
	}
	
	/**
	 * 根据用户id获取全部角色
	 * @param userid	用户id
	 * @param roletype	角色类型
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getRolelistByUserid(String userid,String roletype){
		MainDao dao = new MainDao();
		List list = null;
		try {
			list = dao.queryForList("select t1.* from sys_role t1,sys_role_user t2 where t2.user_id = ? and t1.id=t2.role_id and t1.r_type=?", new Object[]{userid,roletype});
		} catch (Exception e) {
			throw new DBException(e);
		}
		return list;
	}
	
	/**
	 * 根据用户id获取菜单
	 * @param userid 用户id
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getMenulistByUser(String userid){
		MainDao dao = new MainDao();
		List list = null;
		try {
			List<Map> rolelist = getRolelistByUserid(userid);
			if(rolelist != null && rolelist.size() > 0){
				String[] roleids = new String[rolelist.size()];
				int i = 0;
				for(Map map : rolelist){
					roleids[i] = map.get("id").toString();
					i ++;
				}
				list = getMenulistByRoles(roleids);
			}else{
				return null;
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return list;
	}
	
	/**
	 * 根据用户id获取菜单
	 * @param userid 用户id
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getMenulistByUser(String userid,String level){
		MainDao dao = new MainDao();
		List list = null;
		try {
			if(userid.equals("-1")){
				list = dao.queryForList("select * from sys_menu where m_level=? order by m_num", new Object[]{level});
			}else{
				List<Map> rolelist = getRolelistByUserid(userid);
				if(rolelist != null && rolelist.size() > 0){
					String[] roleids = new String[rolelist.size()];
					int i = 0;
					for(Map map : rolelist){
						roleids[i] = map.get("id").toString();
						i ++;
					}
					list = getMenulistByRoles(roleids,level);
				}else{
					return new ArrayList();
				}
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return list;
	}
	
	/**
	 * 根据用户id获取菜单
	 * @param userid 用户id
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getMenulistByUserAndParent(String userid,String parentid){
		MainDao dao = new MainDao();
		List list = null;
		try {
			if(userid.equals("-1")){
				list = dao.queryForList("select * from sys_menu where m_parent_id=? order by m_num", new Object[]{parentid});
			}else{
				List<Map> rolelist = getRolelistByUserid(userid);
				if(rolelist != null && rolelist.size() > 0){
					String[] roleids = new String[rolelist.size()];
					int i = 0;
					for(Map map : rolelist){
						roleids[i] = map.get("id").toString();
						i ++;
					}
					list = getMenulistByRolesAndParent(roleids,parentid);
				}else{
					return null;
				}
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return list;
	}

	
	/**
	 * 根据角色id获取菜单
	 * @param roleid
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getMenulistByRoles(Object[] roleid){
		MainDao dao = new MainDao();
		List list = null;
		try {
			String instr = "";
			int i = 0;
			for(Object role : roleid){
				if(instr.equals("")){
					instr += role;
				}else{
					instr += "," + role;
				}
				i ++;
			}
			list = dao.queryForList("SELECT DISTINCT t1.* FROM sys_menu t1,sys_role_menu t2 WHERE t2.role_id IN ("+instr+") AND t1.id=t2.menu_id AND t1.m_enable=? ORDER BY t1.m_num ", new Object[]{1});
		} catch (Exception e) {
			throw new DBException(e);
		}
		return list;
	}
	

	/**
	 * 根据角色id获取菜单
	 * @param roleid	角色id
	 * @param level		层级
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getMenulistByRoles(Object[] roleid,String level){
		MainDao dao = new MainDao();
		List list = null;
		try {
			String instr = "";
			int i = 0;
			for(Object role : roleid){
				if(instr.equals("")){
					instr += role;
				}else{
					instr += ","+role;
				}
				i ++;
			}
			list = dao.queryForList("select distinct t1.* from sys_menu t1,sys_role_menu t2 where t2.role_id in ("+instr+") and t1.id=t2.menu_id and t1.m_level=? and t1.m_enable=? order by t1.m_num ", new Object[]{level,1});
		} catch (Exception e) {
			throw new DBException(e);
		}
		return list;
	}
	
	/**
	 * 根据角色id & 菜单父id返回菜单
	 * @param roleid
	 * @param parentid
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getMenulistByRolesAndParent(Object[] roleid,String parentid){
		MainDao dao = new MainDao();
		List list = null;
		try {
			String instr = "";
			int i = 0;
			for(Object role : roleid){
				if(instr.equals("")){
					instr += role;
				}else{
					instr += ","+role;
				}
				i ++;
			}
			list = dao.queryForList("select distinct t1.* from sys_menu t1,sys_role_menu t2 where t2.role_id in ("+instr+") and t1.id=t2.menu_id and t1.m_parent_id=? and t1.m_enable=? order by t1.m_num ", new Object[]{parentid,1});
		} catch (Exception e) {
			throw new DBException(e);
		}
		return list;
	}

	
	/**
	 * 根据模块父节点获取子节点
	 * @param parentid
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getMenulistByParent(String parentid){
		MainDao dao = new MainDao();
		List list = null;
		try {
			list = dao.queryForList("select * from sys_menu where m_parent_id=? and m_enable=? order by m_num ", new Object[]{parentid,"1"});
		} catch (Exception e) {
			throw new DBException(e);
		}
		return list;
	}
	
	/**
	 * 根据用户id获取可登录子系统
	 * @param userid 用户id
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getApplistByUser(String userid){
		MainDao dao = new MainDao();
		List list = null;
		try {
			List<Map> rolelist = getRolelistByUserid(userid,ConfigBean.APPLICATIONROLE);
			if(rolelist != null && rolelist.size() > 0){
				String[] roleids = new String[rolelist.size()];
				int i = 0;
				for(Map map : rolelist){
					roleids[i] = map.get("id").toString();
					i ++;
				}
				list = getApplistByRoles(roleids);
			}else{
				return null;
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return list;
	}
	
	/**
	 * 根据角色id获取可登录子系统
	 * @param roleid
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getApplistByRoles(Object[] roleid){
		MainDao dao = new MainDao();
		List list = null;
		try {
			String instr = "";
			Object[] args = new Object[roleid.length + 1];
			int i = 0;
			for(Object role : roleid){
				args[i] = role;
				if(instr.equals("")){
					instr += "?";
				}else{
					instr += ",?";
				}
				i ++;
			}
			args[roleid.length] = ConfigBean.APPLICATIONROLE;
			list = dao.queryForList("select distinct t1.* from gf001 t1,gf014 t2 where t2.g_role_id in ("+instr+") and t2.g_authorityflag=? and  t1.id=t2.g_application_id", args);
		} catch (Exception e) {
			throw new DBException(e);
		}
		return list;
	}
	
	/**
	 * 根据用户id获取可查看数据的子系统
	 * @param userid 用户id
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getAppdatalistByUser(String userid){
		MainDao dao = new MainDao();
		List list = null;
		try {
			if(!userid.equals("-1")){
				List<Map> rolelist = getRolelistByUserid(userid,ConfigBean.FRAMEWORKROLE);
				if(rolelist != null && rolelist.size() > 0){
					String[] roleids = new String[rolelist.size()];
					int i = 0;
					for(Map map : rolelist){
						roleids[i] = map.get("id").toString();
						i ++;
					}
					list = getAppdatalistByRoles(roleids);
				}else{
					return null;
				}
			}else{
				list = dao.queryForList("select * from gf001 ",null);
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return list;
	}
	
	/**
	 * 根据角色id获取可查看数据的子系统
	 * @param roleid
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getAppdatalistByRoles(Object[] roleid){
		MainDao dao = new MainDao();
		List list = null;
		try {
			String instr = "";
			Object[] args = new Object[roleid.length + 1];
			int i = 0;
			for(Object role : roleid){
				args[i] = role;
				if(instr.equals("")){
					instr += "?";
				}else{
					instr += ",?";
				}
				i ++;
			}
			args[roleid.length] = ConfigBean.FRAMEWORKROLE;
			list = dao.queryForList("select distinct t1.* from gf001 t1,gf014 t2 where t2.g_role_id in ("+instr+") and t2.g_authorityflag=? and t1.id=t2.g_application_id",args);
		} catch (Exception e) {
			throw new DBException(e);
		}
		return list;
	}

	/**
	 * 根据角色id获取关联用户
	 * @param roleid 角色id
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getUserlistByRole(String roleid){
		//TODO  暂时用不到  需要实现动态更新用户session时用
		return null;
	}
	
	/**
	 * 根据组织机构id获取关联用户
	 * @param orgid 组织机构id
	 * @param showChild
	 * @return
	 * author wangbo
	 * @version 1.0
	 */
	public List getUserByOrg(String orgid,boolean showChild){
		//TODO 根据组织机构id获取关联用户   有需要该方法的请实现
		return null;
	}
	/**
	 * 根据用户id获取所在组织机构
	 * @param userid 用户id
	 * @return
	 * author wangbo
	 * @version 1.0
	 */

	public Map getOrgByuser(String userid) {
		MainDao dao = new MainDao();
		try {
			User user = new User();
			Map map  = dao.queryForMap("select * from sys_userinfo where id = ?", new Object[]{userid});
			user.setOrgid(map.get("org_id").toString());
			return getOrgByuser(user) ;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * 根据用户id获取所在组织机构
	 * @param user
	 * @return
	 */
	public Map getOrgByuser(User user) {
		MainDao dao = new MainDao();
		Map map = null;
		try {
			map = dao.queryForMap("select * from sys_org where id = ?", new Object[]{user.getOrgid()});
		} catch (Exception e) {
			throw new DBException(e);
		}
		return map;
	}
	
	/**
	 * 根据用户获取数据来源
	 * @param user
	 * @return
	 */
	public Map getDatasourcesDetatlByuser(String userid) {
		MainDao dao = new MainDao();
		Map map = null;
		try {
			map  = this.getOrgByuser(userid);
			if(map != null && !map.isEmpty()){
				map = dao.queryForMap("select * from mod_datasources_detail where id = ?", new Object[]{map.get("datasources_detail_id")});
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return map;
	}
	
	/**
	 * 根据用户获取数据来源
	 * @param user
	 * @return
	 */
	public Map getDatasourcesDetatlByuser(User user) {
		MainDao dao = new MainDao();
		Map map = null;
		try {
			map  = this.getOrgByuser(user);
			if(map != null && !map.isEmpty()){
				map = dao.queryForMap("select * from mod_datasources_detail where id = ?", new Object[]{map.get("datasources_detail_id")});
			}else{
				map = null;
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return map;
	}
	
	
	
	/**
	 * 根据组织机构path获得子系统id
	 * @param id 用户id
	 * @return
	 * author wangf
	 * @version 1.0
	 */
     public List getappbyorg(String id) {
    	 MainDao dao = new MainDao();
         Map pathMap=dao.queryForMap("select g_path from gf002 where id=?",new Object[]{id});
         String path = (String)pathMap.get("g_path");
         List list = null;
         list = dao.queryForList("select id from gf001 where g_orgid in (?)", new Object[]{path});

         return list;
     }
 	
}
