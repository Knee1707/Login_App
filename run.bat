@echo off
chcp 65001 >nul
title Chay Bai 2 (JPA) - CRUD Category
setlocal

REM  LUU Y: SiteMesh 3 (Muc 1) khong render duoc tren Tomcat 11 (Servlet 6.1) -> dung Tomcat 10.1
set "JAVA_HOME=C:\Program Files\Java\jdk-26.0.2.1"
set "CATALINA_HOME=E:\Tools\apache-tomcat-10.1.59"
set "PROJ=%~dp0"
set "MVN=E:\Tools\apache-maven-3.9.16\bin\mvn.cmd"
set "APP=Bai2JPA"
set "PORT=8089"

echo [1/3] Build WAR (cho chut)...
call "%MVN%" -f "%PROJ%pom.xml" clean package
if errorlevel 1 ( echo *** BUILD THAT BAI *** & pause & exit /b 1 )

echo [2/3] Deploy vao Tomcat...
call "%CATALINA_HOME%\bin\shutdown.bat" 2>nul
timeout /t 3 /nobreak >nul
if exist "%CATALINA_HOME%\webapps\%APP%" rmdir /S /Q "%CATALINA_HOME%\webapps\%APP%"
if exist "%CATALINA_HOME%\webapps\%APP%.war" del /Q "%CATALINA_HOME%\webapps\%APP%.war"
copy /Y "%PROJ%target\%APP%.war" "%CATALINA_HOME%\webapps\" >nul

echo [3/3] Khoi dong Tomcat (cong %PORT%)...
call "%CATALINA_HOME%\bin\startup.bat"
timeout /t 10 /nobreak >nul
start "" "http://localhost:%PORT%/%APP%/"

echo.
echo XONG! Mo: http://localhost:%PORT%/%APP%/
echo De tat server: chay stop.bat
pause
