package com.os.framework.vo.transceriver;

import org.msgpack.annotation.Message;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: framework-base
 * @description: 设备接口
 * @author: wangbo
 * @create: 2019-02-26 21:17
 **/
@Message
public class RtuEquipment implements Serializable {
    /**
     * @return the servertime
     */
    public Date getServertime() {
        return servertime;
    }

    /**
     * @param servertime the servertime to set
     */
    public void setServertime(Date servertime) {
        this.servertime = servertime;
    }


//    private static final long serialVersionUID = 1L;

    private String rtuid = "";
    private String datatime = "";
    private Date servertime ;
    private List<Map> rtudi = null;
    private List<Map> rtudo = null;
    private List<Map> rtuai = null;
    private String longtime = "";

    public void formatVal(){
        Double d;
        if(this.rtuai != null && this.rtuai.size() > 0){
            for(Map m : rtuai){
                if(m.get("va") != null && !m.get("va").equals("")){
                    d = Double.valueOf(m.get("va").toString() );
                    m.put("va", Double.parseDouble(String.format("%.1f", d) ));
                }
            }
        }
    }

    /**
     * @return the rtuid
     */
    public String getRtuid() {
        return rtuid;
    }

    /**
     * @param rtuid the rtuid to set
     */
    public void setRtuid(String rtuid) {
        this.rtuid = rtuid;
    }

    /**
     * @return the datatime
     */
    public String getDatatime() {
        return datatime;
    }

    /**
     * @param datatime the datatime to set
     */
    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    /**
     * @return the rtudi
     */
    public List<Map> getRtudi() {
        return rtudi;
    }

    /**
     * @param rtudi the rtudi to set
     */
    public void setRtudi(List<Map> rtudi) {
        this.rtudi = rtudi;
    }

    /**
     * @return the rtudo
     */
    public List<Map> getRtudo() {
        return rtudo;
    }

    /**
     * @param rtudo the rtudo to set
     */
    public void setRtudo(List<Map> rtudo) {
        this.rtudo = rtudo;
    }

    /**
     * @return the rtuai
     */
    public List<Map> getRtuai() {
        return rtuai;
    }

    /**
     * @param rtuai the rtuai to set
     */
    public void setRtuai(List<Map> rtuai) {
        this.rtuai = rtuai;
    }

    /**
     * @return the longtime
     */
    public String getLongtime() {
        return longtime;
    }

    /**
     * @param longtime the longtime to set
     */
    public void setLongtime(String longtime) {
        this.longtime = longtime;
    }

    @Override
    public String toString(){
        return rtuid;
    }
}
