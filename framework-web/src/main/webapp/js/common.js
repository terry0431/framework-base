var basePath = (function getBaseUrl() {
    var url = window.location + "";
    var h = url.split("//");
    var x = h[1].split("/");
    return h[0] + "//" + window.location.host + "/" + x[1] + "/";
})();
function moneyFormat(s, n) {
    if (isNaN(s))return s;
    if(n==undefined){
        n=0;
    }
    n = n >= 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n);
    var l = s.split(".")[0].split("").reverse();
    var t = "";
    for (var i = 0; i < l.length; i ++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    var temp = s.indexOf(".")==-1?"":"."+s.split(".")[1];
    return t.split("").reverse().join("") +temp;
}
function exportExcel(html,columnNum,titleRowNum) {
    ExportExcel.exportExcel(html,columnNum,titleRowNum, function(data) {
        dwr.engine.openInDownload(data);
    });
}
function exportExcelFroData(titleinfo,list) {
    ExportExcel.exportExcelFroData(titleinfo,list, function(data) {
        dwr.engine.openInDownload(data);
    });
}

var iconImg = {
    whIcon:'<img style="cursor: pointer" src="' + basePath + 'common/style1/images/icon_20.gif"/>',
    checkBoxIcon:'<img style="cursor: pointer" src="' + basePath + 'common/style1/images/icon_6.gif"/>',
    editIcon:'<img style="cursor: pointer" src="' + basePath + 'common/style1/images/icon_7.gif"/>',
    deleteIcon:'<img style="cursor: pointer" src="' + basePath + 'common/style1/images/icon_9.gif"/>',
    setIcon:'<img style="cursor: pointer" src="' + basePath + 'common/style1/images/icon_8.gif"/>',
    infoIcon:'<img style="cursor: pointer" src="' + basePath + 'common/style1/images/icon_10.gif"/>',
    impIcon:'<img style="cursor: pointer" src="' + basePath + 'common/style1/images/icon_1.gif"/>',
    downIcon:'<img style="cursor: pointer" src="' + basePath + 'common/style1/images/icon_3.gif"/>',
    searchIcon:'<img style="cursor: pointer" src="' + basePath + 'common/style1/images/icon_17.gif"/>'
};

function lhgDialogReturn(returnValue) {
    var dg = frameElement.lhgDG;
    if (returnValue) {
        var args = dg.getArgs();
        args && args.returnFunc && args.window && args.returnFunc(args.window, returnValue);
    }
    dg.cancel();
    return false;
}

function getShi(){
	SysShengController.getShi($("#sys_sheng_id").val(),function(revalue){
		var optionstr = "<option value=\"\">&nbsp;&nbsp;请选择</option>";
		for(var i =0 ;i < revalue.length;i ++){
			optionstr += "<option value=\""+revalue[i]["s_code"]+"\" >"+revalue[i]["s_name"]+"</option>";
		}
		$("#sys_shi_id").empty();
		$("#sys_qu_id").empty();
		$("#sys_qu_id").append("<option value=\"\">&nbsp;&nbsp;请选择</option>");
		$("#sys_shi_id").append(optionstr);
	});
}

function getQu(){
	SysShengController.getQu($("#sys_shi_id").val(),function(revalue){
		var optionstr = "<option value=\"\">&nbsp;&nbsp;请选择</option>";
		for(var i =0 ;i < revalue.length;i ++){
			optionstr += "<option value=\""+revalue[i]["q_code"]+"\" >"+revalue[i]["q_name"]+"</option>";
		}
		$("#sys_qu_id").empty();
		$("#sys_qu_id").append(optionstr);
	});
}

function getShiQu(sid,qid){
	SysShengController.getShi($("#sys_sheng_id").val(),function(revalue){
		var optionstr = "<option value=\"\">&nbsp;&nbsp;请选择</option>";
		for(var i =0 ;i < revalue.length;i ++){
			optionstr += "<option value=\""+revalue[i]["s_code"]+"\" >"+revalue[i]["s_name"]+"</option>";
		}
		$("#sys_shi_id").empty();
		$("#sys_qu_id").empty();
		$("#sys_qu_id").append("<option value=\"\">&nbsp;&nbsp;请选择</option>");
		$("#sys_shi_id").append(optionstr);
		if(sid != ""){
			$("#sys_shi_id").val(sid);
		
			SysShengController.getQu($("#sys_shi_id").val(),function(revalue){
				optionstr = "<option value=\"\">&nbsp;&nbsp;请选择</option>";
				for(var i =0 ;i < revalue.length;i ++){
					optionstr += "<option value=\""+revalue[i]["q_code"]+"\" >"+revalue[i]["q_name"]+"</option>";
				}
				$("#sys_qu_id").empty();
				$("#sys_qu_id").append(optionstr);
				if(qid != ""){
					$("#sys_qu_id").val(qid);
				}
			});
		}
		
	});
}

function orgChange(val, type){
	CommonController.getJigou(val, type, function(data){
		$("#organization" + type).empty();
		var strHTML = "<option value=''>请选择</option>";
		$.each(data,function(i,o){
			strHTML += "<option value='"+o.id+"'>"+o.j_name+"</option>";
		});
		$("#organization" + type).append(strHTML);
	});
}



var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子   
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X   
function IdCardValidate(idCard) { 
    idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格                     
    if (idCard.length == 15) {   
        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证    
    } else if (idCard.length == 18) {   
        var a_idCard = idCard.split("");                // 得到身份证数组   
        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
            return true;   
        }else {   
            return false;   
        }   
    } else {   
        return false;   
    }   
}   
/**  
 * 判断身份证号码为18位时最后的验证位是否正确  
 * @param a_idCard 身份证号码数组  
 * @return  
 */  
function isTrueValidateCodeBy18IdCard(a_idCard) {   
    var sum = 0;                             // 声明加权求和变量   
    if (a_idCard[17].toLowerCase() == 'x') {   
        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作   
    }   
    for ( var i = 0; i < 17; i++) {   
        sum += Wi[i] * a_idCard[i];            // 加权求和   
    }   
    valCodePosition = sum % 11;                // 得到验证码所位置   
    if (a_idCard[17] == ValideCode[valCodePosition]) {   
        return true;   
    } else {   
        return false;   
    }   
}   
/**  
  * 验证18位数身份证号码中的生日是否是有效生日  
  * @param idCard 18位书身份证字符串  
  * @return  
  */  
function isValidityBrithBy18IdCard(idCard18){   
    var year =  idCard18.substring(6,10);   
    var month = idCard18.substring(10,12);   
    var day = idCard18.substring(12,14);   
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
    // 这里用getFullYear()获取年份，避免千年虫问题   
    if(temp_date.getFullYear()!=parseFloat(year)   
          ||temp_date.getMonth()!=parseFloat(month)-1   
          ||temp_date.getDate()!=parseFloat(day)){   
            return false;   
    }else{   
        return true;   
    }   
}   
  /**  
   * 验证15位数身份证号码中的生日是否是有效生日  
   * @param idCard15 15位书身份证字符串  
   * @return  
   */  
  function isValidityBrithBy15IdCard(idCard15){   
      var year =  idCard15.substring(6,8);   
      var month = idCard15.substring(8,10);   
      var day = idCard15.substring(10,12);   
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
      if(temp_date.getYear()!=parseFloat(year)   
              ||temp_date.getMonth()!=parseFloat(month)-1   
              ||temp_date.getDate()!=parseFloat(day)){   
                return false;   
        }else{   
            return true;   
        }   
  }   
//去掉字符串头尾空格   
function trim(str) {   
    return str.replace(/(^\s*)|(\s*$)/g, "");   
}


/**  
 * 通过身份证判断是男是女  
 * @param idCard 15/18位身份证号码   
 * @return 'female'-女、'male'-男  
 */  
function maleOrFemalByIdCard(idCard){   
    idCard = trim(idCard.replace(/ /g, ""));        // 对身份证号码做处理。包括字符间有空格。   
    if(idCard.length==15){   
        if(idCard.substring(14,15)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else if(idCard.length ==18){   
        if(idCard.substring(14,17)%2==0){   
            return '女';   
        }else{   
            return '男';   
        }   
    }else{   
        return null;   
    }   
}  