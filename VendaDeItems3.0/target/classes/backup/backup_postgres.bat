@echo off
REM Configurações
set USER=postgres
set PGPASSWORD=1705
set DB_NAME="Trabalho2"
set BACKUP_DIR="C:\Users\HP\Documents\BancodeDados2-T2\VendaDeItems\src\main\resources\backup"
set DATE=%DATE:~6,4%%DATE:~3,2%%DATE:~0,2%_%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%
set BACKUP_FILE=%BACKUP_DIR%\backup_%DATE%.backup

REM Verifica se o diretório de backup existe, se não, cria
if not exist "%BACKUP_DIR%" (
    echo Criando diretório de backup: %BACKUP_DIR%
    mkdir "%BACKUP_DIR%"
) else (
    echo Diretório de backup já existe: %BACKUP_DIR%
)

REM Verifica se o arquivo de backup já existe
if exist "%BACKUP_FILE%" (
    echo O arquivo de backup já existe: %BACKUP_FILE%
    echo Excluindo o arquivo existente...
    del "%BACKUP_FILE%"
)

REM Comando pg_dump
echo Realizando backup do banco de dados...
pg_dump -U %USER% -h localhost -d %DB_NAME% -F c -b -v -f "%BACKUP_FILE%"

REM Verifica se o backup foi criado
if exist "%BACKUP_FILE%" (
    echo Backup realizado com sucesso: %BACKUP_FILE%
) else (
    echo Erro ao realizar o backup.
)