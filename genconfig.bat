@echo off
setlocal
if defined JAVA_HOME (
    set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
) else (
    set "JAVA_EXE=java"
)
"%JAVA_EXE%" -Dfile.encoding=UTF-8 --sun-misc-unsafe-memory-access=allow -jar tools\cfggen.jar -headrow 3 -datadir config -gen verify -gen java,dir:gen/src,configgenDir:src/main/java,encoding:utf-8 -gen bytes,schema
if errorlevel 1 (
    if not "%~1" == "/S" pause
    exit /b 1
)
move /Y config.bytes config.data >nul
if errorlevel 1 exit /b 1
echo Success!
if not "%~1" == "/S" pause
exit /b 0
