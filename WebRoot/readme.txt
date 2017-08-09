一 项目部署步骤：
1 编译项目，生成classes文件
2 将编译的classes文件下的hailian文件夹（com/fr下面）复制到
  FineReport安装目录（\WebReport\WEB-INF\classes\com\fr）下面，我的在
  F:\FineReport8.0\FineReport_8.0\WebReport\WEB-INF\classes\com\fr
3 配置自定义的servlet文件
	将项目中自配置的servlet路由（web.xml里面）复制到FineReport安装目录（\WebReport\WEB-INF）下，
	我的在F:\FineReport8.0\FineReport_8.0\WebReport\WEB-INF的web.xml里面 注意xml的字符编码编码格式
4 将自开发的页面（项目WebRoot/hailian文件夹）复制到到FineReport安装目录（\WebReport）下，我的在
   F:\FineReport8.0\FineReport_8.0\WebReport

5 启动FineReport即可


二 改造后登陆页面：
 1 地址：http://localhost:8075/WebReport/hailian/login.html
 2 登陆页配置：
	2.1 打开决策系统http://localhost:8075/WebReport/ReportServer?op=fs，登陆
	2.2 系统管理--外观配置--登陆方式 选择“设置登录网页”，将地址改为改造后登陆页面
	2.3 退出，配置成功

三 单点登录
 1 地址
	http://localhost:8075/WebReport/PortalLoginServlet?Token=11111&Target=https://www.hao123.com/?tn=90207880_hao_pg