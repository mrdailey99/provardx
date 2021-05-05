# runProvarTests.ps1
param ([string] $secretsPassword)

unzip -q Provar_ANT_latest.zip -d $Env:BUILD_SOURCESDIRECTORY\\ProvarHome

$cmd = 'C:\ProgramData\Chocolatey\bin\ant.exe'
$arg1 = '-buildfile'
$arg2 = "$Env:BUILD_SOURCESDIRECTORY\ProvarProject\ANT\build_azure.xml"
$arg3 = '-lib'
$arg4 = "$Env:PROVAR_HOME/ant"
$arg5 = "-DprovarSecretsPassword=$secretsPassword"
$arg6 = 'runtests'

& $cmd $arg1 $arg2 $arg3 $arg4 $arg5 $arg6