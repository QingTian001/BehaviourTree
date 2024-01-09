@echo off

set dir=./
set util=util/
set datadir=config
if not exist %dir% goto exit
call "%util%/jdk-21.0.1/bin/java.exe" -Xms1G -XX:+UseG1GC -Dfile.encoding=utf8 -Dconfiggen.headrow=3 -jar %util%/configgen.jar -datadir %datadir% -verify -gen java,builders:ugc.txt,encoding:utf-8,dir:%dir%\gen\src -gen javadata,file:%dir%\config.data

if %errorlevel% neq 0 (
    if not "%1" == "/S" (
        pause
    )
    exit 1
) else (
    echo.
    echo "Success!"
    echo.
    if not "%1" == "/S" (
        pause
    )
)

