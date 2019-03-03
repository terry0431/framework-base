package com.os.framework.vo.manager;

import java.util.List;
import java.util.Map;

public class User {

    private String id;
    private String name;//姓名
    private String username;//账号
    private String password;//密码	
    private String mac;//网卡地址
    private String bindMac;//绑定mac
    //private String lv1orgid;//所属机构顶层id   暂时没有用	
    private List<Map> menus;	//该用户菜单
    private List<Map> apps;	//该用户可登录应用
    private List<Map> appdatas;	//该用户可查看数据的应用
    /*机构信息表*/
    private String orgid;//组织机构id
    private String jname; //机构名称
    private String jtype;//机构类型
    private String jzuhihao;//组织号
    private String pid;//机构父id
    private String ids;//机构下属id字符串 用,号隔开 的字符串
    private String IPS;//机器ip地址

    private String type;//账户类型 管理账户 | 养殖场账户
    private String yzcid;// 账户类型为 2 时 养殖场账户,养殖场id 
    public String getIPS() {
        return IPS;
    }

    public void setIPS(String iPS) {
        IPS = iPS;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public String getJtype() {
        return jtype;
    }

    public void setJtype(String jtype) {
        this.jtype = jtype;
    }

    public String getJzuhihao() {
        return jzuhihao;
    }

    public void setJzuhihao(String jzuhihao) {
        this.jzuhihao = jzuhihao;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }
//	public String getLv1orgid() {
//		return lv1orgid;
//	}
//	public void setLv1orgid(String lv1orgid) {
//		this.lv1orgid = lv1orgid;
//	}

    public List<Map> getMenus() {
        return menus;
    }

    public void setMenus(List<Map> menus) {
        this.menus = menus;
    }

    public List<Map> getApps() {
        return apps;
    }

    public void setApps(List<Map> apps) {
        this.apps = apps;
    }

    public List<Map> getAppdatas() {
        return appdatas;
    }

    public void setAppdatas(List<Map> appdatas) {
        this.appdatas = appdatas;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getBindMac() {
        return bindMac;
    }

    public void setBindMac(String bindMac) {
        this.bindMac = bindMac;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the yzcid
     */
    public String getYzcid() {
        return yzcid;
    }

    /**
     * @param yzcid the yzcid to set
     */
    public void setYzcid(String yzcid) {
        this.yzcid = yzcid;
    }
}
