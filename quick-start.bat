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
start "CMDBuild" %MVN% clean jetty:run 
if errorlevel 1 goto error

rem cd ..\instance
rem start "Instance" %MVN% clean jetty:run -Djetty.port=8081
rem if errorlevel 1 goto error
rem cd ..\..\

echo [INFO] Please wait a moment. When you see "[INFO] Started Jetty Server" in consoles, you can access below demo sites:
echo [INFO] http://localhost:8080/cmdbuild
rem echo [INFO] http://localhost:8081/instance

goto end
:error
echo Error Happen!!
:end
pause