/**
 * Created by zuoqb on 2017年8月10日14:28:13
 */
/**
 * 多级上报
 */
function initHlRTXReportMethod(){
	  var domain=FR.serverURL+FR.servletURL;
	  //domain="/WebReport";
	  var url=domain.replace("/ReportServer","");
      domain=domain.replace("/ReportServer","")+'/RTXShareServlet';
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
        	  if (res.responseText == "") {
              var signResult = FR.jsonDecode(res.responseText);
              if (signResult.fail) {
            	  console.log(signResult.msg);
              } 
        	  }
          }
      });
};