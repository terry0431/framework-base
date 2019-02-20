var yzclist_url = basePath + "/ifs/zhyy/getYzclist";
var yzqlist_url = basePath + "/ifs/zhyy/getYzqList";
var rtulist_url = basePath + "/ifs/zhyy/getRtuListByYzq";
var rtulist_yzc_url = basePath + "/ifs/zhyy/getRtulistByYzc";
function getYzcList(cbfun) {

    $.ajax({
        url: yzclist_url,
        data: {
        },
        type: 'POST',
        timeout: 3000,
        dataFilter: function (json) {
            console.log("jsonp.filter:" + json);
            return json;
        },
        success: function (json, textStatus) {
            cbfun(json);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("jsonp.error:" + XMLHttpRequest.returnCode);
        }
    });
}


function getYzqList(yzcid, cbfun) {
    $.ajax({
        url: yzqlist_url,
        data: {yzcid: yzcid
        },
        type: 'POST',
        timeout: 3000,
        dataFilter: function (json) {
            console.log("jsonp.filter:" + json);
            return json;
        },
        success: function (json, textStatus) {
            cbfun(json);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("jsonp.error:" + XMLHttpRequest.returnCode);
        }
    });
}

function getRtuList(yzqid, cbfun) {
    $.ajax({
        url: rtulist_url,
        data: {yzqid: yzqid
        },
        type: 'POST',
        timeout: 3000,
        dataFilter: function (json) {
            console.log("jsonp.filter:" + json);
            return json;
        },
        success: function (json, textStatus) {
            cbfun(json);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("jsonp.error:" + XMLHttpRequest.returnCode);
        }
    });
}

function getRtuListByYzcid(yzcid, cbfun) {
    $.ajax({
        url: rtulist_yzc_url,
        data: {yzcid: yzcid
        },
        type: 'POST',
        timeout: 3000,
        dataFilter: function (json) {
            console.log("jsonp.filter:" + json);
            return json;
        },
        success: function (json, textStatus) {
            cbfun(json);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("jsonp.error:" + XMLHttpRequest.returnCode);
        }
    });
}
