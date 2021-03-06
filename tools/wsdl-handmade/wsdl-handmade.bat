@echo off

set WSDLMERGE=bin\WSDLMerge.exe
set XMLSTARLET=bin\xml.exe
set SED=bin\sed.exe

set EXECUTER=http://billing.bgbilling.local:8080/bgbilling/executer

:: oss
::
set TARGET_DIR=soap\oss\src\main\wsdl\handmade
if not exist %TARGET_DIR%\nul mkdir %TARGET_DIR%
call :process %EXECUTER%/ru.bitel.oss.kernel.directories.address AddressService
call :process %EXECUTER%/ru.bitel.oss.kernel.directories.domain  DomainService
call :process %EXECUTER%/ru.bitel.oss.kernel.entity              EntityService
call :process %EXECUTER%/ru.bitel.oss.systems.inventory.resource PhoneResourceService
call :process %EXECUTER%/ru.bitel.oss.systems.order.product      ProductOrderService
call :process %EXECUTER%/ru.bitel.oss.systems.inventory.product  ProductService
call :process %EXECUTER%/ru.bitel.oss.systems.inventory.resource ResourceService
call :process %EXECUTER%/ru.bitel.oss.systems.inventory.service  ServiceService

:: kernel
::
set TARGET_DIR=soap\kernel\src\main\wsdl\handmade
if not exist %TARGET_DIR%\nul mkdir %TARGET_DIR%
call :process %EXECUTER%/ru.bitel.bgbilling.kernel.config ConfigService

:: bonus
::
set TARGET_DIR=soap\bonus\src\main\wsdl\handmade
if not exist %TARGET_DIR%\nul mkdir %TARGET_DIR%
call :process %EXECUTER%/ru.bitel.bgbilling.plugins.bonus BonusService

exit

:process

%WSDLMERGE% %1/%2?wsdl %TARGET_DIR%\%2.wsdl
%XMLSTARLET% format --indent-spaces 2 %TARGET_DIR%\%2.wsdl > %TARGET_DIR%\%2.tmp
del %TARGET_DIR%\%2.wsdl
rename %TARGET_DIR%\%2.tmp %2.wsdl
%SED% "s/<soap:address location=.*/<soap:address location=\"REPLACE_WITH_ACTUAL_URL\">/" %TARGET_DIR%\%2.wsdl > %TARGET_DIR%\%2.tmp
del %TARGET_DIR%\%2.wsdl
rename %TARGET_DIR%\%2.tmp %2.wsdl

exit /B
