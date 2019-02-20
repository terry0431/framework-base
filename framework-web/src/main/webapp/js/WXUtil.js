var jsApiTicket = "";
var jsApiTicket_posturl = "http://www.haihuodl.com/framework-web/wx/getJsApiTicket";

function getJsApiTicket(){
    $.ajax({
        url: posturl,
        type: 'POST',
        async : false,
        //dataType:'JSONP',
        data: {},
        success: function(data){
            alert("ok " + JSON.stringify(data) );
        },
        error: function(data){
            alert("error: " + JSON.stringify(data) );
        }
    });
}