@echo off

set UNZ=bin\unzip.exe

call :process bill
call :process card
call :process inet
call :process kernel
call :process moneta
call :process qiwi
call :process rscm
call :process subscription

exit

:process

set MODULE=%1
set MODULE_JAR=%MODULE%.jar
set TARGET_DIR=soap\%MODULE%\src\main\wsdl\original
if exist %MODULE_JAR% (
    echo Processing %MODULE%...
    if not exist %TARGET_DIR%\nul mkdir %TARGET_DIR%
    %UNZ% -qq -d %TARGET_DIR% %MODULE_JAR% *.wsdl
    echo ...done
) else (
    echo %MODULE%.jar not found
)

exit /B
