@echo off
@echo "ʹ��protoc.exe����JAVA�ļ�"
@echo on
for /r %%i in (*.proto) do protoc --proto_path=%cd% --java_out=F:\tempProto %%i

@echo "JAVA�ļ��������!!"
@echo off
echo "�Ƿ���Ҫ�Զ�����(y or n)��"
set /p choice=
if "%choice%"=="y" XCOPY F:\tempProto  F:\ideaProject\KingOfTower\src\main\java /S
@echo on
pause