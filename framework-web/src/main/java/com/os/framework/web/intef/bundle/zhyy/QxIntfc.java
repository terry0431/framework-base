package com.os.framework.web.intef.bundle.zhyy;

import com.os.framework.db.dao.MainDao;
import com.os.framework.web.util.url.URLUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QxIntfc {

    public QxIntfc() {
    }

    @RequestMapping(value = {"/zhyy/qx/getdata"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public void getdata(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json; charset=utf-8");
        
        MainDao mainDao = new MainDao();
        String btime = request.getParameter("btime");
        String etime = request.getParameter("etime");
//        String url = "http://api.data.cma.cn:8090/api?userId=533965984939DIkhE&pwd=O5o07R2&dataFormat=json&interfaceId=getSurfEleByTimeRangeAndStaID&timeRange=[" + btime + "," + etime + "]        ";
//        String url = "http://api.data.cma.cn:8090/api?userId=533965984939DIkhE&pwd=O5o07R2&dataFormat=json&interfaceId=getSurfEleByTimeRangeAndStaID&timeRange=[" + btime+"," + etime + "]&staIDs=54584&elements=Year,Mon,Day,Hour,PRS,PRS_Sea,PRS_Max,PRS_Min,TEM,TEM_Max,TEM_min,RHU,RHU_Min,VAP,PRE_1h,WIN_D_INST_Max,WIN_S_Max,WIN_D_S_Max,WIN_S_Avg_2mi,WIN_D_Avg_2mi,WEP_Now,WIN_S_Inst_Max&dataCode=SURF_CHN_MUL_HOR";
        String url = "http://api.data.cma.cn:8090/api?userId=536714300811iS0WL&pwd=FmywmnC&dataFormat=json&interfaceId=getSurfEleByTimeRangeAndStaID&timeRange=[" + btime+"," + etime + "]&staIDs=54584&elements=Year,Mon,Day,Hour,PRS,PRS_Sea,PRS_Max,PRS_Min,TEM,TEM_Max,TEM_min,RHU,RHU_Min,VAP,PRE_1h,WIN_D_INST_Max,WIN_S_Max,WIN_D_S_Max,WIN_S_Avg_2mi,WIN_D_Avg_2mi,WEP_Now,WIN_S_Inst_Max&dataCode=SURF_CHN_MUL_HOR";
//536714300811iS0WL&pwd=FmywmnC
        String json = URLUtil.readFileByUrl(url);
        PrintWriter out = response.getWriter();
        out.print(json);
    }
}