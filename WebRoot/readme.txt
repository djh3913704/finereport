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


二 辅助决策登陆改造：
 1 地址：http://localhost:8075/WebReport/hailian/login.html
 2 登陆页配置：
	2.1 打开决策系统http://localhost:8075/WebReport/ReportServer?op=fs，登陆
	2.2 系统管理--外观配置--登陆方式 选择“设置登录网页”，将地址改为改造后登陆页面
	2.3 退出，配置成功

三 单点登录
 1 地址
	http://localhost:8075/WebReport/PortalLoginServlet?Token=11111&Target=1111&__redirect__=true
	参数说明：
	Token：令牌
	Target：目标字符串
	__redirect__：固定参数 true表示认证成功之后直接进入系统首页
	
 2 返回信息说明
  2.1 如果单点登录成功，直接进行决策管理首页
  2.2 如果失败，返回页面有个json格式数据，格式{"msg":"提示信息","fail":"boolean类型，true表示失败"}
其他：辅助决策系统权限目前默认写成222（正式发布需要改，否则所有认证都会返回没有权限的提示）
com.fr.hailian.util.Constants文件中的AUXILIARYROLE_ID
