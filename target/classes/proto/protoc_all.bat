@echo off
@echo "使用protoc.exe生成JAVA文件"
@echo on
for /r %%i in (*.proto) do protoc --proto_path=%cd% --java_out=F:\tempProto %%i

@echo "JAVA文件生成完毕!!"
@echo off
echo "是否需要自动拷贝(y or n)？"
set /p choice=
if "%choice%"=="y" XCOPY F:\tempProto  F:\ideaProject\KingOfTower\src\main\java /S
@echo on
pause