var iframeHeigth;
var iframeWidth;

function browserSizeDeal(hideMenuFlag) {
    hideMenuFlag = hideMenuFlag == undefined ? false : hideMenuFlag
    var topHeight = 132;
    var bottomHeight = 38;
    iframeHeigth = document.documentElement.clientHeight - (topHeight + bottomHeight);
    iframeWidth = document.documentElement.clientWidth - 236 - 2 - 2;
    if (hideMenuFlag) {
        iframeWidth += 236;
    }
    $("#mainDiv").width(iframeWidth);
    $("#mainFrame").height(iframeHeigth).width(iframeWidth);
    $("#leftDiv").height(iframeHeigth - 20)
}
$(function () {
    window.win = $("#mainFrame")[0].contentWindow;
    var sizeTimer;
    $(window).bind("resize", function () {
        sizeTimer != undefined && sizeTimer != null && clearTimeout(sizeTimer);
        sizeTimer = setTimeout('browserSizeDeal($("#leftDiv")[0].style.display=="none")', 100);
    }).load(function () {
        //dwr.engine.setActiveReverseAjax(true);
        //LoginController.validateLogin();
    }).unload(function () {
        $.ajax({url: "logout", cache: false, async: false});
    });
    //$("#stairMenu a").click(function(){

    //alert($(this).attr("url"));

    $.post("main?menuId=1", function (data) {
        $("#menuUl").html(data).find("a").click(function () {
            var url = $(this).attr("url");

            var target = $(this).attr("target");
            $("#mainFrame").attr("src", "");
            if (url != "") {
                $("#leftDiv").hide();
                $("#mainDiv").removeClass("mainRArea");
                browserSizeDeal(true);
                $("#mainFrame").attr("src", url);
            } else {
                //alert(pid);
                if (pid == "null" || pid == "") {
                    pid = $(this).attr("parentId");
                }
                $.post("toLeftTreePage.do?parentId=" + pid, function (returnData) {
                    $("#leftDiv").show();
                    $("#mainDiv").addClass("mainRArea");
                    browserSizeDeal();
                    $("#mainNav").html(returnData);
                    $("#mainNav>li>a").filter(":first").click();
                    pid = "";//仅第一次初始化时有效
                })
            }
        }).filter(":first").click();
        document.getElementById("mainFrame").src = desktopsrc;
    })
    //}).filter(":first").click();
    $("#userInfoModify").click(function () {    	
        window.addUserDialog = new $.dialog({
            id: 'userInfoModifyFrame',
            title: "个人信息维护",
            height: 400,
            width: 600,
            cover: true,
            page: "user/edituserInfo",
            autoPos: true
        });
        addUserDialog.ShowDialog()
    })
        
    $("#notify").click(function(){        
        window.notifyDialog = new $.dialog({
            id: 'notifyFrame',
            title: "查看消息",
            height: 500,
            width: 1500,
		    cover:true,
		    content: 'url:con/zhyy/xiaoxi/list',
		            autoPos:true
		        });
    	})
    var int=self.setInterval("getCounter()",10000);
    getCounter();
    	  	
})
function menuSlide(menuId) {
    var $ul = $("#" + menuId);
    var flag = $ul.css("display") == "none";
    var height = $ul.show().height();
    if (flag) {
        $ul.hide().height(0).show().animate({
            height: height
        }, 400, function () {
            $ul.attr("style", "display:block")
            leftDivOverflow();
        })
    } else {
        $ul.animate({
            height: 0
        }, 400, function () {
            $ul.hide().height(height);
            leftDivOverflow()
        });
    }
    function leftDivOverflow() {
        var $leftDiv = $("#leftDiv");
        if ($("#mainNav").height() > $leftDiv.height()) {
            $leftDiv.css({overflowY: "scroll"})
        } else {
            $leftDiv.css({overflowY: "hidden"})
        }
    }
}

function getCounter(){	
    $.ajax({
        url: "ifs/zhyy/xiaoxi/getByUser",
        type: 'post',
        async : false,
        //dataType:'JSONP',
        data: {},
        success: function(data){
            if(data != null && data.length > 0){
//                alert(parseInt($("#counter").text()) + ":" + data.length );
                if(parseInt($("#counter").text()) !== data.length && data.length > parseInt($("#counter").text()) ){
//                    alert("pl");
                    $("#counter").text(data.length);
                    playSound();//播放报警音乐
                }
            }else{
                $("#counter").text(0);
            }
//          $("#counter").text(data);
//          var counter = new Number(data);
//          if(counter>0){        		
//        		
//              var winframe = window.frames["notifyFrame"];        		
//        	if(winframe){        			
//                  winframe.window.setCounter(counter);
//        	}        		
//          }
            //alert("ok"+JSON.stringify(data));
        },
        error: function(data){        	
            //alert("error: " + JSON.stringify(data) );
        }
    });
}

function playSound()
{
    var borswer = window.navigator.userAgent.toLowerCase();
  if ( borswer.indexOf( "ie" ) >= 0 )
  {
    //IE内核浏览器
    var strEmbed = '<embed name="embedPlay" src="http://www.gongqinglin.com/accessory/ding.wav" autostart="true" hidden="true" loop="false"></embed>';
    if ( $( "body" ).find( "embed" ).length <= 0 )
      $( "body" ).append( strEmbed );
    var embed = document.embedPlay;

    //浏览器不支持 audion，则使用 embed 播放
    embed.volume = 100;
    //embed.play();这个不需要
  } else
  {
    //非IE内核浏览器
    var strAudio = "<audio id='audioPlay' src='http://fjdx.sc.chinaz.com/files/download/sound1/201406/4611.wav' hidden='true'>";
    if ( $( "body" ).find( "audio" ).length <= 0 )
      $( "body" ).append( strAudio );
    var audio = document.getElementById( "audioPlay" );

    //浏览器支持 audion
    audio.play();
  }
}
