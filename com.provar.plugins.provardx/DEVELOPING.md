# Developing

## Pre-requisites

1. Node.js version >= 10.0.0
1. Provar should be installed on the machine
1. There should be a provardx folder with provardx.jar available in the installation directory
1. sfdx cli should be installed on machine (https://developer.salesforce.com/tools/sfdxcli)
1. Install Yarn globally using `npm install -g yarn`.

## Typical workflow

1. Clone this repository from git `git clone https://github.com/ProvarTesting/provardx.git`
1. `cd` into `provardx`
1. We develop on the `development` branch and release from the `master` branch. Checkout
   development branch `git checkout -t origin/development`.
1. `yarn` to bring in all the top-level dependencies
1. `sfdx plugins:link` to link the plugins with `sfdx cli`

## List of Useful commands

### `yarn install`

To install all dependencies

### `yarn run prepack`

This command builds the package.

### `yarn run postpack`

This command cleans working directory from the build artifacts.

### `yarn run posttest`

This command checks for tslint issues in the code.

### `yarn run test`

This runs tests.

### `yarn run prettier`

This command formats the code with the given rules/config.

### `yarn run prettier:verify`

This command lists all the files which are failing as per prettier config.

## Debugging the plugin

We recommend using the Visual Studio Code (VS Code) IDE for your plugin development. Included in the `.vscode` directory of this plugin is a `launch.json` config file, which allows you to attach a debugger to the node process when running your commands.

To debug the `provar:validate` command:

1. Start the inspector

If you linked your plugin to the sfdx cli, call your command with the `dev-suspend` switch:

```sh-session
$ sfdx provar:validate -p './provar-properties.json' --dev-suspend
```

Alternatively, to call your command using the `bin/run` script, set the `NODE_OPTIONS` environment variable to `--inspect-brk` when starting the debugger:

```sh-session
$ NODE_OPTIONS=--inspect-brk bin/run provar:validate -p './provar-properties.json'
```

2. Set some breakpoints in your command code
3. Click on the Debug icon in the Activity Bar on the side of VS Code to open up the Debug view.
4. In the upper left hand corner of VS Code, verify that the "Attach to Remote" launch configuration has been chosen.
5. Hit the green play button to the left of the "Attach to Remote" launch configuration window. The debugger should now be suspended on the first line of the program.
6. Hit the green play button at the top middle of VS Code (this play button will be to the right of the play button that you clicked in step #5).
   <br><img src=".images/vscodeScreenshot.png" width="480" height="278"><br>
   Congrats, you are debugging!
