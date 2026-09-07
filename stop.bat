@echo off
chcp 65001 >nul
title Tat Tomcat
set "JAVA_HOME=C:\Program Files\Java\jdk-26.0.2.1"
set "CATALINA_HOME=E:\Tools\apache-tomcat-10.1.59"
echo Dang tat Tomcat...
call "%CATALINA_HOME%\bin\shutdown.bat"
timeout /t 3 /nobreak >nul
