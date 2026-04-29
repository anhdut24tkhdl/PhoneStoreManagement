@echo off
cd /d %~dp0
mvn clean compile
mvn exec:java
pause
