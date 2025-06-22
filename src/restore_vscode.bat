@echo off
chcp 65001 > nul

set /p BACKUP_DIR=복구할 백업 폴더 경로를 입력하세요: 

if not exist "%BACKUP_DIR%" (
    echo 폴더가 존재하지 않습니다: %BACKUP_DIR%
    pause
    exit /b
)

echo 1. 사용자 설정 복원 중...
xcopy "%BACKUP_DIR%\User" "%APPDATA%\Code\User" /E /I /Y

echo 2. 확장 프로그램 복원 중...
for /f %%i in ('type "%BACKUP_DIR%\vscode-extensions.txt"') do (
    echo 설치 중: %%i
    code --install-extension %%i
)

echo 복구 완료!
pause
