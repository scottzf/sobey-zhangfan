**Sobey-cmop**

### sobey-modules : 一些公用的模块
* sobey-parent 		:依赖库版本定义的父模块
* sobey-core 		:核心通用代码模块
* sobey-extension 	:扩展代码模块
* sobey-test 		:测试扩展代码模块

### sobey-projects 各个具体的项目模块
* cmdbuild     : CMDBuild的webservice接口
* instance 	   : vCenter的webservice接口
* storage      : Netapp的webservice接口
* switch 	   : Switch的webservice接口
* firewall 	   : Firewall的webservice接口
* loadbalancer : LoadBalancer的webservice接口
* dns 		   : DNS的webservice接口
* nagios 	   : Nagios的webservice接口
* zabbix 	   :Zabbix的webservice接口
* redmine 	   : Redmine的webservice接口
* cmop-api	   : 公共功能模块的webservice接口

### webservice相关.
* 执行quick-start.bat,启动所有webservice服务端
* 浏览器访问 http://localhost:portNum/projectName/cxf/soap/xxxxx?wsdl ,Ctrl + S 将文件保存至需要生成客户端代码项目的src/main/resources/wsdl中
* 在需要生成客户端代码的项目pom中,指定代码生成的路径和wsdl文件引用路径
* 执行generate-sources.bat,即可生成webservice客户端代码

###需要导入netscaler的SDK包
* mvn install:install-file -Dfile=D:\nitro.jar -DgroupId=com.citrix.netscaler -DartifactId=nitro -Dversion=9.3 -Dpackaging=jar

###需要导入vijava的jar包
* mvn install:install-file -Dfile=D:\vijava55b20130927.jar -DgroupId=com.vmware -DartifactId=vijava -Dversion=55b20130927 -Dpackaging=jar

###部署
 需要分布式部署，注意生成wsdl文件的URL路径需要和cmop-api中webservice配置文件中的URL路径相同。正确的部署流程应该是：

1. 将各个项目在不同的实例中启动后，保存wsdl文件
2. 将wsdl文件拷贝至cmop-api项目中的WSDL文件夹下
3. 修改cmop-api项目applicationContext-soap-server.xml文件中需要实现webservice的service URL
4. 执行generate-sources.bat
5. 启动cmop-api
