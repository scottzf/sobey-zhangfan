**Sobey-cmop**

### sobey-modules : 一些公用的模块
* sobey-parent : 依赖库版本定义的父模块
* sobey-core : 核心通用代码模块
* sobey-extension : 扩展代码模块
* sobey-test : 测试扩展代码模块

### sobey-projects 各个具体的项目模块
* cmdbuild : CMDBuild的webservice接口
* instance : Instance的webservice接口
* storage : Storage的webservice接口
* switch : Switch的webservice接口
* firewall : Firewall的webservice接口
* loadbalancer : LoadBalancer的webservice接口

### webservice相关.
* 执行quick-start.bat,启动所有webservice服务端
* 浏览器访问 http://localhost:*portNum*/*projectName*/cxf/soap/*webserviceProjectName*?wsdl ,Ctrl + S 将文件保存至/src/main/resources/wsdl
* 在需要生成客户端代码的项目pom中,指定代码生成的路径和wsdl文件引用路径
>  
<plugin>  
	<groupId>org.apache.cxf</groupId>  
	<artifactId>cxf-codegen-plugin</artifactId>  
	<version>${cxf.version}</version>  
	<executions>  
		<execution>  
			<id>generate-sources</id>  
			<phase>generate-sources</phase>  
			<configuration>  
				<sourceRoot>${basedir}/src/main/java</sourceRoot>  
				<wsdlOptions>  
					<wsdlOption>  
						<wsdl>${basedir}/src/main/resources/wsdl/switchesSoapService.xml</wsdl>  
					</wsdlOption>  
				</wsdlOptions>  
			</configuration>  
			<goals>  
				<goal>wsdl2java</goal>  
			</goals>  
		</execution>  
	</executions>  
</plugin>  
> 
 
 * 执行generate-sources.bat,即可生成webservice客户端代码