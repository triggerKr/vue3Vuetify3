@echo off
chcp 65001 > nul

echo VSCode 임시 캐시 및 로그 폴더 삭제 중...
rd /s /q "%APPDATA%\Code\Cache"
rd /s /q "%APPDATA%\Code\CachedData"
rd /s /q "%APPDATA%\Code\GPUCache"
rd /s /q "%APPDATA%\Code\logs"
rd /s /q "%APPDATA%\Code\Storage"
rd /s /q "%APPDATA%\Code\User\workspaceStorage"

echo.

set BACKUP_DIR=%USERPROFILE%\vscode_backup_%date:~0,4%%date:~5,2%%date:~8,2%
echo 백업 폴더: %BACKUP_DIR%
mkdir "%BACKUP_DIR%"

echo 1. 사용자 설정 백업 중...
xcopy "%APPDATA%\Code\User" "%BACKUP_DIR%\User" /E /I /Y

echo 2. 확장 목록 백업 중...
code --list-extensions > "%BACKUP_DIR%\vscode-extensions.txt"

echo.
echo 백업 완료! 백업 경로: %BACKUP_DIR%
pause
