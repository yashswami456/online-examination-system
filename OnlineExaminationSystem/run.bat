@echo off
REM ─────────────────────────────────────────────────────────────
REM  run.bat  —  Build & run the Online Examination System
REM ─────────────────────────────────────────────────────────────

echo.
echo   ╔══════════════════════════════════════════════════════════╗
echo   ║         ONLINE EXAMINATION SYSTEM  v1.0                 ║
echo   ╚══════════════════════════════════════════════════════════╝
echo.

IF NOT EXIST bin mkdir bin

echo   [1/2]  Compiling Java sources...

REM Find all .java files and compile them
for /r src %%f in (*.java) do echo %%f >> sources.txt
javac -d bin @sources.txt
del sources.txt

echo   [2/2]  Compilation successful. Starting application...
echo.

java -cp bin com.exam.Main

pause
