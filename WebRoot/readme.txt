一 项目部署步骤：
1 编译项目，生成classes文件
2 将编译的classes文件下的hailian文件夹（com/fr下面）复制到
  FineReport安装目录（\WebReport\WEB-INF\classes\com\fr）下面，我的在
  F:\FineReport8.0\FineReport_8.0\WebReport\WEB-INF\classes\com\fr
3 配置自定义的servlet文件
	将项目中自配置的servlet路由（web.xml里面）复制到FineReport安装目录（\WebReport\WEB-INF）下，
	我的在F:\FineReport8.0\FineReport_8.0\WebReport\WEB-INF的web.xml里面 注意xml的字符编码编码格式
	说明：web.xml中注释在发布服务时需要释放注释
4 将自开发的页面（项目WebRoot/hailian文件夹）复制到到FineReport安装目录（\WebReport）下，我的在
   F:\FineReport8.0\FineReport_8.0\WebReport

5 启动FineReport即可


二 辅助决策登陆改造：
 1 地址：http://localhost:8075/WebReport/hailian/login.html
 2 登陆页配置：
	2.1 打开决策系统http://localhost:8075/WebReport/ReportServer?op=fs，登陆
	2.2 系统管理--外观配置--登陆方式 选择“设置登录网页”，将地址改为改造后登陆页面
	2.3 退出，配置成功

三 单点登录改造以及将辅助决策页面集成到第三方
 1 地址
	http://localhost:8075/WebReport/portalLoginServlet?Token=11111&Target=1111&__redirect__=true
	参数说明：
	Token：令牌
	Target：目标字符串
	__redirect__：固定参数 true表示认证成功之后直接进入系统首页
	
 2 返回信息说明
  2.1 如果单点登录成功，直接进行决策管理首页
  2.2 如果失败，返回页面有个json格式数据，格式{"msg":"提示信息","fail":"boolean类型，true表示失败"}
  
  
四 RTX集成
 1 引用方式
  1.1 作用机理
     设计模板时可以给控件、工具栏按钮、整个报表添加JS事件，每个事件对应一个function。
     当报表转为html页面时会将这些function加到html的头部head。当事件被触发时如点击按钮时，或者导出打印报表时，对应的function就会被执行。
  1.1 引入现成的js文件(如果使用RTX集成需引用\WebReport\hailian\js\hl_common.js)
      如果不是 所有模板统一引用而是只要某一个页面使用，可以直接进入1.2
      报表工程下所有模板统一引入外部js文件：服务器>服务器配置>引用JavaScript
      相对路径引用js：相对于报表工程目录如WebReport，如WebReport\js下有引用的js文件test.js，则相对路径为js/test.js；
      绝对路径引用js：如D:\tomcat\webapps\WebReport\WEB-INF\scripts\script.js。   
  1.2 事件编辑界面
    找到当前事件要添加的控件，按钮控件设置>事件编辑>添加点击事件便可看到事件编辑界面了
    在编辑页面需要进行的操作：
    1.2.1 如果1.1步骤没有引入hailian/js/hl_common.js，那在上方“引用JavaScript”先引入hl_common.js（注意路径问题），
    否则直接进入下一步
    1.2.2 在下方“JavaScript脚本”中加入自定义方法，方法名称固定：initHlRTXReportMethod();无参数要求。
   
  

五 修改密码
 	1 引用方式
   	引入js同上（\WebReport\hailian\js\hl_common.js）
    2 事件编辑界面 
        绑定的方法名为initHlChangePassword();
        这个方法需要一个参数：pwdInputName-代表新密码输入框的控件名称
    （具体名称位置：选中输入新密码的输入框--右侧选择控件“属性”--基本属性--控件名），将找到的控件名传入绑定方法即可。
    例如：initHlChangePassword("textEditor0");textEditor0是我定义的输入框name
    3 响应
          修改成功，跳转决策系统首页
          修改失败则提示错误信息
 
六 注销
    1 引用方式
   	引入js同上（\WebReport\hailian\js\hl_common.js）
    2 事件编辑界面 
          绑定的方法名为initHlLogout();无参数要求。
    3 响应
          成功：用户退出，跳转决策系统登陆页
          失败：目前没有提示信息，只有打印日志




其他注意项：
 1 辅助决策系统权限目前默认写成222（正式发布需要改，否则所有认证都会返回没有权限的提示）
 com.fr.hailian.util.Constants文件中的AUXILIARYROLE_ID
