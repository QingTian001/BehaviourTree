set JAVA_PATH=C:\Program Files\Java\jdk-21

call "%JAVA_PATH%/bin/java.exe"  -jar ..\tools\cfggen.jar -headrow 3 -datadir . -nowarn -gen server

pause