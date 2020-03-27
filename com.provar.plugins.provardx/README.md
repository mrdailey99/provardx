@provartesting/provardx
=======================

sfdx custom plugin to run provar testcases

[![Version](https://img.shields.io/npm/v/@provartesting/provardx.svg)](https://npmjs.org/package/@provartesting/provardx)
[![CircleCI](https://circleci.com/gh/provardx/provardx/tree/master.svg?style=shield)](https://circleci.com/gh/provardx/provardx/tree/master)
[![Appveyor CI](https://ci.appveyor.com/api/projects/status/github/provardx/provardx?branch=master&svg=true)](https://ci.appveyor.com/project/heroku/provardx/branch/master)
[![Codecov](https://codecov.io/gh/provardx/provardx/branch/master/graph/badge.svg)](https://codecov.io/gh/provardx/provardx)
[![Greenkeeper](https://badges.greenkeeper.io/provardx/provardx.svg)](https://greenkeeper.io/)
[![Known Vulnerabilities](https://snyk.io/test/github/provardx/provardx/badge.svg)](https://snyk.io/test/github/provardx/provardx)
[![Downloads/week](https://img.shields.io/npm/dw/@provartesting/provardx.svg)](https://npmjs.org/package/@provartesting/provardx)
[![License](https://img.shields.io/npm/l/@provartesting/provardx.svg)](https://github.com/provardx/provardx/blob/master/package.json)

<!-- toc -->
* [Debugging your plugin](#debugging-your-plugin)
<!-- tocstop -->
<!-- install -->
<!-- usage -->
```sh-session
$ npm install -g @provartesting/provardx
$ sfdx COMMAND
running command...
$ sfdx (-v|--version|version)
@provartesting/provardx/0.0.1 win32-x64 node-v10.15.3
$ sfdx --help [COMMAND]
USAGE
  $ sfdx COMMAND
...
```
<!-- usagestop -->
<!-- commands -->
* [`sfdx provar:compile [-p <string>] [-h <string>] [-c <string>] [--json] [-l <string>]`](#sfdx-provarcompile--p-string--h-string--c-string---json--l-string)
* [`sfdx provar:metadatacache [-m <string>] [-c <string>] [-p <string>] [-n <string>] [-o <string>] [--json] [-l <string>]`](#sfdx-provarmetadatacache--m-string--c-string--p-string--n-string--o-string---json--l-string)
* [`sfdx provar:runtests [-f <string>] [-p <string>] [-o <string>] [-c <string>] [-m <string>] [-s <string>] [--json] [-l <string>]`](#sfdx-provarruntests--f-string--p-string--o-string--c-string--m-string--s-string---json--l-string)
* [`sfdx provar:test [-p <string>] [--json] [-l <string>]`](#sfdx-provartest--p-string---json--l-string)
* [`sfdx provar:validate [-p <string>] [--json] [-l <string>]`](#sfdx-provarvalidate--p-string---json--l-string)

## `sfdx provar:compile [-p <string>] [-h <string>] [-c <string>] [--json] [-l <string>]`

Pre-compiles any /src PageObject or PageControl Java source files into object code to use in runtests

```
USAGE
  $ sfdx provar:compile [-p <string>] [-h <string>] [-c <string>] [--json] [-l <string>]

OPTIONS
  -c, --projectpath=projectpath    Specify project path override
  -h, --provarhome=provarhome      Specify provar home override
  -l, --loglevel=loglevel          Specify log level for command output
  -p, --propertyfile=propertyfile  Specify custom property file.
  --json                           format output as json

EXAMPLE
  $ sfdx provar:compile -p './myproperties.json' --json --loglevel SEVERE
```

_See code: [src\commands\provar\compile.ts](https://github.com/provardx/provardx/blob/v0.0.1/src\commands\provar\compile.ts)_

## `sfdx provar:metadatacache [-m <string>] [-c <string>] [-p <string>] [-n <string>] [-o <string>] [--json] [-l <string>]`

Command to pre-download any required metadata for a specified user or provar connections.

```
USAGE
  $ sfdx provar:metadatacache [-m <string>] [-c <string>] [-p <string>] [-n <string>] [-o <string>] [--json] [-l 
  <string>]

OPTIONS
  -c, --cachepath=cachepath                      Relative or full file path for where the metadata cache will be stored.
  -l, --loglevel=loglevel                        Specify the log level for command

  -m, --metadatalevel=metadatalevel              Permitted values reload (get all metadata - default) | refresh (only
                                                 download changes).

  -n, --connections=connections                  Specify the name of connection for which metadata to be downloaded.

  -o, --connectionoverrides=connectionoverrides  Specify the name of connection for which metadata to be downloaded.

  -p, --propertyfile=propertyfile                Specify custom property file.

  --json                                         format output as json

EXAMPLE
  $ sfdx provar:metadatacache -m 'refresh' -c './metadata' -f './myproperties.json'
```

_See code: [src\commands\provar\metadatacache.ts](https://github.com/provardx/provardx/blob/v0.0.1/src\commands\provar\metadatacache.ts)_

## `sfdx provar:runtests [-f <string>] [-p <string>] [-o <string>] [-c <string>] [-m <string>] [-s <string>] [--json] [-l <string>]`

Runs the specified list of Provar test cases against the currently configured SFDX defaultuserrname

```
USAGE
  $ sfdx provar:runtests [-f <string>] [-p <string>] [-o <string>] [-c <string>] [-m <string>] [-s <string>] [--json] 
  [-l <string>]

OPTIONS
  -c, --cachepath=cachepath                            Specify relative or full file path for where a metadata cache has
                                                       already been downloaded using either a VCS extract or metadata
                                                       ProvarDX command.

  -f, --filespec=filespec                              Specify comma-delimited, ordered paths of test cases to be
                                                       executed. If not provided all tests in the PROJECTPATH will be
                                                       executed.

  -l, --loglevel=loglevel                              Specify the level of feedback provided during the compilation
                                                       (see above) and execution.

  -m, --metadatalevel=metadatalevel                    Specify permitted values reload (get all metadata - default) |
                                                       refresh (only download changes). This overrides any settings made
                                                       in the propertyfile.

  -o, --connectionoverridefile=connectionoverridefile  Connection file in the format provardx-connection-schema.json
                                                       providing a mapping between the Provar project Connection names
                                                       and the target users to be used.

  -p, --propertyfile=propertyfile                      provardx-properties.json file. If filepath specified the filename
                                                       will be assumed or it can be specified (.e.g myprops.json). File
                                                       must be in JSON format and conform to the provardx-properties
                                                       JSON schema.

  -s, --secrets=secrets                                Specify path to secrets file

  --json                                               format output as json

EXAMPLE
  $ sfdx provar:runtests -f './myproperties.json'
```

_See code: [src\commands\provar\runtests.ts](https://github.com/provardx/provardx/blob/v0.0.1/src\commands\provar\runtests.ts)_

## `sfdx provar:test [-p <string>] [--json] [-l <string>]`

Validate the provardx property file agains standard JSON Schema

```
USAGE
  $ sfdx provar:test [-p <string>] [--json] [-l <string>]

OPTIONS
  -l, --loglevel=loglevel          Allow to provide loglevels.
  -p, --propertyfile=propertyfile  Specify custom property file.
  --json                           format output as json

EXAMPLES
  $ sfdx provar:validate
       Property file is valid.
    
  $ sfdx provar:validate --json
       Invalid property file.
       [Detailed validation error shown]
    
  $ sfdx provar:validate -p './provardx-properties.json' --json
       Invalid property file.
       [Detailed validation error shown]
```

_See code: [src\commands\provar\test.ts](https://github.com/provardx/provardx/blob/v0.0.1/src\commands\provar\test.ts)_

## `sfdx provar:validate [-p <string>] [--json] [-l <string>]`

Validate the provardx property file agains standard JSON Schema

```
USAGE
  $ sfdx provar:validate [-p <string>] [--json] [-l <string>]

OPTIONS
  -l, --loglevel=loglevel          Allow to provide loglevels.
  -p, --propertyfile=propertyfile  Specify custom property file.
  --json                           format output as json

EXAMPLES
  $ sfdx provar:validate
       Property file is valid.
    
  $ sfdx provar:validate --json
       Invalid property file.
       [Detailed validation error shown]
    
  $ sfdx provar:validate -p './provardx-properties.json' --json
       Invalid property file.
       [Detailed validation error shown]
```

_See code: [src\commands\provar\validate.ts](https://github.com/provardx/provardx/blob/v0.0.1/src\commands\provar\validate.ts)_
<!-- commandsstop -->
<!-- debugging-your-plugin -->
# Debugging your plugin
We recommend using the Visual Studio Code (VS Code) IDE for your plugin development. Included in the `.vscode` directory of this plugin is a `launch.json` config file, which allows you to attach a debugger to the node process when running your commands.

To debug the `hello:org` command: 
1. Start the inspector
  
If you linked your plugin to the sfdx cli, call your command with the `dev-suspend` switch: 
```sh-session
$ sfdx hello:org -u myOrg@example.com --dev-suspend
```
  
Alternatively, to call your command using the `bin/run` script, set the `NODE_OPTIONS` environment variable to `--inspect-brk` when starting the debugger:
```sh-session
$ NODE_OPTIONS=--inspect-brk bin/run hello:org -u myOrg@example.com
```

2. Set some breakpoints in your command code
3. Click on the Debug icon in the Activity Bar on the side of VS Code to open up the Debug view.
4. In the upper left hand corner of VS Code, verify that the "Attach to Remote" launch configuration has been chosen.
5. Hit the green play button to the left of the "Attach to Remote" launch configuration window. The debugger should now be suspended on the first line of the program. 
6. Hit the green play button at the top middle of VS Code (this play button will be to the right of the play button that you clicked in step #5).
<br><img src=".images/vscodeScreenshot.png" width="480" height="278"><br>
Congrats, you are debugging!
