@echo off
echo Before run this script, please save 
echo http://localhost:xxxx/cmdbuild/cxf/soap/xxxxx?wsdl to target/wsdl/


call mvn clean generate-sources
if errorlevel 1 goto end

echo [INFO] Code had generated to <${basedir}/src/main/java>
pause

:end
pause