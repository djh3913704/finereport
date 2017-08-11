/**
 * Created by zuoqb on 2017年8月10日14:28:13
 */
/**
 * RTX集成相关方法 多级上报
 */
function initHlRTXReportMethod(){
	  var domain=FR.serverURL+FR.servletURL;
	  //domain="/WebReport";
	  var url=domain.replace("/ReportServer","");
      domain=domain.replace("/ReportServer","")+'/rtxShareServlet';
	  console.log(domain)
	  FR.ajax({
      	  url: domain,
          data: FR.cjkEncodeDO({
        	  domain: url,
          }),
          type: 'POST',
          async: false,
          error: function () {
              FR.Msg.toast("Error!");
          },
          complete: function (res, status) {
        	  if (res.responseText != "") {
              var signResult = FR.jsonDecode(res.responseText);
	              if (signResult.fail) {
	            	  console.log(signResult.msg);
	              } 
        	  }
          }
      });
};

/*
 * 修改密码
 * pwdInputName:新密码控件名字
 */
function initHlChangePassword(pwdInputName){
	  pwdInputName=pwdInputName.toUpperCase();
	  var domain=FR.serverURL+FR.servletURL;
	  domain="/WebReport";
      domain=domain.replace("/ReportServer","")+'/changePwdServlet';
      var newPassword=$('[name="'+pwdInputName+'"]').val();
	  console.log(domain+"--"+newPassword);
	  FR.ajax({
    	  url: domain,
          data: FR.cjkEncodeDO({
        	fr_password: encodeURIComponent(newPassword),
        }),
        type: 'POST',
        async: false,
        error: function () {
            FR.Msg.toast("Error!");
        },
        complete: function (res, status) {
      	  if (res.responseText != "") {
            var signResult = FR.jsonDecode(res.responseText);
	            if (signResult.fail) {
	          	  showErrorMsg($('[name="'+pwdInputName+'"]'), signResult.msg);
	            } else {
                    window.location.href = signResult.msg;
                }
      	  }
        }
    });
};


/*
 * 注销
 */
function initHlLogout(){
	  var domain=FR.serverURL+FR.servletURL;
	  domain="/WebReport";
      domain=domain.replace("/ReportServer","")+'/logoutServlet';
	  console.log(domain);
	  FR.ajax({
    	  url: domain,
        type: 'POST',
        async: false,
        error: function () {
            FR.Msg.toast("Error!");
        },
        complete: function (res, status) {
      	  if (res.responseText != "") {
            var signResult = FR.jsonDecode(res.responseText);
            if (signResult.fail) {
          	  console.log(signResult.msg);
          	  //showErrorMsg($("#"+btnId), signResult.msg);
            } else {
                window.location.href = "/WebReport/ReportServer?op=fs";
            } 
      	  }
        }
    });
};
function showErrorMsg ($pos, msg) {
	var $mask = $('<div class="fs-login-errmask"/>');
    $mask.hide().insertAfter($pos).text(msg);
    $mask.click(function () {
        $(this).fadeOut();
        $pos.select();
    }).fadeIn();
};