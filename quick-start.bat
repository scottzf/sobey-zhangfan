@echo off
echo [Pre-Requirement] Makesure install JDK 6.0+ and set the JAVA_HOME.
echo [Pre-Requirement] Makesure install Maven 3.0.3+ and set the PATH.

set MVN=mvn
set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m

echo [Step 1] Install all sobey modules to local maven repository.
cd sobey-modules
call %MVN% clean install -Dmaven.test.skip=true
if errorlevel 1 goto error
cd ..\

echo [Step 2] Start all sobey projects.
cd sobey-projects\cmdbuild




cd ..\cmdbuild
start "CMDBuild" %MVN% clean clean tomcat:run -Dmaven.tomcat.port=8080
if errorlevel 1 goto error

cd ..\instance
start "Instance" %MVN% clean tomcat:run -Dmaven.tomcat.port=8081
if errorlevel 1 goto error


rem 注释end
goto start  


cd ..\dns
start "DNS" %MVN% clean tomcat:run -Dmaven.tomcat.port=8085
if errorlevel 1 goto error

 
cd ..\switch
start "Switch" %MVN% clean tomcat:run -Dmaven.tomcat.port=8082
if errorlevel 1 goto error
 
cd ..\firewal
start "Firewal" %MVN% clean tomcat:run -Dmaven.tomcat.port=8083
if errorlevel 1 goto error

cd ..\storage
start "Storage" %MVN% clean tomcat:run -Dmaven.tomcat.port=8084
if errorlevel 1 goto error


cd ..\loadbalancer
start "Loadbalancer" %MVN% clean tomcat:run -Dmaven.tomcat.port=8086
if errorlevel 1 goto error

cd ..\nagios
start "Nagios" %MVN% clean tomcat:run -Dmaven.tomcat.port=8087
if errorlevel 1 goto error

rem 注释start
:start


cd ..\cmop-api
start "cmop-api" %MVN% clean tomcat:run -Dmaven.tomcat.port=8088
if errorlevel 1 goto error


echo [INFO] Please wait a moment. When you see "[INFO] Started Jetty Server" in consoles, you can access below demo sites:
echo [INFO] http://localhost:8080/cmdbuild
echo [INFO] http://localhost:8081/instance
echo [INFO] http://localhost:8082/switch
echo [INFO] http://localhost:8083/firewall
echo [INFO] http://localhost:8084/storage
echo [INFO] http://localhost:8085/dns
echo [INFO] http://localhost:8086/loadbalancer
echo [INFO] http://localhost:8087/nagios
echo [INFO] http://localhost:8088/cmop-api

goto end
:error
echo Error Happen!!
:end
pause