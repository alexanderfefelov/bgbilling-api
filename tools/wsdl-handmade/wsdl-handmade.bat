@echo off

set WSDLMERGE=bin\WSDLMerge.exe
set XMLSTARLET=bin\xml.exe
set SED=bin\sed.exe

set TARGET_DIR=soap\oss\src\main\wsdl\handmade
if not exist %TARGET_DIR%\nul mkdir %TARGET_DIR%
call :process http://bgbilling.local:8080/bgbilling/executer/ru.bitel.oss.kernel.directories.address AddressService
call :process http://bgbilling.local:8080/bgbilling/executer/ru.bitel.oss.kernel.entity              EntityService
call :process http://bgbilling.local:8080/bgbilling/executer/ru.bitel.oss.systems.inventory.resource PhoneResourceService
call :process http://bgbilling.local:8080/bgbilling/executer/ru.bitel.oss.systems.order.product      ProductOrderService
call :process http://bgbilling.local:8080/bgbilling/executer/ru.bitel.oss.systems.inventory.product  ProductService
call :process http://bgbilling.local:8080/bgbilling/executer/ru.bitel.oss.systems.inventory.resource ResourceService
call :process http://bgbilling.local:8080/bgbilling/executer/ru.bitel.oss.systems.inventory.service  ServiceService

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
