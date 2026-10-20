@echo off
setlocal
if defined JAVA_HOME (
    set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
) else (
    set "JAVA_EXE=java"
)
"%JAVA_EXE%" --sun-misc-unsafe-memory-access=allow -jar ..\tools\cfggen.jar -headrow 3 -datadir . -nowarn -gen server
pause
