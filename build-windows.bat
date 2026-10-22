@echo off
setlocal
where gradle >nul 2>nul
if errorlevel 1 (
  echo Gradle is not installed or not on PATH.
  echo Install Gradle 8.10.2 and Java 21, then run this script again.
  echo See https://gradle.org/install/
  exit /b 1
)
gradle %*
exit /b %ERRORLEVEL%
